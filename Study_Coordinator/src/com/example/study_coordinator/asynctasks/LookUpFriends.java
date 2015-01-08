package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.study_coordinator.baseclasses.User;

public abstract class LookUpFriends extends LookUp {

	public LookUpFriends(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getFriends.php"+getQuery(params));
	}

	/*
	 * STATIC FUNCTIONS, FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION, THAT NEEDS TO BE
	 * IMPLEMENTED IN EXTENDING CLASS:
	 */

	protected static List<User> getUsers(JSONObject result) throws JSONException {
		List<User> users = new ArrayList<User>();
		
		JSONArray usersArray = result.getJSONArray("friends");
		for (int i = 0; i < usersArray.length(); i++) {
			JSONObject userObject = usersArray.getJSONObject(i);
			int id = userObject.getInt("user_id");
			String name = userObject.getString("user_name");
			String lastName = userObject.getString("user_last_name");
			String username = userObject.getString("username");
			String email = userObject.getString("email");
			User user = new User(id, name, lastName, username, email);
			
			users.add(user);
		}
		return users;
	}

}
