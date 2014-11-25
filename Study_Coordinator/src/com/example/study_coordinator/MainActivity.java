package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.style.ReplacementSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

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
		setContentView(R.layout.activity_main);
		
		drawerTitle = getTitle();
		dataList = new ArrayList<DrawerItem>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);		
		
		
		dataList.add(new DrawerItem(getResources().getString(R.string.my_groups), R.drawable.ic_action_groups));		
		dataList.add(new DrawerItem(getResources().getString(R.string.events), R.drawable.ic_action_events));
		dataList.add(new DrawerItem(getResources().getString(R.string.profil), R.drawable.ic_action_person));
		dataList.add(new DrawerItem(getResources().getString(R.string.settings), R.drawable.ic_action_setting));
		
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
		drawerList.setAdapter(adapter);
		
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
        drawerListViewItems = getResources().getStringArray(R.array.items);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
 
        
	}
	
	public void onDrawerClosed(View view) {
        getActionBar().setTitle(drawerTitle);
        invalidateOptionsMenu(); // creates call to
                                                  // onPrepareOptionsMenu()
	}

	public void onDrawerOpened(View drawerView) {
		getActionBar().setTitle(drawerTitle);
        invalidateOptionsMenu(); // creates call to
                                              // onPrepareOptionsMenu()
	}
	
	public void selectItem(int position){		
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch(position){
			
			case 0:								
				
				fragment = Fragment.instantiate(this, FragmentGroups.class.getName());
				args.putString(FragmentGroups.ITEM_NAME, dataList.get(position).getItemName());
				args.putInt(FragmentGroups.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());
				break;
			case 1:
				fragment = Fragment.instantiate(this, FragmentEvents.class.getName());
				args.putString(FragmentEvents.ITEM_NAME, dataList.get(position).getItemName());
				args.putInt(FragmentEvents.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());	
				break;
			
			case 2:
				fragment = Fragment.instantiate(this, FragmentProfil.class.getName());
				args.putString(FragmentProfil.ITEM_NAME, dataList.get(position).getItemName());
				args.putInt(FragmentProfil.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());				
				break;
			
			case 3:
				fragment = Fragment.instantiate(this, FragmentSettings.class.getName());
				args.putString(FragmentSettings.ITEM_NAME, dataList.get(position).getItemName());
				args.putInt(FragmentSettings.IMAGE_RESOURCE_ID, dataList.get(position).getImageId());						
				break;		
			default:
				break;
		}		 
		fragment.setArguments(args);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.commit();
		
		
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
