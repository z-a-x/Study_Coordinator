package com.example.study_coordinator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Group;

public class GroupDetails extends Fragment {

	public static Fragment newInstance(int page, String title, String argumentName, String argumentValue) {
		// On copy/paste CHANGE THIS !!!
		Fragment tab = new GroupDetails();
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
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		super.onCreate(savedInstanceState);
		int page = getArguments().getInt("someInt", 0);
		String title = getArguments().getString("someTitle");

		final TextView tvGroupName = (TextView) view.findViewById(R.id.group_details_name_tv);

		// On copy/paste CHANGE THIS !!!
		LookUp groupFetcher = new LookUpGroups(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				Group group = getGroups(result).get(0);
				tvGroupName.setText(tvGroupName.getText() + " " + group.name);
			}
		};

		String argumentName = getArguments().getString("argumentName");
		String argumentValue = getArguments().getString("argumentValue");
		groupFetcher.execute(argumentName, argumentValue);

		return view;

	}
}
