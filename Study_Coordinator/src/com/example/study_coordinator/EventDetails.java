package com.example.study_coordinator;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpAttendants;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.CustomGridViewAdapter;
import com.example.study_coordinator.baseclasses.Item;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.User;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class EventDetails extends Fragment {
    String eventId;
    TextView tvName;
    TextView tvDate;
    TextView tvTime;
    TextView tvDesc;
    GridView gridView;
    Button doButton;
    RelativeLayout relativeLayout;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    boolean attending = false;
    SessionManager session;
    HashMap<String, String> pref;
    
    // pridobi event glede na id
    private void createEventList() {
		LookUp eventFetcher = new LookUpEvents(getActivity().getApplicationContext()) {

			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<Event> events = getEvents(result);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM");
				for (Event event : events) {
					Log.d(event.name, event.name);
					tvName.setText(event.name);
					
					cal.setTime(event.date);
					tvDate.setText(sdf.format(event.date) + " " + (cal.get(Calendar.MONTH) + 1 ));
					tvTime.setText(cal.get(Calendar.HOUR_OF_DAY) + " : " +  cal.get(Calendar.MINUTE));
					tvDesc.setText(event.description);
				}
			}
		};		
		eventFetcher.execute("id", eventId);
	}
    
    // pridobi seznam ljudi, ki se bodo udeležili eventa
    private void createAttendantist(final Bitmap userIcon) {
		LookUp eventFetcher = new LookUpAttendants(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> attendants = getAttendants(result);
				for (User user : attendants) {
					Log.d("test",user.name);
					gridArray.add(new Item(userIcon,user.name + " " + user.lastName));
					//if (user.id == Integer.parseInt(pref.get("user_id")) ) {
						attending = true;						
					//}
				}
				gridView.setAdapter(customGridAdapter);
			}
		};
		eventFetcher.execute("id", eventId);
	}

    // newInstance constructor for creating fragment with arguments
    public static EventDetails newInstance(String eventId) {
        EventDetails fragmentFirst = new EventDetails(eventId);
        return fragmentFirst;
    }

    public EventDetails(String eventId){
    	this.eventId = eventId;
    }
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
    }
    
    public void setupButton() {
    	if (attending) {
    		doButton.setText("-");
    	}
    	else {
    		doButton.setText("+");
    	}
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_details, container, false);

        // set userID
        session = new SessionManager(getActivity().getApplicationContext());
        pref = session.getUserDetails();
        
        //set grid view item
        Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.person);
        Bitmap userIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.person);
        
      //setup textViews
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        tvDesc = (TextView)view.findViewById(R.id.tvDesc);
        doButton = (Button) view.findViewById(R.id.doButton);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl4);
        
        gridView = (GridView) view.findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(view.getContext(), R.layout.row_grid, gridArray);
				
		createAttendantist(userIcon);
        createEventList();        
        setupButton();
        return view;
    }
}