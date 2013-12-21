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

import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;

@Repository("groupDao")
public class GroupDaoImpl implements GroupDao {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Override
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;	
	}

	@Override
	public void create(Group group) {
		DirContextAdapter context = new DirContextAdapter();
		mapToContext(group, context);
		ldapTemplate.bind(buildDn(group), context, null);
	}

	@Override
	public void update(Group group) {
		Name dn = buildDn(group);
		DirContextAdapter context = (DirContextAdapter)ldapTemplate.lookup(dn);
	    mapToContext(group, context);
	    ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}

	@Override
	public void delete(Group group) {
		ldapTemplate.unbind(buildDn(group));
	}

	@Override
	public Group findByPrimaryKey(String name) {
		Name dn = buildDn(name);
	    return (Group) ldapTemplate.lookup(dn, getContextMapper());
	}

	@Override
	public List<?> findAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "groupOfNames");
	    return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}

	@Override
	public ContextMapper getContextMapper() {
		return new GroupContextMapper();
	}

	@Override
	public Name buildDn(Group group) {
		return buildDn(group.getGroupName());
	}

	@Override
	public Name buildDn(String groupName) {
		DistinguishedName dn = new DistinguishedName();
	      dn.add("dc","com");
	      dn.add("dc", "example");
	      dn.add("ou", "groups");
	      dn.add("cn", groupName);
	      return dn;
	}

	@Override
	public void mapToContext(Group group, DirContextAdapter context) {
		context.setAttributeValues("objectclass", new String[] {"groupOfNames", "top"});
	    context.setAttributeValue("cn", group.getGroupName());
	    
	    for(Person person : group.getGroupMembers())
	    	context.addAttributeValue("member", person.toString(), false);
	}
	
   private static class GroupContextMapper implements ContextMapper 
   {	
      public Object mapFromContext(Object ctx) 
      {
         DirContextAdapter context = (DirContextAdapter)ctx;
         Group group = new Group();
         group.setGroupName(context.getStringAttribute("cn"));              
         group.setArrayDnMembers(context.getStringAttributes("member"));
         return group;
      }
   }
}
