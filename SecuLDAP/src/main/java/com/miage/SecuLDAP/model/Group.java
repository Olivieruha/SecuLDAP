package com.miage.SecuLDAP.model;

import java.util.List;

public class Group {
	private String groupName;
	private List<Person> groupMembers;
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public List<Person> getGroupMember() {
		return groupMembers;
	}
	
	public void setGroupMember(List<Person> groupMember) {
		this.groupMembers = groupMember;
	}	
}
