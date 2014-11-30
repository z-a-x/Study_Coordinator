package com.example.study_coordinator;

import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem>{
	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResourceId;
	
	public CustomDrawerAdapter(Context context, int layoutResourceId, List<DrawerItem> listItems){
		super(context, layoutResourceId, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResourceId = layoutResourceId;
	
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
          // TODO Auto-generated method stub

          DrawerItemHolder drawerHolder;
          View view = convertView;

          if (view == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerHolder = new DrawerItemHolder();

                view = inflater.inflate(layoutResourceId, parent, false);
                drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
                drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

                view.setTag(drawerHolder);

          } else {
                drawerHolder = (DrawerItemHolder) view.getTag();

          }

          DrawerItem dItem = this.drawerItemList.get(position);

          drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                      dItem.getImageId()));
          drawerHolder.ItemName.setText(dItem.getItemName());

          return view;
    }

    private static class DrawerItemHolder {
          TextView ItemName;
          ImageView icon;
    }
	
}
