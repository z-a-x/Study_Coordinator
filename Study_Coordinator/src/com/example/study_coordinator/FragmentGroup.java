package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.FragmentSocial.MyPagerAdapter;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.graphics.Color;

public class FragmentGroup extends Fragment {

	FragmentPagerAdapter adapterViewPager;
	private static int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		id = getArguments().getInt("id");

		View view = inflater.inflate(R.layout.activity_fragment_group, container, false);
		ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_group);
		adapterViewPager = new MyPagerAdapter(getFragmentManager());
		vpPager.setAdapter(adapterViewPager);

		id = getArguments().getInt("id");

		return view;

	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 3;
		private String[] titles = new String[] { "Details", "Events", "Members" };

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
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
			case 0: // Fragment # 0 - This will show FirstFragment
				// return SocialUsers.newInstance(0, "Details");
				return GroupDetails.newInstance(0, titles[0], id);
			case 1: // Fragment # 0 - This will show FirstFragment different title
				return GroupEvents.newInstance(1, titles[1], id);
			case 2: // Fragment # 0 - This will show FirstFragment different title
				return GroupMembers.newInstance(2, titles[2], id);

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
