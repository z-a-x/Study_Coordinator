package com.example.study_coordinator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.Login.AttemptLogin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
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

public class MainActivity extends FragmentActivity {

	private String[] drawerListViewItems;
    private ListView drawerListView;
    private CharSequence drawerTitle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;    
    private ActionBarDrawerToggle drawerToggle;
    CustomDrawerAdapter adapter;
    List<DrawerItem> dataList;
    private String s1;
    
    public String getS1(){
    	return s1;
    }
            
    // JSON parser class
 	private static JSONParser jsonParser = new JSONParser();
 	
 	// server url (local computer)
 	private static final String DATABASE_URL = "http://192.168.1.78:80/Study_Coordinator/android_connect/DataCollector.php"; 	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
						
		Intent itnt = getIntent();		
		String message = itnt.getStringExtra("update");
		if(message != null){
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		}
		
		drawerTitle = getTitle();
		dataList = new ArrayList<DrawerItem>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);		
		
		dataList.add(new DrawerItem(getResources().getString(R.string.my_groups), R.drawable.ic_action_groups));		
		dataList.add(new DrawerItem(getResources().getString(R.string.events), R.drawable.ic_action_events));
		dataList.add(new DrawerItem(getResources().getString(R.string.profil), R.drawable.ic_action_person));
		dataList.add(new DrawerItem(getResources().getString(R.string.settings), R.drawable.ic_action_setting));
		dataList.add(new DrawerItem("Groups Jure", R.drawable.ic_action_setting));
		
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
		drawerList.setAdapter(adapter);
		
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        
        //AttemptLogin al = (AttemptLogin)
        
	}
	
	public void onDrawerClosed(View view) {
        getActionBar().setTitle(drawerTitle);
        invalidateOptionsMenu(); // creates call to
                                                  // onPrepareOptionsMenu()
	}

	public void onDrawerOpened(View drawerView) {
		getActionBar().setTitle(drawerTitle);
        invalidateOptionsMenu(); // creates call to
                                              // onPrepareOptionsMenu()
	}
	
	public void selectItem(int position){		
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch(position){
			case 0:								
				fragment = instantiateFragment(FragmentGroups.class, position, args);
				break;
				
			case 1:								
				fragment = instantiateFragment(FragmentEvents.class, position, args);
				break;
				
			case 2:								
				fragment = instantiateFragment(FragmentProfil.class, position, args);
				new DataCollector().execute();
		        
		        try {
					this.s1 = new DataCollector().execute().get();
					System.out.println("Data successfully returned: "+s1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case 3:								
				fragment = instantiateFragment(FragmentSettings.class, position, args);
				break;		
				
			case 4:								
				fragment = instantiateFragment(FragmentGroupsJure.class, position, args);
				break;	
				
			default:
				break;
		}		 
		fragment.setArguments(args);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.commit();
		
		
		drawerList.setItemChecked(position, true);
		setTitle(dataList.get(position).getItemName());
		drawerLayout.closeDrawer(drawerList);
	}
	
	private Fragment instantiateFragment(Class<?> fragmentClass, int position, Bundle args) {
		Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());
		String name =  getFragmentName(fragmentClass);
		args.putString(name, dataList.get(position).getItemName());
		args.putInt(FragmentGroups.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());
		return fragment;
	}
	
	private String getFragmentName(Class<?> fragmentClass) {
		String name = "";
		try {
			Field f = fragmentClass.getDeclaredField("ITEM_NAME");
			name = (String) f.get(null);
		} catch (Exception e) {
			name = "undefined";
			e.printStackTrace();
		}
		return name;
	}
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	        drawerLayout.closeDrawer(drawerListView);
	    }
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class DataCollector extends AsyncTask<String, String, String> {
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();  
	    }

		@Override
		protected String doInBackground(String... args) {
			
			String userName = null;
			String userLastName = null;			//
			Intent intent = getIntent();
			String username = intent.getStringExtra("username");
			//System.out.println(username);
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));

				Log.d("request!", "starting");
				// getting server login authorization by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(DATABASE_URL, "POST",params);

				// check your log for json response
				Log.d("Login attempt", json.toString());
				
				userName = (String) json.get("user_name");
				userLastName = (String) json.get("user_last_name");
				System.out.println(json.get("username"));
				System.out.println(userName);
				System.out.println(userLastName);
				s1 = ""+username+" "+userName+" "+userLastName;				
			} 
			catch (JSONException e) {
				System.out.println("Connection failed!");
				e.printStackTrace();
			}
			return ""+username+" "+userName+" "+userLastName;			
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
	      pDialog.dismiss();
	      if (file_url != null){
	      	Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
	      }
		*/
	  }
	}
}