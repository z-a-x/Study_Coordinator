package com.example.study_coordinator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Group;

public class GroupDetails extends Fragment {

	Integer id;
	
	String result;
	FriendAdapter adapter;

	// Store instance variables
	private String title;
	private int page;
	
	public GroupDetails(int id) {
		super();
		this.id = id;
	}

	// newInstance constructor for creating fragment with arguments
	public static GroupDetails newInstance(int page, String title, int id) {
		GroupDetails fragmentFirst = new GroupDetails(id);
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");

		final TextView tvGroupName = (TextView) view.findViewById(R.id.group_details_name_tv);

		LookUp userDetailsFetcher = new LookUpGroups(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				Group group = getGroups(result).get(0);
				tvGroupName.setText(tvGroupName.getText() + " " + group.name);
			}
		};
		userDetailsFetcher.execute("id", id.toString());

		return view;
	}

}
