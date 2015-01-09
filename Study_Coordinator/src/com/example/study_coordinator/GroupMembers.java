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
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.User;

public class GroupMembers extends Fragment {

	Integer id;
	
	ListView listView;
	
	public GroupMembers(int id) {
		super();
		this.id = id;
	}

	// newInstance constructor for creating fragment with arguments
	public static GroupMembers newInstance(int page, String title, int id) {
		GroupMembers fragmentFirst = new GroupMembers(id);
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_group_members, container, false);
		super.onCreate(savedInstanceState);
		listView = (ListView) view.findViewById(R.id.lv_group_members);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
		

		System.out.println("#### Created Group members");

		LookUp userFetcher = new LookUpUsers(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> users = getUsers(result);
				System.out.println("#### "+users.get(0).name);
				FriendAdapter adapter = new FriendAdapter(getActivity(), users);
				listView.setAdapter(adapter);
			}
		};
		userFetcher.execute("group_id", id.toString());

		return view;
	}
}
