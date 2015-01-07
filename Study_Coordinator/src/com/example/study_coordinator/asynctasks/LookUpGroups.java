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
		return super.doInBackground("getGroups.php"+getQuery(params));
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<Group> getGroups(JSONObject result) throws JSONException {
		List<Group> groups = new ArrayList<Group>();

		JSONArray groupsArray = result.getJSONArray("groups");
		String ownerIdIzBaze="-1";
		int ownerId = -1;
		String name = "Napaka";
		int id = -1;
		
		for (int i = 0; i < groupsArray.length(); i++) {
			JSONObject groupObject = groupsArray.getJSONObject(i);
			System.out.println("JSON OBJEKT: "+groupObject.getString("group_name"));
			
			id = Integer.parseInt(groupObject.getString("group_id"));
			name = groupObject.getString("group_name");
			ownerIdIzBaze =  groupObject.getString("groups_owner");
			if(!ownerIdIzBaze.equals("")){
				ownerId = Integer.parseInt(ownerIdIzBaze);
			}
			
			System.out.println(name+" "+id+" "+ownerId);
			Group group = new Group(id, name, ownerId);
			System.out.println("GROUP_ID "+id+" GROUP NAME: "+name+" OWNER_ID+ "+ownerId);
			groups.add(group);
		}
		return groups;
	}

}
