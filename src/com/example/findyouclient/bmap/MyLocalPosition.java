package com.example.findyouclient.bmap;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.findyouclient.pojo.Footprints;

public class MyLocalPosition {

	private MapView mMapView;
	private Context context;
	private MapController mMapController=null;
	// 定位相关
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	//定位图层
	private	locationOverlay myOverlay = null;
	
	private boolean isRequest = false;//是否手动触发请求定位
	private boolean isFirstLoc = true;//是否首次定位
	private Footprints mfoot;
	
	public MyLocalPosition(MapView view, Context context) {
		super();
		this.mMapView = view;
		this.context = context;
	}
	
	/**
	 * 定位功能
	 */
	public Footprints setLocation(Footprints foot){
		 this.mfoot=foot;
		 
		//地图初始化
        mMapController = mMapView.getController();

        //定位初始化
        mLocClient = new LocationClient(context);
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //定位图层初始化
        myOverlay = new locationOverlay(mMapView);
		//设置定位数据
        myOverlay.setData(locData);
	    //添加定位图层
		mMapView.getOverlays().add(myOverlay);
		myOverlay.enableCompass();
        myOverlay.setLocationMode(LocationMode.NORMAL);	
		//修改定位数据后刷新图层生效
		mMapView.refresh();
		
		return mfoot;
	}
	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();            
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            //更新定位数据
            myOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            mMapView.refresh();
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isFirstLoc){
            	//移动地图到定位点
            	Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;

            }
            //首次定位完成
            isFirstLoc = false;
            
          
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }

    
    /**
     * 继承MyLocationOverlay重写dispatchTap实现点击处理
     * @author 陈智磊
     *
     */
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		@Override
  		protected boolean dispatchTap() {
  			//处理点击事件,弹出我的位置详细信息框

  			return true;
  		}
  		
  	}

}