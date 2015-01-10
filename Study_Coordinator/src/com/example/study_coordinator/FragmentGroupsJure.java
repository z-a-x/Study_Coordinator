package com.example.study_coordinator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentGroupsJure extends Fragment {
	ImageView ivIcon;
	TextView tvItemName;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_groups_jure, container, false);

		// TEST BUTTON
		Button button = (Button) view.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openGroupFragment();
			}
		});

		
		return view;
	}

	private void openGroupFragment() {
//		FragmentTransaction transaction = getFragmentManager().beginTransaction();
//		FragmentGroup fragment = new FragmentGroup();
//		Bundle arguments = new Bundle();
//		arguments.putInt("id", 1);
//		fragment.setArguments(arguments);
//		transaction.replace(R.id.content_frame, fragment);
//		transaction.addToBackStack(null);
//		transaction.commit();
//		// NEW (da dela back button)
//		getFragmentManager().executePendingTransactions();
		
		Intent intent = new Intent(getActivity(), FragmentGroup.class);
		Bundle args = new Bundle();
		args.putInt("id", 1);
		intent.putExtras(args);
		startActivity(intent);
	}

}
