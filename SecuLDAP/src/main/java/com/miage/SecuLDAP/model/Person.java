package com.miage.SecuLDAP.model;

public class Person
{
	private String firstName;
	private String lastName;
	private String fullName;
	private String userPassword;
	
	public String getUserPassword()
	{
		return userPassword;
	}
	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}
	public String getFirstName() 
	{
		return firstName;
	}	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}	
	public String getFullName()
	{
		return this.fullName;
	}
	
	public void setFullName(String fullName)
	{
		this.fullName = fullName;	
	}
}
