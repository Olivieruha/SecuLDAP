package com.miage.SecuLDAP.model;

public class Person
{
	private String uid;
	private String firstName;
	private String lastName;
	private String fullName;
	private String userPassword;
	
	public Person(String firstName, String lastName, String userPassword)
	{
		this.uid = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName+"."+lastName;
		this.userPassword = userPassword;
	}
	
	public Person()
	{
		this(null, null, null);
		this.fullName = null;
		this.uid = null;
	}
	
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
	public String getUid() 
	{
		return uid;
	}
	public void setUid(String uid) 
	{
		this.uid = uid;
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
