package com.example.study_coordinator; 

import com.example.study_coordinator.R.id;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		

		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Thin.ttf");
		Button btn = (Button)findViewById(R.id.btnRegister);
		btn.setTypeface(custom_font);
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Medium.ttf");		
	    Button btn2 = (Button)findViewById(R.id.btnSignIn);
		btn2.setTypeface(custom_font);
		
		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-LightItalic.ttf");		
		TextView tw1 = (TextView)findViewById(id.textViewTitle);
		tw1.setTypeface(custom_font);
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
