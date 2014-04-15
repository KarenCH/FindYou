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
	// ��λ���
	private LocationClient mLocClient;
	private LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	//��λͼ��
	private	locationOverlay myOverlay = null;
	
	private boolean isRequest = false;//�Ƿ��ֶ���������λ
	private boolean isFirstLoc = true;//�Ƿ��״ζ�λ
	private Footprints mfoot;
	
	public MyLocalPosition(MapView view, Context context) {
		super();
		this.mMapView = view;
		this.context = context;
	}
	
	/**
	 * ��λ����
	 */
	public Footprints setLocation(Footprints foot){
		 this.mfoot=foot;
		 
		//��ͼ��ʼ��
        mMapController = mMapView.getController();

        //��λ��ʼ��
        mLocClient = new LocationClient(context);
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//��gps
        option.setCoorType("bd09ll");     //������������
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //��λͼ���ʼ��
        myOverlay = new locationOverlay(mMapView);
		//���ö�λ����
        myOverlay.setData(locData);
	    //��Ӷ�λͼ��
		mMapView.getOverlays().add(myOverlay);
		myOverlay.enableCompass();
        myOverlay.setLocationMode(LocationMode.NORMAL);	
		//�޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
		
		return mfoot;
	}
	
	/**
     * ��λSDK��������
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();            
            //�������ʾ��λ����Ȧ����accuracy��ֵΪ0����
            locData.accuracy = location.getRadius();
            // �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
            locData.direction = location.getDerect();
            //���¶�λ����
            myOverlay.setData(locData);
            //����ͼ������ִ��ˢ�º���Ч
            mMapView.refresh();
            //���ֶ�����������״ζ�λʱ���ƶ�����λ��
            if (isRequest || isFirstLoc){
            	//�ƶ���ͼ����λ��
            	Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;

            }
            //�״ζ�λ���
            isFirstLoc = false;
            
          
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }

    
    /**
     * �̳�MyLocationOverlay��дdispatchTapʵ�ֵ������
     * @author ������
     *
     */
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		@Override
  		protected boolean dispatchTap() {
  			//�������¼�,�����ҵ�λ����ϸ��Ϣ��

  			return true;
  		}
  		
  	}

}