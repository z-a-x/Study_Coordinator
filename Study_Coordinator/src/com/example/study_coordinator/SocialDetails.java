package com.example.study_coordinator;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUserDetails;
import com.example.study_coordinator.asynctasks.LookUpUserGroup;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SocialDetails extends Fragment {
	// final ListView listView ;
	
	
	String result;
	FriendAdapter adapter;
	private ProgressDialog pDialog;
	TextView tvUserName;
	TextView tvUserLastName;
	TextView tvUsername;
	TextView tvEmail;
	Button btEdit;
	

	// Store instance variables
	private String title;
	private int page;


	// newInstance constructor for creating fragment with arguments
	public static SocialDetails newInstance(int page, String title) {
		SocialDetails fragmentFirst = new SocialDetails();
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_profil, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		tvUserName = (TextView)view.findViewById(R.id.frag_profil_name_tv);
		tvUserLastName = (TextView)view.findViewById(R.id.frag_profil_surname_tv);
		tvUsername = (TextView)view.findViewById(R.id.frag_profil_username_tv);
		tvEmail = (TextView)view.findViewById(R.id.frag_profil_email_tv); 
		btEdit = (Button)view.findViewById(R.id.frag_profil_edit_bt);
		btEdit.setVisibility(View.GONE);
		
		

		LookUp userDetailsFetcher = new LookUpUserDetails(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				List<User> users = getUserDetails(result);
				User user = null;
				for(int i = 0; i < users.size(); i++){
					user=users.get(i);
					System.out.println("IZPISUJEM USER DETAILS: ");
					System.out.println(user.name);
					System.out.println(user.lastName);
					System.out.println(user.username);
					System.out.println(user.email);
					tvUserName.setText(tvUserName.getText()+" "+user.name);
					tvUserLastName.setText(tvUserLastName.getText()+" "+user.lastName);
					tvUsername.setText(tvUsername.getText()+" "+user.username);
					tvEmail.setText(tvEmail.getText()+" "+user.email);
					break;
				}
				
			}
		};
		/*
		 * 
		 * PRIVZETO NASTAVLJEN USER ID, SPREMENI GLEDE NA IZBOR V LIST_VIEWVU
		 * 
		 * 
		 */
		
		final String TEST_QUERY = "1";
		System.out.println("SSSSSSSSSSSSSSS JE "+TEST_QUERY);
		userDetailsFetcher.execute("user_id", TEST_QUERY);

		return view;
	}

}