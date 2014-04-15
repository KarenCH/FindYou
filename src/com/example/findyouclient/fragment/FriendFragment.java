package com.example.findyouclient.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.findyouclient.LoginActivity;
import com.example.findyouclient.R;
import com.example.findyouclient.adapter.FriendExpendListAdapter;
import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.Friend;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FriendFragment extends Fragment {

	private List<String> groupList=new ArrayList<String>();
	
	private List<Friend> friendList=new ArrayList<Friend>();
	
	private List<List<Friend>> gFlist=new ArrayList<List<Friend>>();
	
	private MethodController controller;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.findyou_friend, null);
		ExpandableListView listView=(ExpandableListView) view.findViewById(R.id.friend_listView);
		controller=new MethodController();
		groupList=controller.getGroup(LoginActivity.key);
		friendList=controller.getFriendList(LoginActivity.key);
		for(int i=0;i<groupList.size();i++){
			List<Friend> groupitemlist=new ArrayList<Friend>();
			for(int j=0;j<friendList.size();j++){
				if(groupList.get(i).equals(friendList.get(j).getGroup())){
					groupitemlist.add(friendList.get(j));
					
				}
			}
			gFlist.add(groupitemlist);
		}
		
		
		
		FriendExpendListAdapter adapter=new FriendExpendListAdapter(gFlist, groupList, getActivity());
		listView.setAdapter(adapter);
		return view;
	}
}
