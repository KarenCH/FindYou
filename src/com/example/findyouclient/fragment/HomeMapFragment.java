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
	 * MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mapView = null;
	/**
	 * ��MapController��ɵ�ͼ����
	 */
	private MapController mMapController = null;
	/**
	 * MKMapViewListener ���ڴ����ͼ�¼��ص�
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
		 * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager. BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
		 * ���ڵ�ͼ��ͼģ�����ٺ����٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ������
		 */
		app = (DemoApplication) getActivity().getApplication();
		if (app.getmBMapManager() == null) {
			app.setmBMapManager(new BMapManager(getActivity()));
			/**
			 * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
			 */
			app.getmBMapManager().init(DemoApplication.strKey,
					new DemoApplication.MyGeneralListener());
		}
		/**
		 * ����MapView��setContentView()�г�ʼ��,��������Ҫ��BMapManager��ʼ��֮��
		 */
		View view = inflater.inflate(R.layout.findyou_map, null);
		mapView = (MapView) view.findViewById(R.id.map_bmapView);

		shareButton = (Button) view.findViewById(R.id.map_share);
		/**
		 * ��ȡ��ͼ������
		 */
		mMapController = mapView.getController();
		/**
		 * ���õ�ͼ�Ƿ���Ӧ����¼� .
		 */
		mMapController.enableClick(true);
		/**
		 * ���õ�ͼ���ż���
		 */
		mMapController.setZoom(12);

		/**
		 * ����ͼ�ƶ���ָ����
		 * ʹ�ðٶȾ�γ�����꣬����ͨ��http://api.map.baidu.com/lbsapi/getpoint/index
		 * .html��ѯ�������� �����Ҫ�ڰٶȵ�ͼ����ʾʹ����������ϵͳ��λ�ã��뷢�ʼ���mapapi@baidu.com��������ת���ӿ�
		 */
		// ��λ
		position = new MyLocalPosition(mapView, context);
		foot=position.setLocation(foot);

		/**
		 * MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
		 */
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * �ڴ˴����ͼ�ƶ���ɻص� ���ţ�ƽ�ƵȲ�����ɺ󣬴˻ص�������
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * �ڴ˴����ͼpoi����¼� ��ʾ��ͼpoi���Ʋ��ƶ����õ� ���ù���
				 * mMapController.enableClick(true); ʱ���˻ص����ܱ�����
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
				 * �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ���� ���ڴ˱����ͼ���洢�豸
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 * ��ͼ��ɴ������Ĳ�������: animationTo()���󣬴˻ص�������
				 */
			}

			/**
			 * �ڴ˴����ͼ������¼�
			 */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(getActivity(), "��ͼ�������", Toast.LENGTH_SHORT)
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
		 * MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
		 */
		mapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		/**
		 * MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
		 */
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		/**
		 * MapView������������Activityͬ������activity����ʱ�����MapView.destroy()
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
