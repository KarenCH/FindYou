package com.example.findyouclient;


import com.example.findyouclient.fragment.FootPrintFragment;
import com.example.findyouclient.fragment.FriendFragment;
import com.example.findyouclient.fragment.HomeMapFragment;
import com.example.findyouclient.fragment.SettingFragment;
import com.example.findyouclient.residemenu.ResideMenu;
import com.example.findyouclient.residemenu.ResideMenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class HomeActivity extends Activity{

	private ResideMenu resideMenu;
	private HomeMapFragment homeMapFragment;
	private FriendFragment friendFragment;
	private FootPrintFragment footPrintFragment;
	private SettingFragment settingFragment;
	private	FrameLayout ignored_view;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_home);
		
		homeMapFragment=new HomeMapFragment();
		friendFragment=new FriendFragment();
		footPrintFragment=new FootPrintFragment();
		settingFragment=new SettingFragment();
		ignored_view = (FrameLayout) findViewById(R.id.home_frame);
		
		setUpMenu();
		setUpViews();	 
	}
		

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);

		
		// create menu items;
		String titles[] = { "Ê×Ò³", "ºÃÓÑ", "×ã¼£", "ÉèÖÃ" };
		int icon[] = { R.drawable.icon_home, R.drawable.icon_profile,
				R.drawable.icon_calendar, R.drawable.icon_settings };
		
		for (int i = 0; i < titles.length; i++) {
			final int position=i;
			ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
			
			item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					switch(position){
					case 0:
						getFragmentManager().beginTransaction().replace(R.id.home_frame, homeMapFragment).commit();
						resideMenu.addIgnoredView(ignored_view);
						break;
					case 1:
						getFragmentManager().beginTransaction().replace(R.id.home_frame, friendFragment).commit();
						resideMenu.removeIgnoredView(ignored_view);
						break;
					case 2:
						getFragmentManager().beginTransaction().replace(R.id.home_frame, footPrintFragment).commit();
						resideMenu.removeIgnoredView(ignored_view);
						break;
					case 3:
						getFragmentManager().beginTransaction().replace(R.id.home_frame, settingFragment).commit();
						resideMenu.removeIgnoredView(ignored_view);
						break;
					}
					resideMenu.closeMenu();
				}
			});
			resideMenu.addMenuItem(item);
			HomeMapFragment homeMapFragment=new HomeMapFragment();
			getFragmentManager().beginTransaction().replace(R.id.home_frame, homeMapFragment).commit();
		}
		// add gesture operation's ignored views
//		FrameLayout ignored_view = (FrameLayout) findViewById(R.id.home_frame);
//		resideMenu.addIgnoredView(ignored_view);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.onInterceptTouchEvent(ev)
				|| super.dispatchTouchEvent(ev);
	}

	private void setUpViews() {
		Button btn_open = (Button) findViewById(R.id.home_menu);
		btn_open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resideMenu.openMenu();
			}
		});
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
		
			Toast.makeText(HomeActivity.this, "Menu is opened!", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void closeMenu() {
			Toast.makeText(HomeActivity.this, "Menu is closed!", Toast.LENGTH_SHORT)
					.show();
		}
	};
}
