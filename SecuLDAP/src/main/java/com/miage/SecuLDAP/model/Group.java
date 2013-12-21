package com.miage.SecuLDAP.model;

import java.util.List;

public class Group {
	private String groupName;
	private List<Person> groupMembers;
	private String[] arrayDnMembers;
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Person> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<Person> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public String[] getArrayDnMembers() {
		return arrayDnMembers;
	}

	public void setArrayDnMembers(String[] arrayDnMembers) {
		this.arrayDnMembers = arrayDnMembers;
	}
}
