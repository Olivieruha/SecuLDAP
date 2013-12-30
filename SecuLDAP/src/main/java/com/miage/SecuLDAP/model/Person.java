package com.miage.SecuLDAP.model;

public class Person
{
	private String lastName;
	private String fullName;
	private String userPassword;
	
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("cn=").append(fullName).append(",ou=users,dc=example,dc=com");
		return sB.toString();
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;	
	}

	@Override
	public boolean equals(Object obj) {
        if(this == obj)
            return true;
 
        if(obj instanceof Person) {
             Person person = (Person) obj;
             if(!this.fullName.equals(person.fullName)) {
            	 return false;
            }
            return true;
        }
        return false;
    }	
}
