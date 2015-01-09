package com.example.study_coordinator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpEvents;
import com.example.study_coordinator.asynctasks.LookUpGroups;
import com.example.study_coordinator.baseclasses.Event;
import com.example.study_coordinator.baseclasses.Group;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class FragmentUpcomingEvents extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
      ListView listView;
      EventAdapter adapter;
      private ProgressDialog pDialog;
      String result;
      
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
      
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {    	  	
            View view = inflater.inflate(R.layout.fragment_upcoming_events, container,false);
            
            listView = (ListView) view.findViewById(R.id.lv_upcoming);
            
            LookUp groupFetcher = new LookUpGroups(getActivity().getApplicationContext()) {

    			@Override
    			public void onSuccessfulFetch(JSONObject result) throws JSONException {
    				List<Group> groups = getGroups(result);
    				String s = "(";
    				for(Group g : groups){
    					s+=g.id+",";
    				}
    				if (s.length() > 0 && s.charAt(s.length()-1)==',') {
    				      s = s.substring(0, s.length()-1);
    				    }
    				s+=")";
    				SessionManager session = new SessionManager(getActivity());
    				session.setMyGroups(s);
    				//System.out.println("NASTAVIL MANAGER NA "+s);							    				
    			}
    		};
    		SessionManager session = new SessionManager(getActivity());
    		HashMap<String, String> pref = session.getUserDetails();
    		String userId = pref.get(SessionManager.KEY_USERID);
    		groupFetcher.execute("user_id", userId);
    		
    		System.out.println("---------------------------------------");
    		System.out.println("ID USER: "+userId);
    		System.out.println("USER GROUPS: "+session.getMyGroups());
    		System.out.println("---------------------------------------");
            
            LookUp eventFetcher = new LookUpEvents(getActivity().getApplicationContext()) {
				
				@Override
				public void onSuccessfulFetch(JSONObject result) throws JSONException {					
					List<Event> list = getEvents(result);
					adapter = new EventAdapter(getActivity(),list);
					listView.setAdapter(adapter);
				}
			};
			eventFetcher.execute("selected_groups", session.getMyGroups());
            /*
            List<String> your_array_list = new ArrayList<String>();
            your_array_list.add("1. dogodek 1.1.2015 - CTK - predmet1 19:00");
            your_array_list.add("2. dogodek 5.1.2015 - FRI - predmet2 15:45");
            
            for(int i = 1; i <= 30; i++){
            	your_array_list.add("Testni dogodek: " + i);        	
            }
            
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, your_array_list );
            
            listView.setAdapter(arrayAdapter); 
            */
            //listView.smoothScrollToPosition(0);
            ivIcon = (ImageView) view.findViewById(R.id.frag_events_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag_events_tv);
 
            //tvItemName.setText(getArguments().getString(ITEM_NAME));
            //ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}