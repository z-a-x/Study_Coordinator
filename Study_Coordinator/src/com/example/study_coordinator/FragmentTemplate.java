package com.example.study_coordinator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public abstract class FragmentTemplate extends Fragment {
	
	protected String id;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
		id = getArguments().getString("id");
		initialize();
    } 

    protected void setTitle(String name) {
		TextView textView = (TextView) getView().findViewById(R.id.title);
		textView.setText(name);
	}
    
    protected abstract void initialize();

	//#######################################
	//############# B O I L E R #############
	//#######################################
	
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
