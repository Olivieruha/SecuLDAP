package com.miage.SecuLDAP.service;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;

import com.miage.SecuLDAP.DAO.PersonDao;
import com.miage.SecuLDAP.DAO.PersonDaoImpl;
import com.miage.SecuLDAP.model.Person;

public class PersonServiceImpl implements PersonService
{

	private PersonDao personDao = new PersonDaoImpl();
	
	public void test()
	{
		personDao.test();
	}
	
	@Override
	public void createPerson(Person person)
	{
		personDao.create(person);
	}

	@Override
	public void updatePerson(Person person)
	{
		personDao.update(person);

	}

	@Override
	public void deletePerson(Person person)
	{
		personDao.delete(person);

	}

	@Override
	public Person findByPrimaryKey(String name)
	{
		return personDao.findByPrimaryKey(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAllPerson()
	{	
		return personDao.findAll();
	}

	@Override
	public ContextMapper getContextMapper()
	{		
		return personDao.getContextMapper();
	}

}
