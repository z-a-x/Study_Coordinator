package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivityWithDrawer {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;
	private String s1;
	String username;

	public String getS1() {
		return s1;
	}

	// JSON parser class
	private static JSONParser jsonParser = new JSONParser();

	// server url (local computer)
	private static final DatabaseConnect databaseConnect = new DatabaseConnect();
	private static final String DATABASE_URL = databaseConnect.getIpAddress() + "DataCollector.php";

	// Session manager class
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent itnt = getIntent();
		String message = itnt.getStringExtra("update");
		if (message != null) {
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Session manager class
		session = new SessionManager(getApplicationContext());
		setDrawer();
	}

	@Override
	public void setTitle(CharSequence title) {
		this.drawerTitle = title;
		getActionBar().setTitle(title);
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		drawerLayout.openDrawer(drawerList);
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		FragmentManager fragmentManager = getFragmentManager();
		if (fragmentManager.getBackStackEntryCount() != 0) {
			fragmentManager.popBackStack();
		} else {
			super.onBackPressed();
		}
	}

	// ////////////////////////////////
	// //// DATA COLLECTOR CLASS //////
	// ////////////////////////////////

	class DataCollector extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			String user_id = null;
			String userName = null;
			String userLastName = null; //
			String email = null;
			String pathToPicture = null;
			Intent intent = getIntent();

			SessionManager session = new SessionManager(getApplicationContext());
			HashMap<String, String> pref = session.getUserDetails();
			username = pref.get(SessionManager.KEY_USERNAME);

			try {

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));

				Log.d("Request for data!", "starting");
				// getting server login authorization by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(DATABASE_URL, "POST", params);

				// check your log for json response
				System.out.println("MAIN ACTIVITY CLASS");
				Log.d("Retrieving data attempt", json.toString());
				user_id = json.getInt("user_id") + "";
				System.out.println("----------------------");
				System.out.println("pravi user id je: " + user_id);
				System.out.println("----------------------");
				session.setUserId(user_id);
				userName = (String) json.get("user_name");
				userLastName = (String) json.get("user_last_name");
				email = (String) json.get("email");

				if (json.isNull("user_avatar")) {

					pathToPicture = "NULL";
				} else {
					pathToPicture = (String) json.get("user_avatar");

				}

				System.out.println(json.get("username"));
				System.out.println(userName);
				System.out.println(userLastName);
				System.out.println(email);
				System.out.println(pathToPicture);

				if (pathToPicture == null || pathToPicture.equals("null")) {
					System.out.println("NI SLIKE");

				} else {
					System.out.println("SLIKAAAAAAAAA " + pathToPicture);
				}
				s1 = "" + userName + " " + userLastName + " " + username + " " + email + " " + pathToPicture;
			} catch (JSONException e) {
				System.out.println("Connection failed!");
				e.printStackTrace();
			}
			return "" + userName + " " + userLastName + " " + username + " " + email + " " + pathToPicture;
		}

		private void doNotCheckLoginData() {
			Intent i = new Intent(MainActivity.this, MainActivity.class);
			finish();
			startActivity(i);
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			/*
			 * pDialog.dismiss(); if (file_url != null){ Toast.makeText(MainActivity.this, file_url,
			 * Toast.LENGTH_LONG).show(); }
			 */
		}
	}
}