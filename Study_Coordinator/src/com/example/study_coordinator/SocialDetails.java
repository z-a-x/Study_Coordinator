package com.example.study_coordinator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.DownloadImageTask;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUserDetails;
import com.example.study_coordinator.asynctasks.LookUpUserGroup;
import com.example.study_coordinator.asynctasks.LookUpUsers;
import com.example.study_coordinator.baseclasses.Group;
import com.example.study_coordinator.baseclasses.User;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
	String userId;
	
	ImageView profilPicture;
	

	// Store instance variables
	private String title;
	private int page;


	public SocialDetails(String userId){
		this.userId = userId;
	}
	// newInstance constructor for creating fragment with arguments
	public static SocialDetails newInstance(int page, String title, String userId) {
		SocialDetails fragmentFirst = new SocialDetails(userId);
		Bundle args = new Bundle();
		args.putInt("someInt", page);
		args.putString("someTitle", title);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_social_details, container, false);
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		profilPicture = (ImageView) view.findViewById(R.id.social_details_profil_image);
		tvUserName = (TextView)view.findViewById(R.id.social_details_name_tv);
		tvUserLastName = (TextView)view.findViewById(R.id.social_details_surname_tv);
		tvUsername = (TextView)view.findViewById(R.id.social_details_username_tv);
		tvEmail = (TextView)view.findViewById(R.id.social_details_email_tv); 		
		
		
		
		
		//new DownloadImageTask((ImageView) view.findViewById(R.id.frag_profil_image)).execute("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
		

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
					System.out.println("Pot do slike: "+user.pathToPicture);
					tvUserName.setText(tvUserName.getText()+" "+user.name);
					tvUserLastName.setText(tvUserLastName.getText()+" "+user.lastName);
					tvUsername.setText(tvUsername.getText()+" "+user.username);
					tvEmail.setText(tvEmail.getText()+" "+user.email);
					if(user.pathToPicture == null || user.pathToPicture.equals("null")){
						System.out.println("NI SLIKE");
						profilPicture.setImageResource(R.drawable.test);
					}
					else{
						System.out.println("SLIKAAAAAAAAA "+user.pathToPicture);
						DatabaseConnect dc = new DatabaseConnect();
						new DownloadImageTask((ImageView) getView().findViewById(R.id.social_details_profil_image)).execute(dc.getIpAddress()+user.pathToPicture);
					}
					
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
		
		final String TEST_QUERY = "4";
		System.out.println("SSSSSSSSSSSSSSS JE "+TEST_QUERY);
		userDetailsFetcher.execute("user_id", userId);
		
		return view;
	}

}