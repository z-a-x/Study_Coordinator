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
import com.example.study_coordinator.asynctasks.UpdateAttendant;
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
import android.widget.Toast;

public class EventDetails extends Fragment implements View.OnClickListener {
    String eventId;
    String userId;
    TextView tvName;
    TextView tvDate;
    TextView tvTime;
    TextView tvDesc;
    GridView gridView;
    Button doButton;
    Bitmap homeIcon;
    Bitmap userIcon;
    
    RelativeLayout relativeLayout;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    boolean attending = false;
    
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
				attending = false;
				for (User user : attendants) {
					Log.d("test",user.name);
					gridArray.add(new Item(userIcon,user.name + " " + user.lastName));
					if (user.id == Integer.parseInt(userId) ) {
						attending = true;						
					}
				}
				gridView.setAdapter(customGridAdapter);
			}
		};
		eventFetcher.execute("id", eventId);
		setupButton();
	}
    
    
 // pridobi seznam ljudi, ki se bodo udeležili eventa
    private void addremoveUser() {
		LookUp eventFetcher = new UpdateAttendant(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				createAttendantist(userIcon);
				setupButton();
			}
		};
		customGridAdapter.clear();
		eventFetcher.execute("action", String.valueOf(attending) + " " + eventId + " " + userId);
	}

    // newInstance constructor for creating fragment with arguments
    public static EventDetails newInstance(String eventId, String userId) {
        EventDetails fragmentFirst = new EventDetails(eventId, userId);
        return fragmentFirst;
    }

    public EventDetails(String eventId, String userId){
    	this.eventId = eventId;
    	this.userId = userId;
    }
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
    }
    
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.doButton:
            	addremoveUser();
            break;
        }   
    }
    
    public void setupButton() {
    	if (!attending) {
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
        
        //set grid view item
        homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.person);
        userIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.person);
        
      //setup textViews
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        tvDesc = (TextView)view.findViewById(R.id.tvDesc);
        doButton = (Button) view.findViewById(R.id.doButton);
        doButton.setOnClickListener(this);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl4);
        
        gridView = (GridView) view.findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(view.getContext(), R.layout.row_grid, gridArray);
				
		createAttendantist(userIcon);
        createEventList();        
        return view;
    }
}