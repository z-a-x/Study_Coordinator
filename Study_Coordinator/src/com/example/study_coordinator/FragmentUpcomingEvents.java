package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.FragmentSearch.SearchPagerAdapter;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.Group;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentUpcomingEvents extends FragmentActivityWithDrawer {

	ImageView ivIcon;
	TextView tvItemName;
	ListView listView;
	EventAdapter adapter;
	private ProgressDialog pDialog;
	String result;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_upcoming_events);

		setDrawer();

		listView = (ListView) findViewById(R.id.lv_upcoming);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(FragmentUpcomingEvents.this, FragmentEvent.class);
				String event_id = adapter.getItem(position).id + "";
				intent.putExtra("selected_event", event_id);
				startActivity(intent);

			}

		});

		LookUp groupFetcher = new LookUpGroups(this) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Group> groups = getGroups(result);
				String s = "(";
				for (Group g : groups) {
					s += g.id + ",";
				}
				if (s.length() > 0 && s.charAt(s.length() - 1) == ',') {
					s = s.substring(0, s.length() - 1);
				}
				s += ")";
				SessionManager session = new SessionManager(FragmentUpcomingEvents.this);
				session.setMyGroups(s);
				// System.out.println("NASTAVIL MANAGER NA "+s);
			}
		};
		SessionManager session = new SessionManager(this);
		HashMap<String, String> pref = session.getUserDetails();
		String userId = pref.get(SessionManager.KEY_USERID);
		groupFetcher.execute("user_id", userId);

		System.out.println("---------------------------------------");
		System.out.println("ID USER: " + userId);
		System.out.println("1. USER GROUPS: " + session.getMyGroups());
		System.out.println("---------------------------------------");

		LookUp eventFetcher = new LookUpEvents(this) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Event> list = getEvents(result);
				List<Event> list1 = new ArrayList<Event>();
				for (Event e : list) {
					Date date1 = e.date;
					Date date2 = new Date();
					if (date2.before(date1)) {
						list1.add(e);
					}
				}
				adapter = new EventAdapter(FragmentUpcomingEvents.this, list1);
				listView.setAdapter(adapter);
			}

		};
		eventFetcher.execute("selected_groups", session.getMyGroups());
		System.out.println("2. USER GROUPS: " + session.getMyGroups());
		/*
		 * List<String> your_array_list = new ArrayList<String>();
		 * your_array_list.add("1. dogodek 1.1.2015 - CTK - predmet1 19:00");
		 * your_array_list.add("2. dogodek 5.1.2015 - FRI - predmet2 15:45");
		 * 
		 * for(int i = 1; i <= 30; i++){ your_array_list.add("Testni dogodek: " + i); }
		 * 
		 * ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
		 * android.R.layout.simple_list_item_1, your_array_list );
		 * 
		 * listView.setAdapter(arrayAdapter);
		 */
		// listView.smoothScrollToPosition(0);
		ivIcon = (ImageView) findViewById(R.id.frag_events_icon);
		tvItemName = (TextView) findViewById(R.id.frag_events_tv);

		// tvItemName.setText(getArguments().getString(ITEM_NAME));
		// ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
	}

}