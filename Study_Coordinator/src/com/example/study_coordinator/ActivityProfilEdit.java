package com.example.study_coordinator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProfilEdit extends Activity {
	
    EditText etUserName;
    EditText etUserLastName;
    EditText etEmail;
    
    TextView tvUserName;
    TextView tvUserLastName;
    TextView tvEmail;
    Button btEditProfil;
    int serverResponseCode = 0;
    private ProgressDialog pDialog;
    private ProgressDialog dialog;
    String email;
	String userName;
	String userLastName;
	String username;
    
	InputStream is=null;
	String result=null;
	String line=null;
	
	String fileName = "";
	ImageView profilePicture;
	String realPath;        
    String upLoadServerUri = null;
    String uploadFilePath;
    Button uploadButton;
    Button selectButton;
    TextView messageText;
    
	int code;
	
	private static final DatabaseConnect databaseConnect = new DatabaseConnect();
	private static final String PROFILE_URL =  databaseConnect.getIpAddress() + "updateProfil.php";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil_edit);
		
		profilePicture = (ImageView)findViewById(R.id.frag_profil_edit_image);
		selectButton = (Button)findViewById(R.id.edit_Profil_selectButton);  
        uploadButton = (Button)findViewById(R.id.edit_profil_uploadButton);
        messageText  = (TextView)findViewById(R.id.edit_profil_messageText);
        //messageText.setVisibility(View.GONE);
        
        
		etEmail = (EditText)findViewById(R.id.frag_profil_edit_email_et);
		etUserName = (EditText)findViewById(R.id.frag_profil_edit_name_et);
    	etUserLastName = (EditText)findViewById(R.id.frag_profil_edit_surname_et);
    	
    	tvEmail = (TextView)findViewById(R.id.frag_profil_edit_email_tv);
    	tvUserName = (TextView)findViewById(R.id.frag_profil_edit_name_tv);
    	tvUserLastName = (TextView)findViewById(R.id.frag_profil_edit_surname_tv);    	
    	
    	Intent intent = getIntent();
		String recivedData = intent.getExtras().getString("data");
        String[] parsedData = recivedData.split(" "); 
        email = parsedData[3];
        userName = parsedData[0];
        userLastName = parsedData[1];
        
        etEmail.setText(email);
        etUserName.setText(userName);
        etUserLastName.setText(userLastName);
        /*
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Thin.ttf");					
		tvUserName.setTypeface(custom_font);
		tvUserLastName.setTypeface(custom_font);
		tvEmail.setTypeface(custom_font);
		
        
		etUserName.setTypeface(custom_font);
		etUserLastName.setTypeface(custom_font);
		etUsername.setTypeface(custom_font);
        */
        
        btEditProfil = (Button) findViewById(R.id.profil_edit_profile_bt);
        btEditProfil.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {             	
	            AsyncTask<String, String, String> result = new DataSender().execute();
	            
	            try {
	            	Intent i = new Intent(ActivityProfilEdit.this, MainActivity.class);
		            i.putExtra("update", result.get());
		            i.putExtra("newData", email);
					System.out.println("RESULT: "+result.get());
					finish();
		  			startActivity(i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
				}
             	
            }
        });
        
        DatabaseConnect dc = new DatabaseConnect();        
        upLoadServerUri = dc.getIpAddress()+"UploadToServer.php";
        
        //klik na upload gumb
        uploadButton.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                uploadFilePath = messageText.getText().toString();
                dialog = ProgressDialog.show(ActivityProfilEdit.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                        public void run() {
                             runOnUiThread(new Runnable() {
                                    public void run() {
                                        messageText.setText("uploading started.....");
                                    }
                                });                      
                           
                             uploadFile(uploadFilePath);
                        }
                      }).start();        
                }
            });
        
        selectButton.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
            	Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                		startActivityForResult(pickPhoto , 0);
                }
            });
    }
    
	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		} 
        finally {
        	if (cursor != null) {
        		cursor.close();
			}
	  }
	}
	
	//po konèani background operaciji vrne sliko in nastavi pot
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
    	super.onActivityResult(requestCode, resultCode, imageReturnedIntent);     	
    	switch(requestCode) {
    	case 0:
    	    if(resultCode == RESULT_OK){  
    	        Uri selectedImage = imageReturnedIntent.getData();
    	        profilePicture.setImageURI(selectedImage);    	        
    	        messageText.setText(getRealPathFromURI(getApplicationContext(), selectedImage));
    	        System.out.println("SPLITAM STRING: "+messageText.getText().toString());
    	        String[] f = messageText.getText().toString().split("/");    	       
      	    	fileName = "images/"+f[f.length-1];
    	    }
    	break; 
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profil_edit, menu);
		return true;
	}
	
	class DataSender extends AsyncTask<String, String, String>{
		
		@Override
		   protected void onPreExecute() {
		       super.onPreExecute();
		       pDialog = new ProgressDialog(ActivityProfilEdit.this);
		       pDialog.setMessage("Updating data...");
		       pDialog.setIndeterminate(false);
		       pDialog.setCancelable(true);
		       pDialog.show();
		   }
		   
		
		@Override		
		public String doInBackground(String... args) {
			Looper.prepare();

	    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	System.out.println("POŠILJAM PODATKE "+ email+ " "+userName+" "+userLastName);
	    	SessionManager session = new SessionManager(getApplicationContext());
			HashMap<String, String> pref = session.getUserDetails();
			username = pref.get(SessionManager.KEY_USERNAME);
			
	    	Editable email = etEmail.getText();
	    	Editable userName = etUserName.getText();
	    	Editable userLastName = etUserLastName.getText();
	    	if(!fileName.equals("")){
	    		System.out.println("POSODABLJAM SLIKO NA: "+fileName);
	    		session.setMyPicture(fileName);
	    		nameValuePairs.add(new BasicNameValuePair("user_avatar",fileName));
	    	}
	    	else{
	    		System.out.println("FILE NAME JE PRAZEN: "+fileName);
	    	}
	    	session.setMyEmail(email.toString());
	    	session.setMyName(userName.toString());
	    	session.setMyLastName(userLastName.toString());
	    	nameValuePairs.add(new BasicNameValuePair("username",username));
	    	nameValuePairs.add(new BasicNameValuePair("email",email.toString()));
	    	nameValuePairs.add(new BasicNameValuePair("userName",userName.toString()));
	    	nameValuePairs.add(new BasicNameValuePair("userLastName", userLastName.toString()));
	    	
	    	
	    	
	    	try{
	    		HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost(PROFILE_URL);
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost); 
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        Log.e("pass 1", "connection success ");
		        System.out.println("Uspel dostop do baze!");
	    	}
	    	
	        catch(Exception e)
	    	{
	        	System.out.println("Dostop do baze ni uspel!");
	        	Log.e("Fail 1", e.toString());
		    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
				Toast.LENGTH_LONG).show();
	    	}     
	        try {
	    		BufferedReader reader = new BufferedReader
					(new InputStreamReader(is,"iso-8859-1"),8);
	            	StringBuilder sb = new StringBuilder();
	            	
	            	while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            	}
	            	is.close();
	            	result = sb.toString();
	            	Log.e("pass 2", "connection success ");
	            	System.out.println("Uspešno sestavljeno sporoèilo");
	        }
	        catch(Exception e){
	        	Log.e("Fail 2", e.toString());
	    	}     
	        try {
	        	JSONObject json_data = new JSONObject(result);
	        	code=(json_data.getInt("code"));
				System.out.println("VALUE OF CODE IS: "+code);
	        	if(code==1){	        		
	        		Toast.makeText(getBaseContext(), "Update Successfully",
					Toast.LENGTH_LONG).show();
	        		System.out.println("Uspešen zapis v bazo");
	        		//Toast.makeText(getApplicationContext(), "Successful update!", 2000).show();
	        	}
	        	else{
        			Toast.makeText(getBaseContext(), "Sorry, Try Again",
					Toast.LENGTH_LONG).show();
        			System.out.println("Zapis v bazo ni uspel!");
	        	}
	    	}
	        catch(Exception e){
	        	Log.e("Fail 3", e.toString());
	        	System.out.println("Postopek ni uspel zaradi drugih napak");
	    	}
	   		return "Successful update";
	    }
		protected void onPostExecute(String file_url) {
		      // dismiss the dialog once product deleted
		      pDialog.dismiss();
		  }
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
         
        if (!sourceFile.isFile()) {
             dialog.dismiss(); 
             Log.e("uploadFile", "Source File not exist :" +uploadFilePath);
              
             runOnUiThread(new Runnable() {
                 public void run() {
                     messageText.setText("Source File not exist :"
                             +uploadFilePath);
                 }
             }); 
             return 0;
        }
        else
        {
             try { 
                  
                   // open a URL connection to the Servlet
                 FileInputStream fileInputStream = new FileInputStream(sourceFile);
                 URL url = new URL(upLoadServerUri);
                  
                 // Open a HTTP  connection to  the URL
                 conn = (HttpURLConnection) url.openConnection(); 
                 conn.setDoInput(true); // Allow Inputs
                 conn.setDoOutput(true); // Allow Outputs
                 conn.setUseCaches(false); // Don't use a Cached Copy
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                 String str = messageText.getText().toString();
              	String[] ss = str.split("/");	   
                 String s1 = ss[ss.length-1];
                 System.out.println("IME FAJLA ZA UPLOAD: "+s1);
                 conn.setRequestProperty("uploaded_file", s1); 
                  
                 dos = new DataOutputStream(conn.getOutputStream());
        
                 dos.writeBytes(twoHyphens + boundary + lineEnd); 
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ s1 + "" + lineEnd);
                  
                 dos.writeBytes(lineEnd);
        
                 // create a buffer of  maximum size
                 bytesAvailable = fileInputStream.available(); 
        
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
        
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                    
                 while (bytesRead > 0) {
                      
                   dos.write(buffer, 0, bufferSize);
                   bytesAvailable = fileInputStream.available();
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                    
                  }
        
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        
                 // Responses from the server (code and message)
                 serverResponseCode = conn.getResponseCode();
                 String serverResponseMessage = conn.getResponseMessage();
                   
                 Log.i("uploadFile", "HTTP Response is : "
                         + serverResponseMessage + ": " + serverResponseCode);
                  
                 if(serverResponseCode == 200){
                      
                     runOnUiThread(new Runnable() {
                          public void run() {   
                        	
                        	  messageText.setText("File is uploaded.");
                              Toast.makeText(ActivityProfilEdit.this, "File Upload Complete.", 
                                           Toast.LENGTH_SHORT).show();
                          }
                      });                
                 }    
                  
                 //close the streams //
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                   
            } catch (MalformedURLException ex) {
                 
                dialog.dismiss();  
                ex.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(ActivityProfilEdit.this, "MalformedURLException", 
                                                            Toast.LENGTH_SHORT).show();
                    }
                });
                 
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {
                 
                dialog.dismiss();  
                e.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(ActivityProfilEdit.this, "Got Exception : see logcat ", 
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                                                 + e.getMessage(), e);  
            }
            dialog.dismiss();       
            return serverResponseCode; 
             
         } // End else block 
       }
}
