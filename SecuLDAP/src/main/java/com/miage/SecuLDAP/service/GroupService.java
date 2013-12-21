package com.miage.SecuLDAP.service;

import java.util.List;

import com.miage.SecuLDAP.model.Group;

public interface GroupService {
	public void createGroup(Group group);
	public void updateGroup(Group group);
	public void deleteGroup(Group group);
	public Group findByPrimaryKey(String name);
	public List findAllGroup();
}
