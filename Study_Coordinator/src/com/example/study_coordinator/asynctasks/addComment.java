package com.example.study_coordinator.asynctasks;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public abstract class addComment extends LookUp {

	public addComment(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return super.doInBackground("addComment.php"+getQuery(params));
	}

	/*
	 * STATIC FUNCTIONS, FOR USE IN onSuccessfulFetch(JSONObject result) FUNCTION, THAT NEEDS TO BE
	 * IMPLEMENTED IN EXTENDING CLASS:
	 */

	protected static String addComment(JSONObject result) throws JSONException {
		
		JSONObject json = result.getJSONObject("result");
		int success = json.getInt("success");
		if (success == 1) {
			return json.getString("message");
		} else {
			return "Error!";

		}
		
	}

}
