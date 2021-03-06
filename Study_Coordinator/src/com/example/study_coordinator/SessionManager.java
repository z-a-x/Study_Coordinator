package com.example.study_coordinator;
 
import java.util.HashMap;
 


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidPref";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";   
    // User id (make variable public to access from outside)
    public static final String KEY_USERID = "user_id";
    // User pass (make variable public to access from outside)
    public static final String KEY_HASHPASS = "user_hashpass";
    // User groups (make variable public to access from outside)

    
    public static final String KEY_GROUPS = "user_groups";
    //grupe od userja naprave
    public static final String KEY_MY_GROUPS = "my_groups";
    
    public static final String KEY_SEARCH_WORD = "search_word";
    
    public static final String KEY_NAME = "name";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PICTURE = "picture";
     
    
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    //nastavi grupe od userja naprave
    public void setMyGroups(String myGroups){
    	editor.putString(KEY_MY_GROUPS, myGroups);
    	editor.commit();
    }
    
    //splo�en uporabnik
    public void setUserGroups(String groups){
    	editor.putString(KEY_GROUPS, groups);
    	// commit changes
        editor.commit();
    }
    
    //skupine splo�nega uporabnika
    public String getUserGroups(){
    	return pref.getString(KEY_GROUPS, null);
    }
    
    
    //pridobi osebne podatke glavnega userja appa
    public String getMyUserId(){
    	return pref.getString(KEY_USERID, null);
    }
    
    public String getMyName(){
    	return pref.getString(KEY_NAME, null);
    }
    
    public String getMyLastName(){
    	return pref.getString(KEY_LAST_NAME, null);
    }
    
    public String getMyEmail(){
    	return pref.getString(KEY_EMAIL, null);
    }
    
    public String getMyPicture(){
    	return pref.getString(KEY_PICTURE, null);
    }
  
    
    //nastavi osebne podatke glavnega userja appa
    public void setMyName(String name){
    	editor.putString(KEY_NAME, name);
    	editor.commit();
    }
    public void setMyLastName(String lastName){
    	editor.putString(KEY_LAST_NAME, lastName );
    	editor.commit();
    }
    
    public void setMyPicture(String picture){
    	editor.putString(KEY_PICTURE, picture);
    	editor.commit();
    }
    
    public void setMyEmail(String email){
    	editor.putString(KEY_EMAIL, email);
    	editor.commit();
    }
    
    public void setUserId(String userId){
    	editor.putString(KEY_USERID, userId);
    	editor.commit();
    }
    
    public void setSearchWord(String searchWord){
    	editor.putString(KEY_GROUPS, searchWord);
    	editor.commit();
    }
    
    /**
     * Create login session
     * */
    public void createLoginSession(String name,String hash, String userID){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
         
        // Storing name in pref
        editor.putString(KEY_USERNAME, name);
         
        // Storing user id in pref
        editor.putString(KEY_USERID, userID);
        
        // Storing user hash pass in pref
        editor.putString(KEY_HASHPASS, hash);
         
        
        // commit changes
        editor.commit();
    }   
     
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             
            // Staring Login Activity
            _context.startActivity(i);
        }
         
    }     
    
    //pridobi trenutno iskalno besedo
    public String getSearchWord(){
    	return pref.getString(KEY_SEARCH_WORD, null);
    }
    
    public String getMyGroups(){
    	return pref.getString(KEY_MY_GROUPS, null);
    }
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
         
        // user email id
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        
        // user email id
        user.put(KEY_HASHPASS, pref.getString(KEY_HASHPASS, null));
        
        user.put(KEY_GROUPS, pref.getString(KEY_MY_GROUPS, null));
         
        // return user
        return user;
    }
     
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
        _context.startActivity(i);
    }
     
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}