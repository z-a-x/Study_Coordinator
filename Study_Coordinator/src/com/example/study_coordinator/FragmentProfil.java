package com.example.study_coordinator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

import android.app.Activity;
import android.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class FragmentProfil extends Fragment{
	ImageView ivIcon;
	ImageView ivProfil;
    TextView tvItemName;
    TextView tvUsername;
    TextView tvUserName;
    TextView tvUserLastName;
    Button btEditProfil;
    FragmentActivity listener;
    Bitmap bitmap;
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    
    @Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
    }	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_layout_profil, container, false);
    	ivIcon = (ImageView) view.findViewById(R.id.frag_profil_icon);       
    	ivProfil = (ImageView) view.findViewById(R.id.frag_profil_profil_image);
    	tvItemName = (TextView) view.findViewById(R.id.frag_profil_tv);                    
    	tvUsername = (TextView) view.findViewById(R.id.frag_profil_email_tv);
    	tvUserName = (TextView) view.findViewById(R.id.frag_profil_name_tv);
    	tvUserLastName = (TextView) view.findViewById(R.id.frag_profil_surname_tv);          
    	
    	ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
    	tvItemName.setText(getArguments().getString(ITEM_NAME));
          
        MainActivity m = (MainActivity) getActivity();
        final String recivedData = m.getS1();
        String[] parsedData = recivedData.split(" "); 
        String username = parsedData[0];
        String userName = parsedData[1];
        String userLastName = parsedData[2];
          
        //tvUserLastName.setText(username);
        tvUserName.setText(tvUserName.getText()+ " "+userName);
        tvUserLastName.setText(tvUserLastName.getText()+ " "+userLastName);
        tvUsername.setText(tvUsername.getText()+" "+username);
        //setup custom fonts buttons & title
		Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto/Roboto-Thin.ttf");					
		tvUserName.setTypeface(custom_font);
		tvUserLastName.setTypeface(custom_font);
		tvUsername.setTypeface(custom_font);
		
		btEditProfil = (Button) view.findViewById(R.id.frag_profil_edit_bt);
    	btEditProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	            	
            	Intent i = new Intent(getActivity(), ActivityProfilEdit.class);    
            	i.putExtra("data", recivedData);
            	getActivity().startActivity(i);
            	
            }
        });
			
          //new LoadImage().execute("http://http://193.2.179.235:80/android_connect/images/jakaProfil.jpg");          
          return view;
    }
	    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
        protected void onPreExecute() {
			super.onPreExecute();                
        }
		protected Bitmap doInBackground(String... args) {
        	try {
        		bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
        		System.out.println("DECODING STREAM");
        	} 
        	catch (Exception e) {
        		System.out.println("LOADING BITMAP FAILED");
        		e.printStackTrace();
        	}
        	return bitmap;
        }
       protected void onPostExecute(Bitmap image) {
    	   if(image != null){
    		   ivProfil.setImageBitmap(image);
    	   }
    	   else{
    		   System.out.println("NO IMAGE");
    	   }
       }
    }
}

