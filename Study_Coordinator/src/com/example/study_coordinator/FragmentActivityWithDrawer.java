package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
		dataList.add(new DrawerItem("Profil", R.drawable.ic_action_person));
		dataList.add(new DrawerItem("Social", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("Events", R.drawable.ic_action_calendar));
		dataList.add(new DrawerItem(getResources().getString(R.string.logout), R.drawable.ic_action_lock));

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
		EditText searchBox = (EditText) findViewById(R.id.txt_search);
		searchBox.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	runSearch();
		            handled = true;
		        }
		        return handled;
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
			intent = new Intent(this, FragmentProfil.class);
			startActivity(intent);
			this.finish();
			break;
		case 1:
			intent = new Intent(this, FragmentSocial.class);
			startActivity(intent);
			this.finish();
			break;
		case 2:
			intent = new Intent(this, FragmentEvents.class);
			startActivity(intent);
			this.finish();
			break;
		case 3:
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
