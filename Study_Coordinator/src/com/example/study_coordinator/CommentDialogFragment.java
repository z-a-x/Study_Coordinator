package com.example.study_coordinator;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.study_coordinator.asynctasks.LookUp;
import com.example.study_coordinator.asynctasks.addComment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

public class CommentDialogFragment extends DialogFragment {
    String eventId;
    String userId;
    String comment;
    
    public CommentDialogFragment(String eventId, String userId){
    	this.eventId = eventId;
    	this.userId = userId;
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_comment, null))
         
        
        // Add action buttons
               .setPositiveButton("Comment", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   Dialog f = (Dialog) dialog;
                	   EditText tv = (EditText) f.findViewById(R.id.commentText);
                	   comment = tv.getText().toString();
                	   addremoveUser();
                		
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       CommentDialogFragment.this.getDialog().cancel();
                   }
               });      
        return builder.create();
    }  
	
	// pridobi seznam ljudi, ki se bodo udeležili eventa
    private void addremoveUser() {
		LookUp eventFetcher = new addComment(getActivity().getApplicationContext()) {
			@Override
			public void onSuccessfulFetch(JSONObject result) throws JSONException {
				
			}
		};
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
		eventFetcher.execute("action",eventId + "." + userId + "." + "1000000000"  + "." + "'" + comment + "'"); //dateFormat.format(date)
	}


}
