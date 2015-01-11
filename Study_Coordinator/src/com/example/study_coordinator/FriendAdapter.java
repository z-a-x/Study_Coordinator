package com.example.study_coordinator;

import java.util.List;

import com.example.study_coordinator.baseclasses.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<User> {
    public FriendAdapter(Context context, List<User> users) {
       super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       User friend = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
       
       // Populate the data into the template view using the data object
       tvName.setText(friend.name+ " "+friend.lastName);
       
       // Return the completed view to render on screen
       return convertView;
   }
}