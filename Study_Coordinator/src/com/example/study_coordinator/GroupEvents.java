package com.example.study_coordinator;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.User;


public class GroupEvents extends Fragment {
	
	Integer id;
	
	ListView listView;
	
	public GroupEvents(int id) {
		super();
		this.id = id;
	}

	// newInstance constructor for creating fragment with arguments
	public static GroupEvents newInstance(int page, String title, int id) {
		GroupEvents fragmentFirst = new GroupEvents(id);
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_group_events, container, false);
		super.onCreate(savedInstanceState);
		listView = (ListView) view.findViewById(R.id.lv_group_events);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});

		LookUp eventFetcher = new LookUpEvents(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Event> users = getEvents(result);
				EventAdapter adapter = new EventAdapter(getActivity(), users);
				listView.setAdapter(adapter);
			}
		};
		eventFetcher.execute("group_id", id.toString());

		return view;
	}
}
