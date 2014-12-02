package com.example.study_coordinator;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

	EditText password;
	EditText repeatPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		password =  (EditText) findViewById(R.id.editTextPass);
		repeatPassword = (EditText) findViewById(R.id.EditTextPass2);
		Button registerBtn = (Button) findViewById(R.id.btnRegister);
		Button backBtn = (Button) findViewById(R.id.backLogin);
		EditText email = (EditText) findViewById(R.id.editTextEmail);
		TextView registerText = (TextView) findViewById(R.id.textView1);
		
		//setup custom fonts buttons & title
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Thin.ttf");
		backBtn.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Medium.ttf");		
		password.setTypeface(custom_font);
		repeatPassword.setTypeface(custom_font);
		registerBtn.setTypeface(custom_font);
		email.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-LightItalic.ttf");		
		registerText.setTypeface(custom_font);
				
		repeatPassword.addTextChangedListener(new TextWatcher() {
			
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (password.getText().toString().equals(repeatPassword.getText().toString())) {
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
			if (password.getText().toString().equals(repeatPassword.getText().toString())) {
				Toast.makeText(Register.this, "Registering", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_LONG).show();
			}
	}
		
		public void backLogin(View v) {
			this.finish();
			this.overridePendingTransition(R.anim.slide_in_left,
	                R.anim.slide_in_right);
		}
}
