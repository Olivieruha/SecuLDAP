package com.miage.SecuLDAP.DAO;

import java.util.List;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Repository;

import com.miage.SecuLDAP.model.Person;

@Repository("personDao")
public class PersonDaoImpl implements PersonDao {
	
	@Autowired
   private LdapTemplate ldapTemplate;
   
   public void setLdapTemplate(LdapTemplate ldapTemplate) {
      this.ldapTemplate = ldapTemplate;
   }

   public void create(Person person) {
      DirContextAdapter context = new DirContextAdapter();
      System.out.println("Contexte ok");
      mapToContext(person, context);
      System.out.println("Mapping ok");
      ldapTemplate.bind(buildDn(person), context, null);
      System.out.println("Binding ok");
   }

   public void update(Person person) {
      Name dn = buildDn(person);
      DirContextAdapter context = (DirContextAdapter)ldapTemplate.lookup(dn);
      mapToContext(person, context);
      ldapTemplate.modifyAttributes(dn, context.getModificationItems());
   }

   public void delete(Person person) {
      ldapTemplate.unbind(buildDn(person));
   }

   public Person findByPrimaryKey(String name) {
      Name dn = buildDn(name);
      return (Person) ldapTemplate.lookup(dn, getContextMapper());
   }

   public List findAll() {
      EqualsFilter filter = new EqualsFilter("objectclass", "person");
      return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
   }

   public ContextMapper getContextMapper() {
      return new PersonContextMapper();
   }

   public Name buildDn(Person person) {
      return buildDn(person.getFullName());
   }

   public Name buildDn(String fullname) {
      DistinguishedName dn = new DistinguishedName();
      dn.add("dc","com");
      dn.add("dc", "example");
      dn.add("ou", "users");
      dn.add("cn", fullname);
      return dn;
   }

   public void mapToContext(Person person, DirContextAdapter context) {
      context.setAttributeValues("objectclass", new String[] {"inetOrgPerson", "organizationalPerson", "person", "top"});
      context.setAttributeValue("cn", person.getFullName());
      context.setAttributeValue("sn", person.getLastName());
      context.setAttributeValue("userPassword", person.getUserPassword());
   }

   private static class PersonContextMapper implements ContextMapper 
   {	
      public Object mapFromContext(Object ctx) 
      {
         DirContextAdapter context = (DirContextAdapter)ctx;
         Person person = new Person();
         person.setFullName(context.getStringAttribute("cn"));
         person.setLastName(context.getStringAttribute("sn"));
         return person;
      }
   }
}
