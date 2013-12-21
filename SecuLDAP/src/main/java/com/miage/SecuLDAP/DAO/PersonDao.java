package com.miage.SecuLDAP.DAO;

import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;

import com.miage.SecuLDAP.model.Person;

public interface PersonDao
{
	public void setLdapTemplate(LdapTemplate ldapTemplate);
	public void create(Person person);
	public void update(Person person);
	public void delete(Person person);
	public Person findByPrimaryKey(String name);
	public Person findByDistinguishedName(String dn);
	public List<?> findAll();
	public ContextMapper getContextMapper();
	public Name buildDn(Person person);
	public Name buildDn(String fullname);
	public void mapToContext(Person person, DirContextAdapter context);
}
