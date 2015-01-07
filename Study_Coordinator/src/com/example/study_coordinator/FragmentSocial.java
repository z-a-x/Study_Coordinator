package com.example.study_coordinator;

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

public class FragmentSocial extends Fragment  {
	
	FragmentPagerAdapter adapterViewPager;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.activity_fragment_social, container,
                 false);
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager_social);
        adapterViewPager = new MyPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        
        return view;
        
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        private String[] titles = new String[]{"Details", "Groups", "Friends"};
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
                //return SocialUsers.newInstance(0, "Details");
            	return SearchUsers.newInstance(0, titles[0]);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SocialGroups.newInstance(1, titles[1]);
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return SocialUsers.newInstance(2, titles[2]);
            
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