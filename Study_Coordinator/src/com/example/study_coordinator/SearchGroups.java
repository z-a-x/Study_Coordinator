package com.example.study_coordinator;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Group;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchGroups extends Fragment {
	// final ListView listView ;
	private static JSONParser jsonParser = new JSONParser();
	ListView listView;
	String result;
	GroupAdapter adapter;
	private ProgressDialog pDialog;

	// Store instance variables
	private String title;
	private int page;

	// newInstance constructor for creating fragment with arguments
	public static SearchGroups newInstance(int page, String title) {
		SearchGroups fragmentFirst = new SearchGroups();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_search_groups, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		listView = (ListView) view.findViewById(R.id.lv_search_groups);

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

		LookUp groupFetcher = new LookUpGroups(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Group> groups = getGroups(result);
				adapter = new GroupAdapter(getActivity(), groups);
				System.out.println("velikost adapterja: " + adapter.getCount());
				listView.setAdapter(adapter);
			}
		};
		groupFetcher.execute();
		
		return view;
	}

}
