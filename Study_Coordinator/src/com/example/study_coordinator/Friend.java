package com.example.study_coordinator;

public class Friend {
    private String username;
    private String userName;
    private String userLastName;
    
    public Friend(String userName, String userLastName){
    	this.userName = userName;
    	this.userLastName = userLastName;
    }

    public Friend(String username) {
       this.username = username;       
    }
    public String getUsername(){
    	return username;
    }
    public String getUserName(){
    	return userName;
    }
    public String getUserLastName(){
    	return userLastName;
    }
}