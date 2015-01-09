package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.baseclasses.User;

import android.content.Context;

/**
 * Example of received JSON: {"users":[{"user_id":"0","user_name":"aaa","user_last_name":"bbb","username":"aaa"}]}
 */
public abstract class LookUpUsers extends LookUp {

	public LookUpUsers(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getUsers.php"+getQuery(params));
	}	

	protected static List<User> getUsers(JSONObject result) throws JSONException {
		List<User> users = new ArrayList<User>();
		
		JSONArray usersArray = result.getJSONArray("users");
		for (int i = 0; i < usersArray.length(); i++) {
			JSONObject userObject = usersArray.getJSONObject(i);
			
			int user_id = userObject.getInt("user_id");
			String user_name = userObject.getString("user_name");
			String user_last_name = userObject.getString("user_last_name");
			String username = userObject.getString("username");
			String user_avatar = userObject.getString("user_avatar");
			String email = userObject.getString("email");
			
			User user = new User(user_id, user_name, user_last_name, username, email);
			users.add(user);
		}
		return users;
	}

}
