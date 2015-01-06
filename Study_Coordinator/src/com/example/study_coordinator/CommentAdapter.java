package com.example.study_coordinator;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment> {
	public CommentAdapter(Context context, ArrayList<Comment> users) {
	       super(context, 0, users);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       Comment comment = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
	       }
	       // Lookup view for data population
	       TextView tvName = (TextView) convertView.findViewById(R.id.tvNameComment);
	       
	       // Populate the data into the template view using the data object
	       tvName.setText(comment.username+ " "+comment.text);
	       
	       // Return the completed view to render on screen
	       return convertView;
	   }
}
