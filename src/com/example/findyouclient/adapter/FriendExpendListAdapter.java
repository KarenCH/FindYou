package com.example.findyouclient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.findyouclient.R;
import com.example.findyouclient.pojo.Friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FriendExpendListAdapter extends BaseExpandableListAdapter {

	private List<List<Friend>> gFList=new ArrayList<List<Friend>>();
	private List<String> groupList=new ArrayList<String>();
	private LayoutInflater inflater;
	public FriendExpendListAdapter(List<List<Friend>> friendList,
			List<String> groupList, Context context) {
		super();
		this.gFList = friendList;
		this.groupList = groupList;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return gFList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return gFList.get(groupPosition).get(childPosition).getFriend_id();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view=inflater.inflate(R.layout.findyou_friendlist_item, null);
		
		TextView nameText=(TextView) view.findViewById(R.id.friend_nameText);
		CheckBox check=(CheckBox) view.findViewById(R.id.friend_stateCheck);
		
		nameText.setText(gFList.get(groupPosition).get(childPosition).getFriend_name());
		check.setChecked(gFList.get(groupPosition).get(childPosition).isState());
		
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return gFList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view=inflater.inflate(R.layout.findyou_group_item, null);
		TextView groupname=(TextView) view.findViewById(R.id.group_name);
		TextView numberText=(TextView) view.findViewById(R.id.group_number);
		groupname.setText(groupList.get(groupPosition));
		numberText.setText(String.valueOf(gFList.get(groupPosition).size()));
		
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
