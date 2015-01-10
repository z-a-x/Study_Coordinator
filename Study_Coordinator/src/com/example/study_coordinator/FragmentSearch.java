package com.example.study_coordinator;

import com.example.study_coordinator.tabs.TabGroups;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class FragmentSearch extends Fragment {

	SearchPagerAdapter adapterViewPager;
	View view;
	
	LayoutInflater inflater;
	ViewGroup container; 
	Bundle savedInstanceState;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		this.savedInstanceState = savedInstanceState;
		
		view = inflater.inflate(R.layout.activity_fragment_search, container, false);
		ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_search);
		adapterViewPager = new SearchPagerAdapter(getFragmentManager(),
				"b");
		vpPager.setAdapter(adapterViewPager);
		setOnClickListener();
		return view;
	}

	private void setOnClickListener() {
		ImageButton searchButton = (ImageButton) view.findViewById(R.id.bt_search);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				runSearch();
			}
		});
	}
	
	public void runSearch() {
		System.out.println("#### Running search");
	}

	public static class SearchPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;
		private String[] titles = new String[] { "Users", "Groups" };
		public String query;

		public SearchPagerAdapter(FragmentManager fragmentManager, String query) {
			super(fragmentManager);
			this.query = query;
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return TabUsers.newInstance(0, titles[0], "search", query);
			case 1:
				return TabGroups.newInstance(1, titles[1], "search", query);

			default:
				return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}
}