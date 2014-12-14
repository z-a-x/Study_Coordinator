package com.example.study_coordinator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentGroupsJure extends Fragment{
	ImageView ivIcon;
    TextView tvItemName;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.fragment_layout_groups_jure, container,false);
          
//          ivIcon = (ImageView) view.findViewById(R.id.frag_groupsJure_icon);
//          tvItemName = (TextView) view.findViewById(R.id.frag_groupsJure_tv);
//          
//          tvItemName.setText(getArguments().getString(ITEM_NAME));
//          ivIcon.setImageDrawable(view.getResources().getDrawable(
//                      getArguments().getInt(IMAGE_RESOURCE_ID)));
          
          //TEST BUTTON
          Button button = (Button) view.findViewById(R.id.button1);
          button.setOnClickListener(new OnClickListener()
	          {
	                    @Override
	                    public void onClick(View v)
	                    {
	                    	openGroupFragment();
	                    } 
	          }); 
          return view;
    }

    
    private void openGroupFragment() {
	    FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    FragmentGroup fragment = new FragmentGroup();
	    Bundle arguments = new Bundle();
	    arguments.putString("id", "1231231");
	    fragment.setArguments(arguments);
	    transaction.replace(R.id.content_frame, fragment);
	    transaction.addToBackStack(null);
	    transaction.commit(); 
    }
    
}


