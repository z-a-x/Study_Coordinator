package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.study_coordinator.DatabaseConnect;
import com.example.study_coordinator.JSONParser;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

/*
 * Example of received JSON: {"groups":[{"group_id":"11","group_name":"prva grupa","groups_owner":"tle bi mogu bit int"}]}
 */
public abstract class LookUpUserDetails extends LookUp {
	private static JSONParser jsonParser = new JSONParser();
	public LookUpUserDetails(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getUserDetails.php"+getQuery(params));
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<User> getUserDetails(JSONObject result) throws JSONException {
		List<User> users = new ArrayList<User>();

		JSONArray userGroupArray = result.getJSONArray("user");
		
		for (int i = 0; i < userGroupArray.length(); i++) {
			
			JSONObject userObject = userGroupArray.getJSONObject(i);			
			int id = userObject.getInt("user_id");
			String name = userObject.getString("user_name");
			String lastName = userObject.getString("user_last_name");
			String userName = userObject.getString("username");
			String email = userObject.getString("email");
			String picture =userObject.getString("user_avatar");

			User user = new User(id, name, lastName, userName, email, picture);
				
			
			
			users.add(user);
			break;
		}
		return users;
	}

}
