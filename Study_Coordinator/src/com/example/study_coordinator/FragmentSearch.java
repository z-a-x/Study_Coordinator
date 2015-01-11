package com.example.study_coordinator;

import com.example.study_coordinator.tabs.TabGroups;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FragmentSearch extends FragmentActivityWithDrawer {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_search);
		
		Intent intent = getIntent();
		String query = "";
		if (intent.hasExtra("query")) {
			query = intent.getStringExtra("query");
		}
		
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        SearchPagerAdapter adapterViewPager = new SearchPagerAdapter(getFragmentManager(), query);
        vpPager.setAdapter(adapterViewPager);

        setDrawer();
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