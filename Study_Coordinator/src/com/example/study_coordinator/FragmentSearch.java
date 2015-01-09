package com.example.study_coordinator;

import com.example.study_coordinator.tabs.TabGroups;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSearch extends Fragment {

	FragmentPagerAdapter adapterViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragment_search, container, false);
		ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_search);
		adapterViewPager = new MyPagerAdapter(getFragmentManager());
		vpPager.setAdapter(adapterViewPager);

		return view;

	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;
		private String[] titles = new String[] { "Users", "Groups" };

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
			case 0:
				return TabUsers.newInstance(0, titles[0], "search", "i");
			case 1:
				return TabGroups.newInstance(1, titles[1], "search", "z");

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