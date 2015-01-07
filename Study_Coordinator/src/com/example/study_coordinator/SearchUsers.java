package com.example.study_coordinator;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUsers;
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

public class SearchUsers extends Fragment {
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
	public static SearchUsers newInstance(int page, String title) {
		SearchUsers fragmentFirst = new SearchUsers();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_search_users, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		listView = (ListView) view.findViewById(R.id.lv_search_users);

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

		LookUp userFetcher = new LookUpUsers(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> users = getUsers(result);
				adapter = new FriendAdapter(getActivity(), users);
				listView.setAdapter(adapter);
			}
		};
		final String TEST_QUERY = "a";
		userFetcher.execute("search", TEST_QUERY);
		

		return view;
	}

}
