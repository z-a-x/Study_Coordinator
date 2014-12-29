package com.example.study_coordinator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProfilEdit extends Activity {
	EditText etUsername;
    EditText etUserName;
    EditText etUserLastName;
    TextView tvUsername;
    TextView tvUserName;
    TextView tvUserLastName;
    Button btEditProfil;
    private ProgressDialog pDialog;
    String username;
	String userName;
	String userLastName;
    
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
	private static final DatabaseConnect databaseConnect = new DatabaseConnect();
	private static final String PROFILE_URL =  databaseConnect.getIpAddress() + "updateProfil.php";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil_edit);
		
		etUsername = (EditText)findViewById(R.id.frag_profil_edit_email_et);
		etUserName = (EditText)findViewById(R.id.frag_profil_edit_name_et);
    	etUserLastName = (EditText)findViewById(R.id.frag_profil_edit_surname_et);
    	
    	tvUsername = (TextView)findViewById(R.id.frag_profil_edit_email_tv);
    	tvUserName = (TextView)findViewById(R.id.frag_profil_edit_name_tv);
    	tvUserLastName = (TextView)findViewById(R.id.frag_profil_edit_surname_tv);    	
    	
    	Intent intent = getIntent();
		String recivedData = intent.getExtras().getString("data");
        String[] parsedData = recivedData.split(" "); 
        username = parsedData[0];
        userName = parsedData[1];
        userLastName = parsedData[2];
        
        etUsername.setText(username);
        etUserName.setText(userName);
        etUserLastName.setText(userLastName);
        
        btEditProfil = (Button) findViewById(R.id.profil_editprofile_bt);
        btEditProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {             	
	            AsyncTask<String, String, String> result = new DataSender().execute();
	            
	            try {
	            	Intent i = new Intent(ActivityProfilEdit.this, MainActivity.class);
		            i.putExtra("update", result.get());
					System.out.println("RESULT: "+result.get());
					finish();
		  			startActivity(i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
				}
             	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profil_edit, menu);
		return true;
	}
	
	class DataSender extends AsyncTask<String, String, String>{
		
		@Override
		   protected void onPreExecute() {
		       super.onPreExecute();
		       pDialog = new ProgressDialog(ActivityProfilEdit.this);
		       pDialog.setMessage("Updating data...");
		       pDialog.setIndeterminate(false);
		       pDialog.setCancelable(true);
		       pDialog.show();
		   }
		   
		
		@Override		
		public String doInBackground(String... args) {
			Looper.prepare();

	    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	System.out.println("POŠILJAM PODATKE "+ username+ " "+userName+" "+userLastName);
	    	
	    	Editable username = etUsername.getText();
	    	Editable userName = etUserName.getText();
	    	Editable userLastName = etUserLastName.getText();
	    	
	    	nameValuePairs.add(new BasicNameValuePair("username",username.toString()));
	    	nameValuePairs.add(new BasicNameValuePair("userName",userName.toString()));
	    	nameValuePairs.add(new BasicNameValuePair("userLastName", userLastName.toString()));
	    	
	    	try{
	    		
	    		HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(PROFILE_URL);
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost); 
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        Log.e("pass 1", "connection success ");
		        System.out.println("Uspel dostop do baze!");
	    	}
	    	
	        catch(Exception e)
	    	{
	        	System.out.println("Dostop do baze ni uspel!");
	        	Log.e("Fail 1", e.toString());
		    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
				Toast.LENGTH_LONG).show();
	    	}     
	        
	        try {
	    		BufferedReader reader = new BufferedReader
					(new InputStreamReader(is,"iso-8859-1"),8);
	            	StringBuilder sb = new StringBuilder();
	            	
	            	while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            	}
	            	
	            	is.close();
	            	result = sb.toString();
	            	Log.e("pass 2", "connection success ");
	            	System.out.println("Uspešno sestavljeno sporoèilo");
	            	
	        }
	        catch(Exception e){
	        	Log.e("Fail 2", e.toString());
	    	}     
	       
	        try {
	        	JSONObject json_data = new JSONObject(result);
	        	code=(json_data.getInt("code"));
				System.out.println("VALUE OF CODE IS: "+code);
	        	if(code==1){	        		
	        		Toast.makeText(getBaseContext(), "Update Successfully",
					Toast.LENGTH_LONG).show();
	        		System.out.println("Uspešen zapis v bazo");
	        		//Toast.makeText(getApplicationContext(), "Successful update!", 2000).show();
	        		
	        		
	        	}
	        	else{
        			Toast.makeText(getBaseContext(), "Sorry, Try Again",
					Toast.LENGTH_LONG).show();
        			System.out.println("Zapis v bazo ni uspel!");
	        	}
	    	}
	        catch(Exception e){
	        	Log.e("Fail 3", e.toString());
	        	System.out.println("Postopek ni uspel zaradi drugih napak");
	    	}
	   		return "Successful update";
	    }
		protected void onPostExecute(String file_url) {
		      // dismiss the dialog once product deleted
			  
		      pDialog.dismiss();
		      
			
		  }
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
