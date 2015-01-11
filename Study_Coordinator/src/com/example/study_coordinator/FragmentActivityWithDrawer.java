package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FragmentActivityWithDrawer extends FragmentActivity {

	protected LinearLayout drawerLinear;
	protected ListView drawerList;
	List<DrawerItem> dataList;
	protected CharSequence drawerTitle;
	protected DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void setDrawer() {
		drawerTitle = getTitle();
		dataList = new ArrayList<DrawerItem>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer_list);

		// DRAWER ITEMS:
		dataList.add(new DrawerItem("MainActivity", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Events", R.drawable.ic_action_events));
		dataList.add(new DrawerItem("Profil", R.drawable.ic_action_person));
		dataList.add(new DrawerItem("Social", R.drawable.ic_action_group));
		dataList.add(new DrawerItem(getResources().getString(R.string.logout), R.drawable.ic_action_search));

		CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		drawerLinear = (LinearLayout) findViewById(R.id.left_drawer);

		setOnClickListener();
	}

	// SEARCH BOX:
	private void setOnClickListener() {
		ImageButton searchButton = (ImageButton) findViewById(R.id.bt_search);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				runSearch();
			}
		});
	}

	public void runSearch() {
		System.out.println("#### Running search");
		EditText editText = (EditText) findViewById(R.id.txt_search);
		String query = editText.getText().toString();

		Intent intent = new Intent(this, FragmentSearch.class);
		intent.putExtra("query", query + "");
		startActivity(intent);
	}

	public void onDrawerClosed(View view) {
		getActionBar().setTitle(drawerTitle);
		invalidateOptionsMenu(); // creates call to
		finish(); // onPrepareOptionsMenu()
	}

	public void onDrawerOpened(View drawerView) {
		getActionBar().setTitle(drawerTitle);
		invalidateOptionsMenu(); // creates call to
									// onPrepareOptionsMenu()
	}

	public void selectItem(int position) {
		Intent intent;
		switch (position) {
		case 0:
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case 1:
			intent = new Intent(this, FragmentSearch.class);
			startActivity(intent);
			this.finish();
			break;
		case 2:
			intent = new Intent(this, FragmentEvents.class);
			startActivity(intent);
			this.finish();
			break;
		case 3:
			intent = new Intent(this, FragmentProfil.class);
			startActivity(intent);
			this.finish();
			break;
		case 4:
			intent = new Intent(this, FragmentSocial.class);
			startActivity(intent);
			this.finish();
			break;
		case 5:
			this.finish();
			break;

		default:
			break;
		}

		drawerList.setItemChecked(position, true);
		setTitle(dataList.get(position).getItemName());
		// drawerLayout.closeDrawer(drawerList);
		drawerLayout.closeDrawer(drawerLinear);
	}

	protected class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
			drawerLayout.closeDrawer(drawerLinear);
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
