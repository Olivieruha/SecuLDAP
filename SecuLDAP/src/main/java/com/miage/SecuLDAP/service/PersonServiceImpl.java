package com.miage.SecuLDAP.service;

import java.util.List;

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

	@Override
	public List<?> findAllPerson() {	
		return personDao.findAll();
	}
}
