package com.example.study_coordinator.baseclasses;
			
public class User implements HasId {
	public final int id;
	public final String name;
    public final String lastName;    
    public final String username;
    public  String email;
    public String pathToPicture;
    
	public User(int id,  String name, String lastName, String username, String email ) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;		
		this.username=username;
		this.email = email;
	}

	public User(int id, String name, String lastName, String username) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.username = username;

	}
	
	public User(int id,  String name, String username, String lastName, String email, String pathToPicture ) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;		
		this.username=username;
		this.email = email;
		this.pathToPicture = pathToPicture;
	}
	
	@Override
	public int getId() {
		return id;
	}
}
