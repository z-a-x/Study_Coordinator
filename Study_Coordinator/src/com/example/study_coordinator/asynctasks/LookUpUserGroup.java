package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.study_coordinator.baseclasses.User;

/*
 * Example of received JSON: {"groups":[{"group_id":"11","group_name":"prva grupa","groups_owner":"tle bi mogu bit int"}]}
 */
public abstract class LookUpUserGroup extends LookUp {

	public LookUpUserGroup(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getUserGroups.php"+getQuery(params));
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<User> getUserGroup(JSONObject result) throws JSONException {
		List<User> users = new ArrayList<User>();
		List<Integer> ids = new ArrayList<Integer>();
		JSONArray userGroupArray = result.getJSONArray("user_group");
		
		for (int i = 0; i < userGroupArray.length(); i++) {
			JSONObject userObject = userGroupArray.getJSONObject(i);
			
			int id = userObject.getInt("user_id");
			String name = userObject.getString("user_name");
			String lastName = userObject.getString("user_last_name");
			String userName = userObject.getString("username");
			
			User user = new User(id, name, lastName, userName);
			ids.add(user.id);
			System.out.println(user.id);
			if(i == 0){
				users.add(user);
			}
			
			else{
				if(!(ids.get(i-1) == ids.get(i))){
				System.out.println("DODAJAM");
				users.add(user);
				}
			}
			
		}		
		return users;
	}

}
