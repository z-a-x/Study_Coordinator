package com.example.study_coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.Login.AttemptLogin;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SyncStatusObserver;

public class FragmentSearch extends Fragment{
    //final ListView listView ;
    private static JSONParser jsonParser = new JSONParser();
    ListView listView;
    String result;
    FriendAdapter adapter;
    private ProgressDialog pDialog;
    ArrayList<Friend> friends;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_layout_search, container, false);
        super.onCreate(savedInstanceState);       
        
         listView= (ListView) view.findViewById(R.id.lvUsers);
        
                
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
               /*
               String  itemValue = (String) listView.getItemAtPosition(position);
                  
                // Show Alert 
                Toast.makeText(getActivity().getApplicationContext(),
                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  .show();
             
              */
              }
              

         }); 
        
        new DataCollector().execute();
	    //System.out.println("konèna velikost adapterja: "+adapter.getCount());
        
        
        return view;
    }
    
    
	
	class DataCollector extends AsyncTask<String, String, String> {
		
	
	
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();  
	        pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
	    }

		@Override
		protected String doInBackground(String... args) {		
			
			SessionManager session = new SessionManager(getActivity());
			String username = null;
			if (session.isLoggedIn()) {
				HashMap<String, String> pref = session.getUserDetails();
				username = pref.get(SessionManager.KEY_USERNAME);
				
		    }
			if(username != null){
				System.out.println("Dobil shared preferences!");
				System.out.println("Username is: "+username);
			}
			else{
				System.out.println("Didn't get shared preferences!");
			}
			DatabaseConnect dc = new DatabaseConnect();
			String database_url =   dc.getIpAddress() + "getUsers.php";
			
        	String st = null;
            try
            {
              HttpClient httpclient = new DefaultHttpClient();
              HttpPost httppost = new HttpPost(database_url);
              HttpResponse response = httpclient.execute(httppost);
              st =  EntityUtils.toString(response.getEntity());     
            } 
            catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                }
            
            
            
	            return st;
	            
	            
		}
		/**
	   * After completing background task Dismiss the progress dialog
	   * **/
		@Override
	    protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
			
			
			friends = new ArrayList<Friend>();
		    
		    
		    String response = result.toString();
		    try {
                JSONObject jsonObj = new JSONObject(result);
                 
                // Getting JSON Array node
                JSONArray users = jsonObj.getJSONArray("users");
                	
                
                // looping through All Contacts
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                    String user_name = c.getString("user_name");
                    String user_last_name = c.getString("user_last_name");
                    
                    System.out.println(c.getString("user_name"));
                    System.out.println(c.get("user_last_name"));
                    Friend f = new Friend(user_name, user_last_name);
                    System.out.println(f.userName+"     "+f.userLastName);
                    friends.add(f);                    
		    }
                
            } catch (JSONException e) {
                e.printStackTrace();
		    
            }
		    System.out.println(response);	
		    
		    adapter = new FriendAdapter(getActivity(), friends);
		    System.out.println("velikost adapterja: "+adapter.getCount());
		    listView.setAdapter(adapter);
		}
	}
	
}
