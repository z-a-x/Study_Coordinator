package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

/*
 * Example of received JSON: {"groups":[{"group_id":"11","group_name":"prva grupa","groups_owner":"tle bi mogu bit int"}]}
 */
public abstract class LookUpGroups extends LookUp {

	public LookUpGroups(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getGroups.php");
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<Group> getGroups(JSONObject result) throws JSONException {
		List<Group> groups = new ArrayList<Group>();

		JSONArray groupsArray = result.getJSONArray("groups");
		for (int i = 0; i < groupsArray.length(); i++) {
			JSONObject groupObject = groupsArray.getJSONObject(i);
			int id = groupObject.getInt("group_id");
			String name = groupObject.getString("group_name");
			int ownerId = groupObject.getInt("groups_owner");
			Group group = new Group(id, name, ownerId);
			groups.add(group);
		}
		return groups;
	}

}
