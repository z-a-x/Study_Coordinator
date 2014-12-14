package com.example.study_coordinator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentProfilJure extends FragmentTemplate {
	
	@Override
	protected void initialize() {
		setTitle("User "+id);
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_profil_jure, container, false);
        return view;
    }
    
}
