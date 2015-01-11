package com.example.study_coordinator;

import java.util.HashMap;

import com.example.study_coordinator.tabs.TabUsers;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSocial extends FragmentActivityWithDrawer {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_social);

		// PRIDOBLJE ID IZ SEARCH -> TAB USERS
		Intent intent = getIntent();
		String user_id = intent.getStringExtra("selected_user");
		if (user_id == null) {
			SessionManager s = new SessionManager(this);
			HashMap<String, String> pref = s.getUserDetails();
			System.out.println("--------------------------");
			System.out.println("CLASS FRAGMENTSOCIAL");
			System.out.println("user id " + pref.get(s.KEY_USERID));
			System.out.println("--------------------------");
			user_id = pref.get(s.KEY_USERID);
		}

		ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
		FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager(), user_id);
		vpPager.setAdapter(adapterViewPager);

		setDrawer();
	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		String userId;
		private static int NUM_ITEMS = 3;
		private String[] titles = new String[] { "Details", "Groups", "Friends" };

		public MyPagerAdapter(FragmentManager fragmentManager, String userId) {
			super(fragmentManager);
			this.userId = userId;
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
				return SocialDetails.newInstance(0, titles[0], userId);
			case 1: 
				return SocialGroups.newInstance(1, titles[1], userId);
			case 2: 
				return SocialFriends.newInstance(2, titles[2]);

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