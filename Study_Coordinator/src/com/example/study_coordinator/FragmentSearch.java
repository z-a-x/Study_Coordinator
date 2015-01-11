package com.example.study_coordinator;

import java.util.List;

import com.example.study_coordinator.tabs.TabGroups;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class FragmentSearch extends FragmentActivityWithDrawer {
	SearchPagerAdapter adapterViewPager;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;
	
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
        adapterViewPager = new SearchPagerAdapter(getFragmentManager(), query);
        vpPager.setAdapter(adapterViewPager);

        getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
        setDrawer();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		drawerLayout.openDrawer(drawerList);
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;

		}

		return super.onOptionsItemSelected(item);
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