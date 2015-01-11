package com.example.study_coordinator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivityWithDrawer {

	private String[] drawerListViewItems;
	private ListView drawerListView;
	private CharSequence drawerTitle;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
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

//		OLD DRAWER COMMENTED OUT (now replaced with new one, with setDrawer() call):
//		
//		drawerTitle = getTitle();
//		dataList = new ArrayList<DrawerItem>();
//		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		drawerList = (ListView) findViewById(R.id.left_drawer);

//		dataList.add(new DrawerItem(getResources().getString(R.string.search), R.drawable.ic_action_search));
//		dataList.add(new DrawerItem(getResources().getString(R.string.events), R.drawable.ic_action_events));
//		dataList.add(new DrawerItem(getResources().getString(R.string.profil), R.drawable.ic_action_person));
//		dataList.add(new DrawerItem(getResources().getString(R.string.social), R.drawable.ic_action_group));
//		dataList.add(new DrawerItem("Groups Jure", R.drawable.ic_action_setting));
//		dataList.add(new DrawerItem(getResources().getString(R.string.logout), R.drawable.ic_action_setting));
//		dataList.add(new DrawerItem("Event test", R.drawable.ic_action_setting));

//		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
//		drawerList.setAdapter(adapter);

//		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
//		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// AttemptLogin al = (AttemptLogin)

		// Session manager class
		session = new SessionManager(getApplicationContext());
        setDrawer();
	}

//	OLD DRAWER METHODS COMMENTED OUT (new code is in FragmentActivityWithDrawer.java):
//
//	public void onDrawerClosed(View view) {
//		getActionBar().setTitle(drawerTitle);
//		invalidateOptionsMenu(); // creates call to
//									// onPrepareOptionsMenu()
//	}
//
//	public void onDrawerOpened(View drawerView) {
//		getActionBar().setTitle(drawerTitle);
//		invalidateOptionsMenu(); // creates call to
//									// onPrepareOptionsMenu()
//	}
//
//	public void selectItem(int position) {
//		Fragment fragment = null;
//		Bundle args = new Bundle();
//		boolean logout = false;
//		boolean social = false;
//		boolean event = false;
//		switch (position) {
//		case 0:
//			//fragment = instantiateFragment(FragmentSearch.class, position, args);
//			//break;
//			logout = true;
//			//fragment = instantiateFragment(FragmentEvent.class, position, args);
//			Intent intentSearch = new Intent(this, FragmentSearch.class);
//			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
//			startActivity(intentSearch);
//            finish();
//            
//			break;
//
//		case 1:
//			event = true;
//			Intent intentEvents = new Intent(this, FragmentUpcomingEvents.class);
//			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
//			startActivity(intentEvents);
//			finish();
//						
//			break;
//
//		case 2:
//			new DataCollector().execute();
//			event=true;
//			Intent intentProfil = new Intent(this, FragmentProfil.class);
//			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
//			startActivity(intentProfil);
//			finish();
//			/*
//			fragment = instantiateFragment(FragmentProfil.class, position, args);
//			
//			
//
//			try {
//				this.s1 = new DataCollector().execute().get();
//				System.out.println("Data successfully returned: " + s1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			fragment.setArguments(args);
//			FragmentManager fm = getFragmentManager();
//			FragmentTransaction fragmentTransaction = fm.beginTransaction();
//			fragmentTransaction.replace(R.id.content_frame, fragment);
//			fragmentTransaction.commit();
//			*/
//			break;
//
//		case 3:
//			social = true;
//			Intent i = new Intent(this, FragmentSocial.class);			
//			startActivity(i);
//			break;
//		case 4:
//			fragment = instantiateFragment(FragmentGroupsJure.class, position, args);
//			break;
//
//		case 5:
//			session.logoutUser();
//			logout = true;
//			Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_SHORT).show();
//			finish();
//			break;
//			
//		case 6:
//			logout = true;
//			//fragment = instantiateFragment(FragmentEvent.class, position, args);
//			Intent intent = new Intent(this, FragmentEvent.class);
//			intent.putExtra("selected_event", "5");
//			overridePendingTransition(R.anim.fadeout, R.anim.fadein);
//			startActivity(intent);
//            finish();
//            
//			break;
//			
//		default:
//			break;
//		}
//		if (!logout && !social && !event) {
//			
//			fragment.setArguments(args);
//			FragmentManager fm = getFragmentManager();
//			FragmentTransaction fragmentTransaction = fm.beginTransaction();
//			fragmentTransaction.replace(R.id.content_frame, fragment);
//			fragmentTransaction.commit();
//			 
//		}
//		drawerList.setItemChecked(position, true);
//		setTitle(dataList.get(position).getItemName());
//		drawerLayout.closeDrawer(drawerList);
//	}
//	
//	private class DrawerItemClickListener implements ListView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView parent, View view, int position, long id) {
//			selectItem(position);
//			drawerLayout.closeDrawer(drawerListView);
//		}
//	}

	
//	OLD CODE FOR INSTIATING FRAGMENTS:
//
//	private Fragment instantiateFragment(Class<?> fragmentClass, int position, Bundle args) {
//		Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());
//		String name = getFragmentName(fragmentClass);
//		args.putString(name, dataList.get(position).getItemName());
//		args.putInt(FragmentGroups.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());
//		return fragment;
//	}
//
//	private String getFragmentName(Class<?> fragmentClass) {
//		String name = "";
//		try {
//			Field f = fragmentClass.getDeclaredField("ITEM_NAME");
//			name = (String) f.get(null);
//		} catch (Exception e) {
//			name = "undefined";
//			e.printStackTrace();
//		}
//		return name;
//	}



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
				user_id = json.getInt("user_id")+"";							
				System.out.println("----------------------");
				System.out.println("pravi user id je: "+user_id);
				System.out.println("----------------------");
				session.setUserId(user_id);
				userName = (String) json.get("user_name");
				userLastName = (String) json.get("user_last_name");
				email = (String) json.get("email");
				
				if(json.isNull( "user_avatar" )){
					
					pathToPicture="NULL";
				}
				else{
					pathToPicture = (String)json.get("user_avatar");
					
				}
				
				System.out.println(json.get("username"));
				System.out.println(userName);
				System.out.println(userLastName);
				System.out.println(email);
				System.out.println(pathToPicture);
				
				if(pathToPicture == null || pathToPicture.equals("null")){
					System.out.println("NI SLIKE");
					
				}
				else{
					System.out.println("SLIKAAAAAAAAA "+pathToPicture);					
				}
				s1 = "" + userName + " " + userLastName + " " + username + " " + email+" "+pathToPicture;
			} catch (JSONException e) {
				System.out.println("Connection failed!");
				e.printStackTrace();
			}
			return "" + userName + " " + userLastName + " " + username + " " + email+" "+pathToPicture;
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