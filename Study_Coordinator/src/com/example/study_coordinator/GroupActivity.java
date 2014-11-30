package com.example.study_coordinator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

public class GroupActivity extends Activity {

	public static final String JSON_TEST_STRING = "{" +
			"\"group_id\":\"123213\"," +
			"\"group_name\":\"Prvi A\"," +
			"\"user\":[" +
				"{\"user_id\":\"1232113\",\"user_name\":\"Mojca\",\"user_last_name\":\"Bohar\"}," +
				"{\"user_id\":\"4324324\",\"user_name\":\"Tjasa\",\"user_last_name\":\"Zitnik\"}," +
				"{\"user_id\":\"132213123\",\"user_name\":\"Maja\",\"user_last_name\":\"Pepelnik\"}" +
			"]," +
			"\"event\":[" +
				"{\"event_id\":\"23434\",\"event_name\":\"Po Mati\",\"location_id\":\"234342\"}" +
			"]" +
		"}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		
		try {
			JSONObject groupsJsonObj = new JSONObject(JSON_TEST_STRING);
			setGroupName(groupsJsonObj);
			createUserButtons(groupsJsonObj);
			createEventButtons(groupsJsonObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setGroupName(JSONObject groupsJsonObj) throws JSONException {
		String name = groupsJsonObj.getString("group_name");
		TextView textView = (TextView) this.findViewById(R.id.textViewGroupName);
		textView.setText(name);
	}
	
	private void createUserButtons(JSONObject groupsJsonObj) throws JSONException {
		// TODO: Tu in v naslednji metodi bo namesto WelcomeActivity.class ustrezen activity.
		createButtonsFromTable(groupsJsonObj, "user", "user_id", "user_name", WelcomeActivity.class, R.id.usersLayout);
	}
	
	private void createEventButtons(JSONObject groupsJsonObj) throws JSONException {
		createButtonsFromTable(groupsJsonObj, "event", "event_id", "event_name", WelcomeActivity.class, R.id.eventsLayout);
	}
	
	private void createButtonsFromTable(JSONObject groupsJsonObj, String tableName, 
			String idColumn, String nameColumn, Class<?> callsActivity, int layoutId) throws JSONException {
		LinearLayout layout = (LinearLayout) this.findViewById(layoutId);
	    JSONArray rows = groupsJsonObj.getJSONArray(tableName);
	    for(int i = 0 ; i < rows.length() ; i++){
	        JSONObject row = (JSONObject) rows.get(i);
	        String id = row.getString(idColumn); 
	        String name = row.getString(nameColumn); 
			createButton(id, name, callsActivity, layout);   
	    }    
	}
	
	private void createButton(String id, String name, Class<?> callsActivity, LinearLayout layout) {
		LayoutParams lparams = new LayoutParams(
				   LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		Button bt = new Button(this);
		bt.setLayoutParams(lparams);
		bt.setText(name);
		UserButtonListener btLis = new UserButtonListener(id, this, callsActivity);
		bt.setOnClickListener(btLis);
		layout.addView(bt);
	}
	
	class UserButtonListener implements View.OnClickListener {
		private String id;
		GroupActivity groupActivity;
		Class<?> callsActivity;
		public UserButtonListener(String id, GroupActivity groupActivity, Class<?> callsActivity) { 
			this.id = id; 
			this.groupActivity = groupActivity;
			this.callsActivity = callsActivity;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(groupActivity, callsActivity);
			Bundle bundle = new Bundle(); 
			bundle.putString("id", id);
			intent.putExtras(bundle);
			// TODO: Ko bosta user in event Activiti koncana, se ju bo tukaj klicalo.
			//startActivity(intent);
		}
	}

	//#######################################
	//############# B O I L E R #############
	//#######################################
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		return true;
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
