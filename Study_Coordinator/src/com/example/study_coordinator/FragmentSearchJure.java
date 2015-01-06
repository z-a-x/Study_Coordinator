package com.example.study_coordinator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class FragmentSearchJure extends FragmentTemplate {

	@Override
	protected void initialize() {
		initializeTabs();
	}
	
	// ///// TABS ////////

	private void initializeTabs() {
		TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		spec1.setIndicator("TAB 1");
		spec1.setContent(R.id.tab1);

		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("TAB 2");
		spec2.setContent(R.id.tab2);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);

		setTabListener();
	}

	/**
	 * Da spremeni barvo naslovov tabov
	 */
	private void setTabListener() {
		final TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
					TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); // Unselected Tabs
					tv.setTextColor(Color.parseColor("#000000"));
				}
				TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); // for
				tv.setTextColor(Color.parseColor("#ffffff"));
			}
		});
	}

	@Override
	public void onResume() {
		final TabHost tabHost = (TabHost) getView().findViewById(R.id.tabhost);
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			tv.setTextSize(26);
			if (i == 0) {
				tv.setTextColor(Color.parseColor("#ffffff"));
			}
		}
		super.onResume();
	}
	
	// #######################################
	// ############# B O I L E R #############
	// #######################################

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_search_jure, container, false);
		return view;
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
