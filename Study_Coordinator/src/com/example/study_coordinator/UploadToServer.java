package com.example.study_coordinator;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sql.ConnectionPoolDataSource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
  
public class UploadToServer extends Activity {     
    TextView messageText;
    Button uploadButton;
    Button selectButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    ImageView iv;
    String realPath;        
    String upLoadServerUri = null;
    String uploadFilePath;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_server);
        selectButton = (Button)findViewById(R.id.selectButton);  
        uploadButton = (Button)findViewById(R.id.uploadButton);
        messageText  = (TextView)findViewById(R.id.messageText);
        iv = (ImageView)findViewById(R.id.profil_image_upload); 
        
         
        /************* Php script path ****************/
        DatabaseConnect dc = new DatabaseConnect();        
        upLoadServerUri = dc.getIpAddress()+"UploadToServer.php";
        
        //klik na upload gumb
        uploadButton.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                uploadFilePath = messageText.getText().toString();
                dialog = ProgressDialog.show(UploadToServer.this, "", "Uploading file...", true);
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
    	        iv.setImageURI(selectedImage);    	        
    	        messageText.setText(getRealPathFromURI(getApplicationContext(), selectedImage));
    	    }
    	break; 
    	}
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
                                Toast.makeText(UploadToServer.this, "File Upload Complete.", 
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
                          Toast.makeText(UploadToServer.this, "MalformedURLException", 
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
                          Toast.makeText(UploadToServer.this, "Got Exception : see logcat ", 
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