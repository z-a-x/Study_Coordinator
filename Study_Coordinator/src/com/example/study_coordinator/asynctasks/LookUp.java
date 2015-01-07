package com.example.study_coordinator.asynctasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.DatabaseConnect;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

/**
 * Basic extension of the AsyncTask, intended for querying JSON APIs. It receives the static part of the URI
 * in the constructor and query part in the first parameter of the execute function. The extending class needs
 * to define onPostExecute function.
 */
public abstract class LookUp extends AsyncTask<String, Void, JSONObject> {

	final String address;
	final Context context;

	public LookUp(Context context) {
		this.context = context;
		DatabaseConnect dc = new DatabaseConnect();
		this.address = dc.getIpAddress();
	}

	public abstract void onSuccessfulFetch(JSONObject result) throws JSONException;

	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			String combinedAddress = address;
			if (params != null && params.length != 0) {
				combinedAddress = address + params[0];
			}

			final HttpGet request = new HttpGet(combinedAddress);
			request.addHeader("Accept", "application/json");
			final HttpClient client = new DefaultHttpClient();
			final HttpResponse response = client.execute(request);
			final HttpEntity entity = response.getEntity();
			String responseJson = EntityUtils.toString(entity);
			return new JSONObject(responseJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if (result == null) {
			Toast.makeText(context, "Error occured while downloading data.", Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			onSuccessfulFetch(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void runPeriodically(int initialDelay, int miliseconds) {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask asynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						try {
							LookUp.this.execute();
						} catch (Exception e) {
						}
					}
				});
			}
		};
		timer.schedule(asynchronousTask, initialDelay, miliseconds);
	}
	
	/**
	 * Returns a URL query string.
	 * Params represent name/value pairs; they should always have even length.
	 * Example:
	 * in: "id", "14"
	 * out: "?id=14"
	 */
	protected static String getQuery(String... params) {
		if (params.length == 0) {
			return "";
		}
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		for (int i = 0; i < params.length/2; i++) {
			paramsList.add(new BasicNameValuePair(params[i], params[i+1]));
		}
	    return "?" + URLEncodedUtils.format(paramsList, "utf-8");
	}


	// TODO: include users id into queries:
	// Should look something like this (this code used to be in SearchGroups, but it was usless:
	// SessionManager session = new SessionManager(getActivity());
	// String username = null;
	// if (session.isLoggedIn()) {
	// HashMap<String, String> pref = session.getUserDetails();
	// username = pref.get(SessionManager.KEY_USERNAME);
	//
	// }
	// if (username != null) {
	// System.out.println("Dobil shared preferences!");
	// System.out.println("Username is: " + username);
	// } else {
	// System.out.println("Didn't get shared preferences!");
	// }

}
