package com.example.study_coordinator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentEventJure extends FragmentTemplate {
	
	@Override
	protected void initialize() {
		setTitle("Event "+id);
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_event_jure, container, false);
        return view;
    }
    
}