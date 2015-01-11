package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.baseclasses.User;

import android.content.Context;

public abstract class LookUpAttendants extends LookUp {

	public LookUpAttendants(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getAttendants.php"+getQuery(params));
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<User> getAttendants(JSONObject result) throws JSONException {
		List<User> attendants = new ArrayList<User>();
		JSONArray attendantsArray = result.getJSONArray("user_event");
		for (int i = 0; i < attendantsArray.length(); i++) {		
			JSONObject attendantsObject = attendantsArray.getJSONObject(i);
			int id = attendantsObject.getInt("user_id");
			String name = attendantsObject.getString("user_name");
			String lastName = attendantsObject.getString("user_last_name");
						
			try {
				User user = new User(id, name, lastName, null);
				attendants.add(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return attendants;
	}

}
