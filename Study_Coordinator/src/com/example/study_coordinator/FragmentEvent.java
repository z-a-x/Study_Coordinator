package com.example.study_coordinator;

import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class FragmentEvent extends FragmentActivityWithDrawer  {
	
	FragmentPagerAdapter adapterViewPager;
	
	CustomDrawerAdapter adapter;
	String eventId;
	String userId;
	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_event);
		
		//PRIDOBLJEN ID EVENTA IZ UPCOMING EVENTS
		Intent intent = getIntent();
		eventId = intent.getStringExtra("selected_event");
		
		//PRIDOBLJEN USER ID IZ session manager
		session = new SessionManager(getApplicationContext());
		HashMap<String, String> pref = session.getUserDetails();
		userId = pref.get("user_id");
		
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getFragmentManager(), eventId, userId);
        vpPager.setAdapter(adapterViewPager);
        setTitle("Event");
        setDrawer();
    }
	
	
	@Override
	public void setTitle(CharSequence title) {
		this.drawerTitle = title;
		getActionBar().setTitle(title);
	};

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        String eventId;
        String userId;

            public MyPagerAdapter(FragmentManager fragmentManager, String eventId, String userId) {
                super(fragmentManager);
                this.eventId = eventId;
                this.userId = userId;
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
                return EventDetails.newInstance(this.eventId, this.userId);
            case 1: // Fragment # 1 - This will show SecondFragment
                return EventComments.newInstance(this.eventId, this.userId);
            default:
                return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
        	if (position == 0)
        		return "Details";
        	else return "Comments";
        }

    }

}