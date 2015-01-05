package com.example.study_coordinator;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar.Tab;

public class FragmentSearch extends Fragment {
    ListView listView ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_layout_search, container, false);
        super.onCreate(savedInstanceState);        
        
        //Construct the data source
        ArrayList<Friend> arrayOfFriends = new ArrayList<Friend>();
        arrayOfFriends.add(new Friend("jPlut"));
        arrayOfFriends.add(new Friend("rRozina"));
        arrayOfFriends.add(new Friend("aBokal"));
        // Create the adapter to convert the array to views
        FriendAdapter adapter = new FriendAdapter(getActivity(), arrayOfFriends);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
        
                
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
               String  itemValue = (String) listView.getItemAtPosition(position);
                  
                // Show Alert 
                Toast.makeText(getActivity().getApplicationContext(),
                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  .show();
             
              }

         }); 
        
        
        
        return view;
    }

}