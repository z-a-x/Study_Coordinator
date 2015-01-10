package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import com.example.study_coordinator.tabs.TabEvents;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class FragmentGroup extends FragmentActivity {

	FragmentPagerAdapter adapterViewPager;

	//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		int id = getArguments().getInt("id");
//
//		View view = inflater.inflate(R.layout.activity_fragment_group, container, false);
//		ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_group);
//		adapterViewPager = new MyPagerAdapter(getFragmentManager(), id);
//		vpPager.setAdapter(adapterViewPager);
//
//		return view;
//
//	}
	
	// DRAWER:
	
	private String[] drawerListViewItems;
	private ListView drawerListView;
	private CharSequence drawerTitle;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//int id = savedInstanceState.getInt("id");
		// TODO: change:
		int id = 1;
		setContentView(R.layout.activity_fragment_group);
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getFragmentManager(), id);
        vpPager.setAdapter(adapterViewPager);
        
        // DRAWER:
        drawerTitle = getTitle();
		dataList = new ArrayList<DrawerItem>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
	
		dataList.add(new DrawerItem("MainActivity", R.drawable.ic_action_search));
		dataList.add(new DrawerItem(getResources().getString(R.string.logout), R.drawable.ic_action_search));
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		drawerListView = (ListView) findViewById(R.id.left_drawer);
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
	
	// DRAWER
	
	public void onDrawerClosed(View view) {
		getActionBar().setTitle(drawerTitle);
		invalidateOptionsMenu(); // creates call to
		finish();							// onPrepareOptionsMenu()
	}

	public void onDrawerOpened(View drawerView) {
		getActionBar().setTitle(drawerTitle);
		invalidateOptionsMenu(); // creates call to
									// onPrepareOptionsMenu()
	}
	
	public void selectItem(int position) {
		Fragment fragment = null;
		Bundle args = new Bundle();
		Intent intent;
		boolean logout = false;
		switch (position) {
		case 0:
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
            this.finish();
		case 1:
			logout = true;
			//fragment = instantiateFragment(FragmentEvent.class, position, args);
            this.finish();
			break;

		default:
			break;
		}
		
		drawerList.setItemChecked(position, true);
		setTitle(dataList.get(position).getItemName());
		drawerLayout.closeDrawer(drawerList);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
			drawerLayout.closeDrawer(drawerListView);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		this.drawerTitle = title;
		getActionBar().setTitle(title);
	};

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
	
}
