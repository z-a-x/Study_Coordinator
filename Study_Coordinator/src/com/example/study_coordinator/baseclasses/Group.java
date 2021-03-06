package com.example.study_coordinator.baseclasses;

public class Group implements HasId {
	public final int id;
	public final String name;
    public final int ownerId;
    
	public Group(int id, String name, int ownerId) {
		this.id = id;
		this.name = name;
		this.ownerId = ownerId;
	}
	
	@Override
	public int getId() {
		return id;
	}
}
