package com.miage.SecuLDAP.service;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;

import com.miage.SecuLDAP.model.Person;

public interface PersonService
{
	public void createPerson(Person person);
	public void updatePerson(Person person);
	public void deletePerson(Person person);
	public Person findByPrimaryKey(String name);
	@SuppressWarnings("rawtypes")
	public List findAllPerson();
	public ContextMapper getContextMapper();
	
}
