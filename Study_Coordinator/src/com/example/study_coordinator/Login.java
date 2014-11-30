package com.example.study_coordinator;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.R.id;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private Button logIn, register;
	private EditText user, pass;
	private TextView title;
	
	// JSON parser class
	private static JSONParser jsonParser = new JSONParser();
	
	// server url (local computer)
	private static final String LOGIN_URL = "http://188.230.178.39:80/android_connect/login.php";
	
	//ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//setup buttons & title
		logIn = (Button)findViewById(R.id.btnSignIn);
		register = (Button)findViewById(R.id.btnRegister);
		title = (TextView)findViewById(id.textViewTitle);
		
		//setup custom fonts buttons & title
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Thin.ttf");
		register.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Medium.ttf");		
		logIn.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-LightItalic.ttf");		
		title.setTypeface(custom_font);
	
		//setup input fields
		user = (EditText)findViewById(R.id.editTextEmail);
		pass = (EditText)findViewById(R.id.editTextPass);	
		
		//register listeners
		//logIn.setOnClickListener((OnClickListener) this);
		//register.setOnClickListener((OnClickListener) this);

		

	}
	
	public void signIn(View v) {
		new AttemptLogin().execute();
	}
	
	/*
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSignIn:
				new AttemptLogin().execute();
			break;
		case R.id.btnRegister:
				Intent i = new Intent(this, Register.class);
				startActivity(i);
			break;

		default:
			break;
		}*
	}*/


	
	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
	    * Before starting background thread Show Progress Dialog
	    * */
		boolean failure = false;

		
	   @Override
	   protected void onPreExecute() {
	       super.onPreExecute();
	       pDialog = new ProgressDialog(Login.this);
	       pDialog.setMessage("Attempting login...");
	       pDialog.setIndeterminate(false);
	       pDialog.setCancelable(true);
	       pDialog.show();
	   }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
	       int success;
	       String username = user.getText().toString();
	       String password = pass.getText().toString();
	       try {
	           // Building Parameters
	           List<NameValuePair> params = new ArrayList<NameValuePair>();
	           params.add(new BasicNameValuePair("username", username));
	           params.add(new BasicNameValuePair("password", password));

	           Log.d("request!", "starting");
	           // getting server login authorization by making HTTP request
	           JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

	           // check your log for json response
	           Log.d("Login attempt", json.toString());

	           // json success tag
	           success = json.getInt(TAG_SUCCESS);
	           if (success == 1) {
	           	Log.d("Login Successful!", json.toString());
	           	Intent i = new Intent(Login.this, MainActivity.class);
	           	finish();
				startActivity(i);
	           	return json.getString(TAG_MESSAGE);
	           }else{
	           	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
	           	return json.getString(TAG_MESSAGE);

	           }
	       } catch (JSONException e) {
	           e.printStackTrace();
	       }

	       return null;

		}
		/**
	    * After completing background task Dismiss the progress dialog
	    * **/
	   protected void onPostExecute(String file_url) {
	       // dismiss the dialog once product deleted
	       pDialog.dismiss();
	       if (file_url != null){
	       	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
	       }

	   }

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
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

