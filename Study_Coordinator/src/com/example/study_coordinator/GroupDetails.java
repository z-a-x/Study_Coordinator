package com.example.study_coordinator;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study_coordinator.asynctasks.JoinGroup;
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
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		int page = getArguments().getInt("someInt", 0);
		String title = getArguments().getString("someTitle");
		String groupIdString = getArguments().getString("argumentValue");
		int groupId = Integer.valueOf(groupIdString);

		populateGroupDetails(view);
		addJoinButtonIfNecesary(view, groupId);

		return view;
	}

	// GROUP DETAILS:
	private void populateGroupDetails(View view) {
		final TextView tvGroupName = (TextView) view.findViewById(R.id.group_details_name_tv);

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
	}

	// JOIN GROUP BUTTON:
	private void addJoinButtonIfNecesary(final View view, final Integer groupId) {

		// Check if user is part of group:
		LookUp groupFetcher = new LookUpGroups(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Group> groups = getGroups(result);
				boolean isMember = false;
				for (Group group : groups) {
					if (group.id == groupId) {
						isMember = true;
						break;
					}
				}
				if (!isMember) {
					addJoinButton(view, groupId);
				}
			}
		};
		groupFetcher.execute("user_id", getUserId());
	}

	private String getUserId() {
		SessionManager session = new SessionManager(getActivity());
		Map<String, String> pref = session.getUserDetails();
		return pref.get("user_id");
	}

	private void addJoinButton(View view, Integer groupId) {
		final LinearLayout layout = (LinearLayout) view.findViewById(R.id.group_details_layout);
		// create button
		Button b = new Button(getActivity());

		LayoutParams lparams = new LayoutParams(40, LayoutParams.WRAP_CONTENT);
		lparams.gravity = 17;
		lparams.topMargin = 40;
		lparams.width = 160;
		lparams.height = LayoutParams.WRAP_CONTENT;
		b.setLayoutParams(lparams);
		b.setBackgroundDrawable(getResources().getDrawable(R.drawable.singin));

		b.setTextColor(Color.BLACK);
		b.setText("Join group");
		layout.addView(b);		
		System.out.println("#### added button to group details");
		// add listener
		JoinButtonListener buttonListener = new JoinButtonListener(groupId);
		b.setOnClickListener(buttonListener);
	}
	
	class JoinButtonListener implements View.OnClickListener {
		private Integer groupId;
		
		public JoinButtonListener(Integer groupId) {
			this.groupId = groupId;
		}

		@Override
		public void onClick(View v) {
			LookUp groupJoiner = new JoinGroup(getActivity()) {
				
				@Override
				public void onSuccessfulFetch(JSONObject result) throws JSONException {
					// Print message
					Toast.makeText(getActivity(), "You have joined the group.", Toast.LENGTH_SHORT).show();
					// Reload group page
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					Intent intent = new Intent(getActivity(), FragmentGroup.class);
					intent.putExtra("id", groupId.toString());
					startActivity(intent);
					transaction.addToBackStack(null);
					transaction.commit();
					getFragmentManager().executePendingTransactions();
				}
			};
			groupJoiner.execute("group_id", groupId.toString(), "user_id", getUserId());
			
		}
	}
	


}



















