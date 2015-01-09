package com.example.study_coordinator;

import com.example.study_coordinator.tabs.TabEvents;
import com.example.study_coordinator.tabs.TabUsers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGroup extends Fragment {

	FragmentPagerAdapter adapterViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int id = getArguments().getInt("id");

		View view = inflater.inflate(R.layout.activity_fragment_group, container, false);
		ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_group);
		adapterViewPager = new MyPagerAdapter(getFragmentManager(), id);
		vpPager.setAdapter(adapterViewPager);

		return view;

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
