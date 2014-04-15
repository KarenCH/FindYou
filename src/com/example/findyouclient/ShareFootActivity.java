package com.example.findyouclient;

import java.util.Date;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.Footprints;
import com.example.findyouclient.pojo.State;
import com.example.findyouclient.util.DemoApplication;
import com.example.findyouclient.util.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShareFootActivity extends Activity implements Handler.Callback{
	
	private Handler handler;
	private EditText description;
	private TextView addressText;
	private ImageView imgBtn;
	private Button shareBtn;
	private static final int RESULT_LOAD_IMAGE=0x111;
	private String picturePath;
	private String pictureType;
	private Footprints foot;
	private MethodController controller;
	private DemoApplication app;
	private LocationManager lm;
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	private LocationClientOption option;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_sharefoot);
		handler=new Handler(this);
		description=(EditText) findViewById(R.id.sharefoot_text);
		imgBtn=(ImageView) findViewById(R.id.sharefoot_imgbtn);
		shareBtn=(Button) findViewById(R.id.share_shareBtn);
		addressText=(TextView) findViewById(R.id.sharefoot_address);
		foot=(Footprints) getIntent().getSerializableExtra("foot");
		controller=new MethodController();
		
		app = (DemoApplication) ShareFootActivity.this.getApplication();
		if (app.getmBMapManager() == null) {
			app.setmBMapManager(new BMapManager(ShareFootActivity.this));
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.getmBMapManager().init(DemoApplication.strKey,
					new DemoApplication.MyGeneralListener());
		}
		
		lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Toast.makeText(ShareFootActivity.this, "请开启gps，否则将无法精确定位您的位置！", Toast.LENGTH_LONG).show();
		}
		
		// 定位初始化
		mLocClient = new LocationClient(ShareFootActivity.this);
		locData = new LocationData();
		option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		mLocClient.setLocOption(option);
		mLocClient.start();
		mLocClient.registerLocationListener(myListener);
		
		
		
		imgBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent imageIntent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(imageIntent, RESULT_LOAD_IMAGE);
				
			}
		});
		
		description.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				foot.setDescription(description.getText().toString());
			}
		});
		
		shareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Date date=new Date(System.currentTimeMillis());
				foot.setNow_date(date);
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Bitmap newbitmap=BitmapFactory.decodeFile(picturePath);
						Log.v("picturelength", String.valueOf(ImageUtil.Bitmap2Bytes(newbitmap).length/1024));
						
						Bitmap combitmap=ImageUtil.compressImage(newbitmap,pictureType);
						foot.setImg(ImageUtil.bitmapToStr(combitmap));
						
						handler.sendEmptyMessage(1);
					}
				}).start();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	        Uri selectedImage = data.getData();
	        String[] filePathColumn = { MediaStore.Images.Media.DATA };

	        Cursor cursor = getContentResolver().query(selectedImage,
	                filePathColumn, null, null, null);
	        cursor.moveToFirst();

	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        picturePath = cursor.getString(columnIndex);
	        pictureType=picturePath.split("\\.")[picturePath.split("\\.").length-1];
	        cursor.close();
	        
			Bitmap newbitmap=BitmapFactory.decodeFile(picturePath);
			
			Log.d("width", String.valueOf(imgBtn.getWidth()));
			Log.d("hight", String.valueOf(imgBtn.getHeight()));
			
			Bitmap turnbitmap=ImageUtil.extractMiniThumb(newbitmap, imgBtn.getWidth(), imgBtn.getHeight());
		
			imgBtn.setImageBitmap(turnbitmap);
		}
	}
	
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			
			if(location==null){
				return;
			}
			
			locData.latitude=location.getLatitude();
			locData.longitude=location.getLongitude();
			foot.setX_position(locData.latitude);
			foot.setY_position(locData.longitude);
			
		
			MKSearch search = new MKSearch();
			search.init(app.getmBMapManager(), new MyMKSearchListener());

			int weidu = (int) (locData.latitude * 1000000);
			int jindu = (int) (locData.longitude * 1000000);
			search.reverseGeocode(new GeoPoint(weidu, jindu));
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}

	}
	
	class MyMKSearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			if (arg0 == null) {
				addressText.setText("位置无法确认");
				foot.setLocation("未知位置");
			} else {
				addressText.setText(arg0.strAddr);
				foot.setLocation(arg0.strAddr);
			}
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {

		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {

		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		State state=controller.addFootprints(foot);
		Toast.makeText(ShareFootActivity.this, state.getMessage(), Toast.LENGTH_SHORT).show();
		return true;
	}

}
