package com.example.study_coordinator;

import com.example.study_coordinator.tabs.TabEvents;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentGroup extends FragmentActivityWithDrawer {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_group);
		
		Intent intent = getIntent();
		String idString = intent.getStringExtra("id");
		int id = Integer.parseInt(idString);
		ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
		FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager(), id);
		vpPager.setAdapter(adapterViewPager);

		// DRAWER:
		setDrawer();
	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 3;
		private String[] titles = new String[] { "Details", "Events", "Members" };
		private final Integer id;

		public MyPagerAdapter(FragmentManager fragmentManager, int id) {
			super(fragmentManager);
			this.id = id;
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
				return GroupDetails.newInstance(0, titles[0], "id", id.toString());
			case 1:
				return TabEvents.newInstance(1, titles[1], "group_id", id.toString());
			case 2:
				return TabUsers.newInstance(2, titles[2], "group_id", id.toString());

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
