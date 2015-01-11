package com.example.study_coordinator.asynctasks;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

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
			return json.getString("message");
		} else {
			return "Error!";

		}
		
	}

}
