package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

	EditText pass;
	EditText repeatPassword;
	EditText user;
	
	 // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    private static final DatabaseConnect databaseConnect = new DatabaseConnect();
    private static final String LOGIN_URL = databaseConnect.getIpAddress() + "register.php";
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		user  = (EditText) findViewById(R.id.editTextEmail);
		pass =  (EditText) findViewById(R.id.editTextPass);
		repeatPassword = (EditText) findViewById(R.id.EditTextPass2);
		Button registerBtn = (Button) findViewById(R.id.btnRegister);
		Button backBtn = (Button) findViewById(R.id.backLogin);
		EditText email = (EditText) findViewById(R.id.editTextEmail);
		TextView registerText = (TextView) findViewById(R.id.textView1);
		
		//setup custom fonts buttons & title
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Thin.ttf");
		backBtn.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Medium.ttf");		
		pass.setTypeface(custom_font);
		repeatPassword.setTypeface(custom_font);
		registerBtn.setTypeface(custom_font);
		email.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-LightItalic.ttf");		
		registerText.setTypeface(custom_font);
				
		repeatPassword.addTextChangedListener(new TextWatcher() {
			
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (pass.getText().toString().equals(repeatPassword.getText().toString())) {
				repeatPassword.setBackgroundColor(getResources().getColor(R.color.passwordMatch));
			}
			else {
				repeatPassword.setBackgroundColor(getResources().getColor(R.color.passwordFalse));
			}
			
		}
			
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			repeatPassword.setBackgroundColor(Color.RED);
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
		 
		 });
			}
		public void registerUser(View v) {
			if (pass.getText().toString().equals(repeatPassword.getText().toString())) {
				//Toast.makeText(Register.this, "Registering", Toast.LENGTH_LONG).show();
				new CreateUser().execute();
			}
			else {
				//Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_LONG).show();
			}
	}
		
		public void backLogin(View v) {
			this.finish();
			this.overridePendingTransition(R.anim.slide_in_left,
	                R.anim.slide_in_right);
		}
		

		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{
		    if (keyCode == KeyEvent.KEYCODE_BACK ) {
		    	
		    	this.finish();
				this.overridePendingTransition(R.anim.slide_in_left,
		                R.anim.slide_in_right);
		        return false;
		    }     
		    return super.onKeyDown(keyCode, event);    
		}
		
		class CreateUser extends AsyncTask<String, String, String> {

			 /**
	         * Before starting background thread Show Progress Dialog
	         * */
			boolean failure = false;

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Register.this);
	            pDialog.setMessage("Creating User...");
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

	                //Posting user data to script
	                JSONObject json = jsonParser.makeHttpRequest(
	                       LOGIN_URL, "POST", params);

	                // full json response
	                Log.d("Login attempt", json.toString());

	                // json success element
	                success = json.getInt(TAG_SUCCESS);
	                if (success == 1) {
	                	Log.d("User Created!", json.toString());
	                	finish();
	                	return json.getString(TAG_MESSAGE);
	                }else{
	                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
	                	return json.getString(TAG_MESSAGE);

	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	                cancel(failure);
	            } catch (Exception e) {
					cancel(failure);
				}

	            return null;

			}
			
			protected void onCancelled() {
				Toast.makeText(Register.this, "Network error!", Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			}
			
			 /* After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	            pDialog.dismiss();
	            if (file_url != null){
	            	Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
	            }

	        }
		}

		
}
