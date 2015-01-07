package com.example.study_coordinator;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.User;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.graphics.Color;

public class FragmentGroup extends FragmentTemplate {

	public static final String JSON_TEST_STRING = "{" + "\"group_id\":\"123213\","
			+ "\"group_name\":\"Prvi A\"," + "\"user\":["
			+ "{\"user_id\":\"1232113\",\"user_name\":\"Mojca\",\"user_last_name\":\"Bohar\"},"
			+ "{\"user_id\":\"4324324\",\"user_name\":\"Tjasa\",\"user_last_name\":\"Zitnik\"},"
			+ "{\"user_id\":\"132213123\",\"user_name\":\"Maja\",\"user_last_name\":\"Pepelnik\"}" + "],"
			+ "\"event\":["
			+ "{\"event_id\":\"23434\",\"event_name\":\"Po Mati\",\"location_id\":\"234342\"}" + "]" + "}";

	@Override
	protected void initialize() {
		try {
			initializeTabs();

			JSONObject groupsJsonObj = new JSONObject(JSON_TEST_STRING);
			setTitle("Grupa " + id);

			createUserList();
			createEventList();

			// EX:
			// createUserButtons(groupsJsonObj);
			// createEventButtons(groupsJsonObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// ///// TABS ////////

	private void initializeTabs() {
		TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		spec1.setIndicator("ABOUT");
		spec1.setContent(R.id.tabAbout);

		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("EVENTS");
		spec2.setContent(R.id.tabEvents);

		TabSpec spec3 = tabHost.newTabSpec("Tab 3");
		spec3.setIndicator("USERS");
		spec3.setContent(R.id.tabMembers);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);

		setTabListener();
	}

	/**
	 * Da spremeni barvo naslovov tabov
	 */
	private void setTabListener() {
		final TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
					TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
							.findViewById(android.R.id.title); // Unselected Tabs
					tv.setTextColor(Color.parseColor("#000000"));
				}
				TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); // for
				tv.setTextColor(Color.parseColor("#ffffff"));
			}
		});
	}

	@Override
	public void onResume() {
		final TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			tv.setTextSize(26);
			if (i == 0) {
				tv.setTextColor(Color.parseColor("#ffffff"));
			}
		}
		super.onResume();
	}

	// ///// CREATE LISTS ////////

	private void createUserList() {
		LookUp userFetcher = new LookUpUsers(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> users = getUsers(result);
				for (User user : users) {
					createButton(((Integer) user.id).toString(), user.userName, new FragmentProfilJure(),
							(LinearLayout) getView().findViewById(R.id.usersLayout));
				}
			}
		};
		userFetcher.execute();
	}

	private void createEventList() {
		LookUp eventFetcher = new LookUpEvents(getActivity().getApplicationContext()) {
			
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Event> events = getEvents(result);
				for (Event event : events) {
					createButton(((Integer) event.id).toString(), event.name, new FragmentEventJure(),
							(LinearLayout) getView().findViewById(R.id.eventsLayout));
				}
			}
		};
		eventFetcher.execute();

	}

	// ////////////////////

	private void createUserButtons(JSONObject groupsJsonObj) throws JSONException {
		createButtonsFromTable(groupsJsonObj, "user", "user_id", "user_name", new FragmentProfilJure(),
				R.id.usersLayout);
	}

	private void createEventButtons(JSONObject groupsJsonObj) throws JSONException {
		createButtonsFromTable(groupsJsonObj, "event", "event_id", "event_name", new FragmentEventJure(),
				R.id.eventsLayout);
	}

	private void createButtonsFromTable(JSONObject groupsJsonObj, String tableName, String idColumn,
			String nameColumn, Fragment targetFragment, int layoutId) throws JSONException {
		LinearLayout layout = (LinearLayout) getView().findViewById(layoutId);
		JSONArray rows = groupsJsonObj.getJSONArray(tableName);
		for (int i = 0; i < rows.length(); i++) {
			JSONObject row = (JSONObject) rows.get(i);
			String id = row.getString(idColumn);
			String name = row.getString(nameColumn);
			createButton(id, name, targetFragment, layout);
		}
	}

	@SuppressLint("NewApi")
	private void createButton(String id, String name, Fragment targetFragment, LinearLayout layout) {
		LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		Button bt = new Button(getActivity());
		bt.setLayoutParams(lparams);
		bt.setText(name);
		UserButtonListener btLis = new UserButtonListener(id, targetFragment);
		bt.setOnClickListener(btLis);
		layout.addView(bt);
	}

	class UserButtonListener implements View.OnClickListener {
		private String id;
		private Fragment fragment;

		public UserButtonListener(String id, Fragment fragment) {
			this.id = id;
			this.fragment = fragment;
		}

		@Override
		public void onClick(View v) {
			openFragment(id, fragment);
		}
	}

	private void openFragment(String id, Fragment fragment) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Bundle arguments = new Bundle();
		arguments.putString("id", id);
		fragment.setArguments(arguments);
		transaction.replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
		// NEW (da dela back button)
		getFragmentManager().executePendingTransactions();
	}

	// #######################################
	// ############# B O I L E R #############
	// #######################################

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_group, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
