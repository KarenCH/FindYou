package com.example.findyouclient.adapter;

import java.util.List;

import com.example.findyouclient.R;
import com.example.findyouclient.pojo.Footprints;
import com.example.findyouclient.util.ImageUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class FootListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Footprints> footlist;

	public FootListAdapter(List<Footprints> footlist, Context context) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.footlist = footlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return footlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return footlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		View view = inflater.inflate(R.layout.findyou_footprint_item, null);

		FrameLayout imagelayout=(FrameLayout) view.findViewById(R.id.foot_imagelayout);
		ImageView imageview = (ImageView) view
				.findViewById(R.id.foot_imageview);

		TextView locationText = (TextView) view
				.findViewById(R.id.foot_loacition);
		TextView descriptionText = (TextView) view
				.findViewById(R.id.foot_description);
		TextView dateText = (TextView) view.findViewById(R.id.foot_nowdate);

		imageview.setImageBitmap(ImageUtil.strToBitmap(footlist.get(arg0).getImg()));
		
		locationText.setText(footlist.get(arg0).getLocation());
		descriptionText.setText(footlist.get(arg0).getDescription());
		dateText.setText(footlist.get(arg0).getNow_date().toString());
		
		imagelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Õ¹Ê¾´óÍ¼
			}
		});
		

		return view;
	}

}
