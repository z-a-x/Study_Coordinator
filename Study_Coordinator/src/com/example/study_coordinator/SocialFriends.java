package com.example.study_coordinator;


import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUserGroup;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SocialFriends extends Fragment {
	// final ListView listView ;
	private static JSONParser jsonParser = new JSONParser();
	ListView listView;
	String result;
	FriendAdapter adapter;
	private ProgressDialog pDialog;

	// Store instance variables
	private String title;
	private int page;


	// newInstance constructor for creating fragment with arguments
	public static SocialFriends newInstance(int page, String title) {
		SocialFriends fragmentFirst = new SocialFriends();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_social_friends, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		listView = (ListView) view.findViewById(R.id.lv_social_friends);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;

				// ListView Clicked item value
				/*
				 * String itemValue = (String) listView.getItemAtPosition(position);
				 * 
				 * // Show Alert Toast.makeText(getActivity().getApplicationContext(),
				 * "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG) .show();
				 */
			}

		});

		LookUp userGroupFetcher = new LookUpUserGroup(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				/*
				SocialGroups sg = new SocialGroups();
				
				List<Group> l =  sg.getSendGroups();
				s = "(";
				for(Group g : l){
					s+= g.name+" ,";
				}
				s+=")";
				*/
				List<User> friends = getUserGroup(result);
				
				adapter = new FriendAdapter(getActivity(), friends);
				listView.setAdapter(adapter);
				
			}
		};
		SessionManager session = new SessionManager(getActivity());
		String query_groups = session.getUserGroups();
	
		
		System.out.println("Zahteva po uporabnikih iz skupin: "+query_groups);
		userGroupFetcher.execute("selected_groups", query_groups);

		return view;
	}

}