package com.miage.SecuLDAP.service;

import java.util.List;

import com.miage.SecuLDAP.model.Person;

public interface PersonService
{
	public void createPerson(Person person);
	public void updatePerson(Person person);
	public void deletePerson(Person person);
	public Person findByPrimaryKey(String name);
	public Person findByDistinguishedName(String dn);
	public List findAllPerson();
}
