package com.example.study_coordinator.tabs;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.study_coordinator.FragmentGroup;
import com.example.study_coordinator.GroupAdapter;
import com.example.study_coordinator.R;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.HasId;

public class TabGroups extends Fragment {

	public static Fragment newInstance(int page, String title, String argumentName, String argumentValue) {
		// On copy/paste CHANGE THIS !!!
		Fragment tab = new TabGroups();
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
		final FragmentActivity callingFragment = new FragmentGroup();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HasId baseClass = (HasId) listView.getItemAtPosition(position);

//				FragmentTransaction transaction = getFragmentManager().beginTransaction();
//				Bundle arguments = new Bundle();
//				arguments.putInt("id", baseClass.getId());
//				callingFragment.setArguments(arguments);
//				transaction.replace(R.id.content_frame, callingFragment);
//				transaction.addToBackStack(null);
//				transaction.commit();
//				getFragmentManager().executePendingTransactions();
				
				//TODO add id!				
				startActivity(new Intent(getActivity(), FragmentGroup.class));
			}
		});

		// On copy/paste CHANGE THIS !!!
		LookUp userFetcher = new LookUpGroups(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Group> groups = getGroups(result);
				ListAdapter adapter = new GroupAdapter(getActivity(), groups);
				listView.setAdapter(adapter);
			}
		};
		String argumentName = getArguments().getString("argumentName");
		String argumentValue = getArguments().getString("argumentValue");
		userFetcher.execute(argumentName, argumentValue);

		return view;
	}
}