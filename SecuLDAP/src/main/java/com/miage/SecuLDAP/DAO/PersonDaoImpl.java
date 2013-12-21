package com.miage.SecuLDAP.DAO;

import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;

import com.miage.SecuLDAP.model.Person;

public class PersonDaoImpl implements PersonDao {
   private LdapTemplate ldapTemplate = new LdapTemplate((ContextSource) getContextMapper());

   public void test() {
	   System.out.println("test");
   }
   
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
      System.out.println("Building Dn ok : dn = "+dn);
      if(getContextMapper() == null) System.out.println("Context mapper null");
      else System.out.println("Context mapper ok");
      if(ldapTemplate == null)System.out.println("Ldaptemplate null");
      else System.out.println("Ldaptemplate ok");
      return (Person) ldapTemplate.lookup(dn, getContextMapper());
   }

   @SuppressWarnings("rawtypes")
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
      context.setAttributeValues("objectclass", new String[] {"inetOrgPerson", "organizationalPerson", "person", "top", "userPassword"});
      context.setAttributeValue("cn", person.getFullName());
      context.setAttributeValue("sn", person.getLastName());
   }

   private static class PersonContextMapper implements ContextMapper 
   {	
      public Object mapFromContext(Object ctx) 
      {
         DirContextAdapter context = (DirContextAdapter)ctx;
         Person person = new Person();
         person.setFullName(context.getStringAttribute("cn"));
         person.setLastName(context.getStringAttribute("sn"));
         person.setUserPassword(context.getStringAttribute("userPassword"));
         return person;
      }
   }
}
