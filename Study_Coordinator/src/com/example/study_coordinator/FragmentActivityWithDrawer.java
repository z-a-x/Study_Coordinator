package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FragmentActivityWithDrawer extends FragmentActivity {
	
	protected String[] drawerListViewItems;
	protected ListView drawerListView;
	protected CharSequence drawerTitle;
	protected DrawerLayout drawerLayout;
	protected ListView drawerList;
	protected ActionBarDrawerToggle drawerToggle;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void setDrawer() {
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
	
	protected class DrawerItemClickListener implements ListView.OnItemClickListener {
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
