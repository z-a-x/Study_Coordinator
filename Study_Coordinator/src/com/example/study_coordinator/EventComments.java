package com.example.study_coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class EventComments extends Fragment implements OnClickListener{
    ListView listView;
    String result;
    CommentAdapter adapter;
    ArrayList<Comment> comments;
    String eventId;
    String userId;
    Button doButton;
    
    
 // newInstance constructor for creating fragment with arguments
    public static EventComments newInstance(String eventId, String userId) {
        EventComments fragmentFirst = new EventComments(eventId, userId);
        return fragmentFirst;
    }
    
    public EventComments(String eventId, String userId){
    	this.eventId = eventId;
    	this.userId = userId;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_event_comments, container, false);
        super.onCreate(savedInstanceState);       
        listView= (ListView) view.findViewById(R.id.lvComments);
        doButton = (Button) view.findViewById(R.id.doButton);
        doButton.setOnClickListener(this);
                
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
               
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
    
    
    @Override
    public void onClick(View v) {
    switch (v.getId()) {
        case R.id.doButton:
        	DialogFragment newFragment = new CommentDialogFragment(eventId, userId);
            newFragment.show(getFragmentManager(), "Comments");
            break;
        }   
    }
    
	
	class DataCollector extends AsyncTask<String, String, String> {
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();  
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
			String database_url =   dc.getIpAddress() + "getComments.php";
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("event_id", eventId));
			
        	String st = null;
            try
            {
              HttpClient httpclient = new DefaultHttpClient();
              HttpPost httppost = new HttpPost(database_url);
              httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
              HttpResponse response = httpclient.execute(httppost);
              st =  EntityUtils.toString(response.getEntity());     
            } 
            catch (IOException e) {
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
			
			comments = new ArrayList<Comment>();
		    
		    
		    String response = result.toString();
		    try {
                JSONObject jsonObj = new JSONObject(result);
                 
                // Getting JSON Array node
                JSONArray users = jsonObj.getJSONArray("comments");
                	
                
                // looping through All Contacts
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);
                    String username = c.getString("username");
                    String text = c.getString("text");
                    
                    System.out.println(c.getString("username"));
                    System.out.println(c.get("text"));
                    Comment com = new Comment(username, text);
                    
                    System.out.println(com.username+"     "+com.text);
                    comments.add(com);                    
		    }
                
            } catch (JSONException e) {
                e.printStackTrace();
		    
            }
		    System.out.println(response);	
		    
		    adapter = new CommentAdapter(getActivity(), comments);
		    System.out.println("velikost adapterja: "+adapter.getCount());
		    listView.setAdapter(adapter);
		}
	}


}
	
