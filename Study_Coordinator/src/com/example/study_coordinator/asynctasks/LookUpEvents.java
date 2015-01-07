package com.example.study_coordinator.asynctasks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.content.Context;

/*
 * Example of received JSON: {"events":[{"event_id":"1","location_id":"0","group_id":"0",
 * 										"event_name":"prvi event", "date":"2015-01-16 00:00:00",
 * 										"description":"se dobimo po predavanjih","scope":"public"}]} 
 */
public abstract class LookUpEvents extends LookUp {

	public LookUpEvents(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("getEvents.php"+getQuery(params));
	}

	/*
	 * FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION
	 */
	protected static List<Event> getEvents(JSONObject result) throws JSONException {
		List<Event> events = new ArrayList<Event>();
		JSONArray eventsArray = result.getJSONArray("events");
		
		for (int i = 0; i < eventsArray.length(); i++) {
			JSONObject eventObject = eventsArray.getJSONObject(i);
			int id = eventObject.getInt("event_id");
			String locationId = eventObject.getString("geoloc");
			int groupId = eventObject.getInt("group_id");
			String name = eventObject.getString("event_name");
			String date = eventObject.getString("time");
			String description = eventObject.getString("description");
			String scope = eventObject.getString("scope");
			
			try {
				Event event = new Event(id, locationId, groupId, name, date, description, scope);
				events.add(event);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return events;
	}

}
