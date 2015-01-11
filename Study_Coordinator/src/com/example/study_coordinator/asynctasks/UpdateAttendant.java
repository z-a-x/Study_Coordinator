package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.study_coordinator.Login;
import com.example.study_coordinator.MainActivity;
import com.example.study_coordinator.baseclasses.User;

public abstract class UpdateAttendant extends LookUp {

	public UpdateAttendant(Context context) {
		super(context);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("setAttendant.php"+getQuery(params));
	}

	/*
	 * STATIC FUNCTIONS, FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION, THAT NEEDS TO BE
	 * IMPLEMENTED IN EXTENDING CLASS:
	 */

	protected static String setAttendant(JSONObject result) throws JSONException {
		
		JSONObject json = result.getJSONObject("result");
		int success = json.getInt("success");
		if (success == 1) {
			return json.getString("success");
		} else {
			return json.getString("message");

		}
		
	}

}
