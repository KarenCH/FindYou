package com.example.findyouclient.fragment;

import java.util.List;

import com.example.findyouclient.LoginActivity;
import com.example.findyouclient.R;
import com.example.findyouclient.adapter.FootListAdapter;
import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.Footprints;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FootPrintFragment extends Fragment {
	
	private ListView listview;
	
	private MethodController controller;
	
	private List<Footprints> footlist;
	
	
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
		View view=inflater.inflate(R.layout.findyou_footprint, null);
		
		listview=(ListView) view.findViewById(R.id.foot_Listview);
		controller=new MethodController();
		
		footlist=controller.getFootList(LoginActivity.key);
		
		for(int i=0;i<footlist.size();i++){
			Log.d("foot", String.valueOf(footlist.get(i).getX_position()));
		}
		
		
		FootListAdapter adapter=new FootListAdapter(footlist, getActivity());
		
		listview.setAdapter(adapter);
		
		return view;
	}
}
