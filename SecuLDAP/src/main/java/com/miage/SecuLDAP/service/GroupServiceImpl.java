package com.miage.SecuLDAP.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.SecuLDAP.DAO.GroupDao;
import com.miage.SecuLDAP.model.Group;
import com.miage.SecuLDAP.model.Person;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private PersonService personService;
	
	@Override
	public void createGroup(Group group) {
		groupDao.create(group);
	}

	@Override
	public void updateGroup(Group group) {
		groupDao.update(group);
	}

	@Override
	public void deleteGroup(Group group) {
		groupDao.delete(group);
	}

	@Override
	public Group findByPrimaryKey(String name) {
		Group group = groupDao.findByPrimaryKey(name);
		List<Person> groupMembers = new LinkedList<Person>();
		for(int i = 0 ; i < group.getArrayDnMembers().length ; ++i)
			groupMembers.add(personService.findByDistinguishedName(group.getArrayDnMembers()[i]));
		group.setGroupMembers(groupMembers);
		return group; //groupDao.findByPrimaryKey(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<Group> findAllGroup() {
		return (LinkedList<Group>) groupDao.findAll();
	}
}
