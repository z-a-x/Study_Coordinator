package com.example.study_coordinator;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.study_coordinator.baseclasses.Event;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, List<Event> events) {
       super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Event event = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
    	   // TODO
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
       }
       // Lookup view for data population
       // TODO
       TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
       
       // Populate the data into the template view using the data object
       //your_array_list.add("1. dogodek 1.1.2015 - CTK - predmet1 19:00");
       String s = (String) event.date.toString();
       String[] s1 = s.split(" ");
       
       System.out.println(s);
       tvName.setText(event.name+" - "+s1[2]+". "+" "+s1[1]+" "+s1[5]+" ob "+s1[3]);
       
       // Return the completed view to render on screen
       return convertView;
   }
}