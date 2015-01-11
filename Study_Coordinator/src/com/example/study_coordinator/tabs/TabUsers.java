package com.example.study_coordinator.tabs;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.study_coordinator.FragmentEvent;
import com.example.study_coordinator.FragmentGroup;
import com.example.study_coordinator.FragmentSocial;
import com.example.study_coordinator.FriendAdapter;
import com.example.study_coordinator.R;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.HasId;
import com.example.study_coordinator.baseclasses.User;

public class TabUsers extends Fragment {
	ListAdapter adapter;

	public static Fragment newInstance(int page, String title, String argumentName, String argumentValue) {
		// On copy/paste CHANGE THIS !!!
		Fragment tab = new TabUsers();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		args.putString("argumentName", argumentName);
		args.putString("argumentValue", argumentValue);
		tab.setArguments(args);
		return tab;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab_with_listview, container, false);
		super.onCreate(savedInstanceState);
		final ListView listView = (ListView) view.findViewById(R.id.listview);

		// On copy/paste CHANGE THIS !!!
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				Intent intent = new Intent(getActivity(), FragmentSocial.class);
				User u = (User) adapter.getItem(position);
		        intent.putExtra("selected_user", u.id+"");
		        System.out.println("----------------");
				System.out.println("Razred TabsUsers");
				System.out.println("Pošiljam userja: "+u.id+"");
				System.out.println("----------------");
		        startActivity(intent);
				transaction.addToBackStack(null);
				transaction.commit();
				getFragmentManager().executePendingTransactions();
			}
		});

		// On copy/paste CHANGE THIS !!!
		LookUp userFetcher = new LookUpUsers(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> users = getUsers(result);
				adapter = new FriendAdapter(getActivity(), users);
				listView.setAdapter(adapter);
			}
		};
		String argumentName = getArguments().getString("argumentName");
		String argumentValue = getArguments().getString("argumentValue");
		userFetcher.execute(argumentName, argumentValue);

		return view;
	}
}
