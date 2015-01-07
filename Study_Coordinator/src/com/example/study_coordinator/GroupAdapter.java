package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.List;

import com.example.study_coordinator.baseclasses.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GroupAdapter extends ArrayAdapter<Group>{
	public GroupAdapter(Context context, List<Group> users) {
	       super(context, 0, users);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       Group group = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
	       }
	       // Lookup view for data population
	       TextView tvName = (TextView) convertView.findViewById(R.id.tvNameGroup);
	       
	       // Populate the data into the template view using the data object
	       tvName.setText(group.name);
	       
	       // Return the completed view to render on screen
	       return convertView;
	   }
}
