package com.example.findyouclient.fragment;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.example.findyouclient.LoginActivity;
import com.example.findyouclient.R;
import com.example.findyouclient.ShareFootActivity;
import com.example.findyouclient.bmap.MyLocalPosition;
import com.example.findyouclient.pojo.Footprints;
import com.example.findyouclient.util.DemoApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeMapFragment extends Fragment {

	/**
	 * MapView 是地图主控件
	 */
	private MapView mapView = null;
	/**
	 * 用MapController完成地图控制
	 */
	private MapController mMapController = null;
	/**
	 * MKMapViewListener 用于处理地图事件回调
	 */
	MKMapViewListener mMapListener = null;
	private DemoApplication app;
	private Button shareButton;
	private Context context;
	private Footprints foot;
	private MyLocalPosition position;

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

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = inflater.getContext();
		foot = new Footprints();
		foot.setId(Integer.parseInt(LoginActivity.key));
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		app = (DemoApplication) getActivity().getApplication();
		if (app.getmBMapManager() == null) {
			app.setmBMapManager(new BMapManager(getActivity()));
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.getmBMapManager().init(DemoApplication.strKey,
					new DemoApplication.MyGeneralListener());
		}
		/**
		 * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		 */
		View view = inflater.inflate(R.layout.findyou_map, null);
		mapView = (MapView) view.findViewById(R.id.map_bmapView);

		shareButton = (Button) view.findViewById(R.id.map_share);
		/**
		 * 获取地图控制器
		 */
		mMapController = mapView.getController();
		/**
		 * 设置地图是否响应点击事件 .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(12);

		/**
		 * 将地图移动至指定点
		 * 使用百度经纬度坐标，可以通过http://api.map.baidu.com/lbsapi/getpoint/index
		 * .html查询地理坐标 如果需要在百度地图上显示使用其他坐标系统的位置，请发邮件至mapapi@baidu.com申请坐标转换接口
		 */
		// 定位
		position = new MyLocalPosition(mapView, context);
		foot=position.setLocation(foot);

		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
				 * mMapController.enableClick(true); 时，此回调才能被触发
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null) {
					title = mapPoiInfo.strText;
					Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT)
							.show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 * 地图完成带动画的操作（如: animationTo()）后，此回调被触发
				 */
			}

			/**
			 * 在此处理地图载完成事件
			 */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(getActivity(), "地图加载完成", Toast.LENGTH_SHORT)
						.show();

			}
		};
		mapView.regMapViewListener(DemoApplication.getInstance()
				.getmBMapManager(), mMapListener);

		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent intent = new Intent(context, ShareFootActivity.class);
				Bundle bundler = new Bundle();
				bundler.putSerializable("foot", foot);
				intent.putExtras(bundler);
				startActivity(intent);

			}
		});

		return view;
	}

	@Override
	public void onPause() {
		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		/**
		 * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		mapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		mapView.onRestoreInstanceState(savedInstanceState);
	}
}
