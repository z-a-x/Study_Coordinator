package com.example.study_coordinator;

public class Friend {
    public String username;
    public String userName;
    public String userLastName;
    
    public Friend(String userName, String userLastName){
    	this.userName = userName;
    	this.userLastName = userLastName;
    }

    public Friend(String username) {
       this.username = username;
       
    }
}