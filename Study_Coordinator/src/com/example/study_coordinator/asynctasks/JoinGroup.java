package com.example.study_coordinator.asynctasks;

import org.json.JSONObject;

import android.content.Context;

public abstract class JoinGroup extends LookUp {

	public JoinGroup(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		String queryAddress = "joinGroup.php"+getQuery(params);
		System.out.println(queryAddress);
		return super.doInBackground(queryAddress);
	}

}