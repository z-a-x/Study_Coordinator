package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class UpcomingActivity2 extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
      ListView lv;
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
      public UpcomingActivity2() {
		// TODO Auto-generated constructor stub
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {    	  	
            View view = inflater.inflate(R.layout.activity_upcoming_activity2, container,false);
            lv = (ListView) view.findViewById(R.id.listView1);
            
            List<String> your_array_list = new ArrayList<String>();
            your_array_list.add("1. dogodek 1.1.2015 - CTK - predmet1 19:00");
            your_array_list.add("2. dogodek 5.1.2015 - FRI - predmet2 15:45");
            
            for(int i = 1; i <= 30; i++){
            	your_array_list.add("Testni dogodek: " + i);        	
            }
            
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, your_array_list );
            
            lv.setAdapter(arrayAdapter); 
            lv.smoothScrollToPosition(0);
            ivIcon = (ImageView) view.findViewById(R.id.frag_events_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag_events_tv);
 
            //tvItemName.setText(getArguments().getString(ITEM_NAME));
            //ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}