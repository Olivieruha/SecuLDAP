package com.miage.SecuLDAP.model;

import java.util.LinkedList;
import java.util.List;

public class Group {
	private String groupName;
	private List<Person> groupMembers = new LinkedList<Person>();
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
	
	@Override
	public boolean equals(Object obj) {
        if(this == obj)
            return true;
 
        if(obj instanceof Group) {
             Group group = (Group) obj;
             if(!this.groupName.equals(group.groupName)) {
            	 return false;
            }
            return true;
        }
        return false;
    }	
}
