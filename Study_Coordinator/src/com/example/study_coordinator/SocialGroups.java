
package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SocialGroups extends Fragment {
	// final ListView listView ;
	private static JSONParser jsonParser = new JSONParser();
	ListView listView;
	String result;
	GroupAdapter adapter;
	private ProgressDialog pDialog;
	String userId;
	

	// Store instance variables
	private String title;
	private int page;

	
	public SocialGroups(String userId){
		this.userId = userId;
	}
	
	// newInstance constructor for creating fragment with arguments
	public static SocialGroups newInstance(int page, String title, String userId) {
		SocialGroups fragmentFirst = new SocialGroups(userId);
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_social_groups, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		listView = (ListView) view.findViewById(R.id.lv_social_groups);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				Intent intent = new Intent(getActivity(), FragmentGroup.class);
				Group g = (Group) adapter.getItem(position);
				intent.putExtra("id", g.id + "");
				startActivity(intent);
				transaction.addToBackStack(null);
				transaction.commit();
				getFragmentManager().executePendingTransactions();
			}

		});

		LookUp groupFetcher = new LookUpGroups(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Group> groups = getGroups(result);
				String s = "(";
				for(Group g : groups){
					s+=g.id+",";
				}
				if (s.length() > 0 && s.charAt(s.length()-1)==',') {
				      s = s.substring(0, s.length()-1);
				    }
				s+=")";
				SessionManager session = new SessionManager(getActivity());
				session.setUserGroups(s);
				System.out.println("NASTAVIL MANAGER NA "+s);							
				adapter = new GroupAdapter(getActivity(), groups);
				listView.setAdapter(adapter);
			}
		};
		
		groupFetcher.execute("user_id", userId);
		
		
		return view;
	}

}
