package com.miage.SecuLDAP.service;

import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.SecuLDAP.DAO.PersonDao;
import com.miage.SecuLDAP.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;
	
	@Override
	public void createPerson(Person person) {
		personDao.create(person);
	}

	@Override
	public void updatePerson(Person person) {
		personDao.update(person);
	}

	@Override
	public void deletePerson(Person person) {
		personDao.delete(person);
	}

	@Override
	public Person findByPrimaryKey(String name) {
		return personDao.findByPrimaryKey(name);
	}
	
	@Override
	public Person findByDistinguishedName(String dn) {
		return personDao.findByDistinguishedName(dn);
	}


	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<Person> findAllPerson() {	
		return (LinkedList<Person>) personDao.findAll();
	}
}
