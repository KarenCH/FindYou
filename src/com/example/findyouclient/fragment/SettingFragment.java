package com.example.findyouclient.fragment;

import com.example.findyouclient.R;
import com.example.findyouclient.UserInfoActivity;
import com.example.findyouclient.util.ImageUtil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

	private Button userInfoBtn;
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
		View view=inflater.inflate(R.layout.findyou_setting, null);
		
		userInfoBtn=(Button) view.findViewById(R.id.setting_userinfo);
		
		userInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}
	
	
}
