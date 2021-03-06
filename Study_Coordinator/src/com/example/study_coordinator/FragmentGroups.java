package com.example.study_coordinator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

 
public class FragmentGroups extends Fragment {
	
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";	  
	FragmentActivity listener;
	ImageView ivIcon;
    TextView tvItemName;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
    }	

	@Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
            View view = inflater.inflate(R.layout.fragment_layout_groups, container, false);
            /*
            ivIcon = (ImageView) view.findViewById(R.id.frag_groups_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag_groups_tv); 
            tvItemName.setText(getArguments().getString(ITEM_NAME));
            ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
            */
            return view;
      }
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
 
}
