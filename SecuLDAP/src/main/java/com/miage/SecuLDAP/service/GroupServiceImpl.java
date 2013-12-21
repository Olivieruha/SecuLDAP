package com.miage.SecuLDAP.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.SecuLDAP.DAO.GroupDao;
import com.miage.SecuLDAP.model.Group;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
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
		return groupDao.findByPrimaryKey(name);
	}

	@Override
	public List<?> findAllGroup() {
		return groupDao.findAll();
	}
}
