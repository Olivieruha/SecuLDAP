package com.miage.SecuLDAP.DAO;

import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;

import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;

public interface GroupDao {
	public void setLdapTemplate(LdapTemplate ldapTemplate);
	public void create(Group group);
	public void update(Group group);
	public void delete(Group group);
	public Group findByPrimaryKey(String name);
	public List findAll();
	public ContextMapper getContextMapper();
	public Name buildDn(Group group);
	public Name buildDn(String fullGroupName);
	public void mapToContext(Group group, DirContextAdapter context);
}
