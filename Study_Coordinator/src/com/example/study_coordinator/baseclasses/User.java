package com.example.study_coordinator.baseclasses;
			
public class User {
	public final int id;
	public final String name;
    public final String lastName;
    public final String userName;
    
	public User(int id, String name, String lastName, String userName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.userName = userName;
	}
}
