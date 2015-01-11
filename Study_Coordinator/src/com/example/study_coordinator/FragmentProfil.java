package com.example.study_coordinator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.DownloadImageTask;
import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.LookUpUserDetails;
import com.example.study_coordinator.baseclasses.User;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.media.JetPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentProfil extends FragmentActivity{
    TextView tvUsername;
    TextView tvUserName;
    TextView tvUserLastName;
    TextView tvemail;
    Button btEditProfil;
    FragmentActivity listener;
    Bitmap bitmap;
    ImageView profilPicture;
    ImageView iw;
    String pathToPicture;
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    
   
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.fragment_layout_profil);
    	
    	//ivIcon = (ImageView) view.findViewById(R.id.frag_profil_icon);       
    	//ivProfil = (ImageView) view.findViewById(R.id.frag_profil_profil_image);
    	//tvItemName = (TextView) view.findViewById(R.id.frag_profil_tv);
    	
    	tvUsername = (TextView) findViewById(R.id.frag_profil_username_tv);
    	tvUserName = (TextView) findViewById(R.id.frag_profil_name_tv);
    	tvUserLastName = (TextView) findViewById(R.id.frag_profil_surname_tv);          
    	tvemail = (TextView) findViewById(R.id.frag_profil_email_tv);
    	//ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
    	//tvItemName.setText(getArguments().getString(ITEM_NAME));
        
    	/*
        MainActivity m = new MainActivity();
        final String recivedData = m.getS1();
        String[] parsedData = recivedData.split(" ");
        System.out.println("Printing parsed data: ");
        for(int i = 0; i < parsedData.length; i++){
        	System.out.println(parsedData[i]);
        }
        */
    	SessionManager session = new SessionManager(getApplicationContext());
    	HashMap<String, String> pref = session.getUserDetails();
		
        
        
        String userName = session.getMyName();
        String userLastName = session.getMyLastName();
        String username = pref.get(SessionManager.KEY_USERNAME);
        String email = session.getMyEmail();
        pathToPicture = session.getMyPicture();
        final String recivedData = "" + userName + " " + userLastName + " " + username + " " + email+" "+pathToPicture;
          
        tvemail.setText(tvemail.getText()+ " "+email);
        tvUsername.setText(tvUsername.getText()+" "+username);
        tvUserName.setText(tvUserName.getText()+ " "+userName);
        tvUserLastName.setText(tvUserLastName.getText()+ " "+userLastName);
        
		btEditProfil = (Button) findViewById(R.id.frag_profil_edit_bt);		
    	btEditProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	            	
            	Intent i = new Intent(FragmentProfil.this, ActivityProfilEdit.class);    
            	i.putExtra("data", recivedData);
            	startActivity(i);
            	
            }
        });
    	
    	//setup custom fonts buttons & title
		/*
    	Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto/Roboto-Thin.ttf");					
		tvUserName.setTypeface(custom_font);
		tvUserLastName.setTypeface(custom_font);
		tvUsername.setTypeface(custom_font);
		tvemail.setTypeface(custom_font);
		 */
		//Lookup uporabljamo samo zaradi asynctaska pr loudanju slike
		LookUp userDetailsFetcher = new LookUpUserDetails(getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				
					if(pathToPicture == null || pathToPicture.equals("null") || pathToPicture.equals("NULL")){
						System.out.println("NI SLIKE");
						profilPicture = (ImageView) findViewById(R.id.frag_profil_image);
						profilPicture.setImageResource(R.drawable.test);
					}
					else{
						System.out.println("SLIKAAAAAAAAA "+pathToPicture);
						DatabaseConnect dc = new DatabaseConnect();
						new DownloadImageTask((ImageView) findViewById(R.id.frag_profil_image)).execute(dc.getIpAddress()+pathToPicture);
					}
				}
		};
		final String TEST_QUERY = "4";
		System.out.println("SSSSSSSSSSSSSSS JE "+TEST_QUERY);
		userDetailsFetcher.execute("user_id", TEST_QUERY);
		
          //new LoadImage().execute("http://http://193.2.179.235:80/android_connect/images/jakaProfil.jpg");          
          
    }
    
	    
   
    
}

