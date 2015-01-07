package com.example.study_coordinator;

import java.io.Console;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.baseclasses.Event;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventDetails extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    
    TextView tvName;
    TextView tvDate;
    TextView tvTime;
    TextView tvDesc;
    

    private void createEventList() {
		LookUp eventFetcher = new LookUpEvents(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Event> events = getEvents(result);
				for (Event event : events) {
					Log.d(event.name, event.name);
					tvName.setText(event.name);
					tvDate.setText(event.date.toString());
					tvTime.setText(event.date.toString());
					tvDesc.setText(event.description);
				}
			}
		};
		eventFetcher.execute("id", "5");
	}

    // newInstance constructor for creating fragment with arguments
    public static EventDetails newInstance(int page, String title) {
        EventDetails fragmentFirst = new EventDetails();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        
       
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_details, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        
      //setup textViews
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        tvDesc = (TextView)view.findViewById(R.id.tvDesc);
        createEventList();
        return view;
    }
}