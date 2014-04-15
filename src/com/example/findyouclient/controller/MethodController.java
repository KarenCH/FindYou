package com.example.findyouclient.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;

import com.example.findyouclient.pojo.Footprints;
import com.example.findyouclient.pojo.Friend;
import com.example.findyouclient.pojo.State;
import com.example.findyouclient.pojo.UserInfo;
import com.example.findyouclient.util.HttpClientUtil;
import com.example.findyouclient.util.JsonUtil;

public class MethodController {

	/**
	 * 登录方法
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public State login(String account, String password) {
		State state = null;

		try {
			account = URLEncoder.encode(account, "utf-8");
			password = URLEncoder.encode(password, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "2"));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		String type = "login";
		// 向服务端发出请求
		String str = HttpClientUtil.postRequest(type, params);

		if ("服务器端异常".equals(str)) {
			state = new State();
			state.setStateCode(-10);
			state.setMessage("服务器端异常");
		} else {
			state = new JsonUtil().getState(str);
		}
		return state;

	}

	/**
	 * 用户注册
	 * 
	 * @param account
	 * @param password
	 * @param email
	 * @return
	 */
	public State register(String account, String password, String email) {
		State state = null;
		try {
			account = URLEncoder.encode(account, "utf-8");
			password = URLEncoder.encode(password, "utf-8");
			email = URLEncoder.encode(email, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String params = "methodID=1&account=" + account + "&password="
//				+ password + "&email=" + email;
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "2"));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));
		
		String type = "login";
		String str = HttpClientUtil.postRequest(type, params);

		if ("服务器端异常".equals(str)) {
			state = new State();
			state.setStateCode(-10);
			state.setMessage("服务器端异常");
		} else {
			state = new JsonUtil().getState(str);
		}
		return state;

	}

	/**
	 * 找回密码
	 * 
	 * @param account
	 * @param email
	 * @return
	 */
	public State forgetPassword(String account, String email) {
		State state = null;
		try {
			account = URLEncoder.encode(account, "utf-8");
			email = URLEncoder.encode(email, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String params = "methodID=3&account=" + account + "&email=" + email;
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "2"));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("email", email));
		
		String type = "login";
		String str = HttpClientUtil.postRequest(type, params);

		if ("服务器端异常".equals(str)) {
			state = new State();
			state.setStateCode(-10);
			state.setMessage("服务器端异常");
		} else {
			state = new JsonUtil().getState(str);
		}
		return state;

	}

	/**
	 * 获取分组集合
	 * 
	 * @param key
	 * @return 分组集合
	 */
	public List<String> getGroup(String key) {
		List<String> groupList = new ArrayList<String>();

		String type = "group";
//		String params = "methodID=1&Key=" + key;
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "1"));
		params.add(new BasicNameValuePair("Key", key));

		String str = HttpClientUtil.postRequest(type, params);
		if ("服务器端异常".equals(str)) {
			return null;
		} else {
			groupList = new JsonUtil().getGroupList(str);
			return groupList;
		}

	}

	/**
	 * 获取好友列表
	 * 
	 * @param key
	 * @return 好友集合
	 */
	public List<Friend> getFriendList(String key) {
		List<Friend> friendlist = new ArrayList<Friend>();
		try {
			key = URLEncoder.encode(key, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = "friend";
//		String params = "methodID=3&Key=" + key;
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "3"));
		params.add(new BasicNameValuePair("Key", key));
		

		String str = HttpClientUtil.postRequest(type, params);

		if ("服务器端异常".equals(str)) {
			return null;
		} else {
			friendlist = new JsonUtil().getFriendList(str);
			return friendlist;
		}

	}

	/**
	 * 获取足迹集合
	 */
	public List<Footprints> getFootList(String key) {

		List<Footprints> footlist = new ArrayList<Footprints>();

		try {
			key = URLEncoder.encode(key, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String type = "footprints";
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "2"));
		params.add(new BasicNameValuePair("Key", key));

		String str = HttpClientUtil.postRequest(type, params);

		if ("服务器端异常".equals(str)) {
			return null;
		} else {
			footlist = new JsonUtil().getFootList(str);
			return footlist;
		}

	}

	/**
	 * 增加足迹
	 * @param foot
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public State addFootprints(Footprints foot) {

		State state = new State();
		String type = "footprints";
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "1"));
	
			params.add(new BasicNameValuePair("Key", String.valueOf(foot.getId())));
			params.add(new BasicNameValuePair("now_date", format.format(foot.getNow_date())));
			params.add(new BasicNameValuePair("x_position", String.valueOf(foot.getX_position())));
			params.add(new BasicNameValuePair("y_position", String.valueOf(foot.getY_position())));
			params.add(new BasicNameValuePair("location", foot.getLocation()));
//			Log.d("Location--------------",  URLEncoder.encode(foot.getLocation(), "utf-8"));
			params.add(new BasicNameValuePair("description", foot.getDescription()));
			params.add(new BasicNameValuePair("img", foot.getImg()));
		
		
		
		String str=HttpClientUtil.postRequest(type, params);
		
		if ("服务器端异常".equals(str)) {
			state = new State();
			state.setStateCode(-10);
			state.setMessage("服务器端异常");
		} else {

			state = new JsonUtil().getState(str);
		}
		return state;
	}
	
	
	
	/**
	 * 获取用户信息
	 * @param key
	 * @return
	 */
	public UserInfo getUserInfo(String key){
		UserInfo info=new UserInfo();

		String type = "info";
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "1"));
		params.add(new BasicNameValuePair("Key", key));

		String str=HttpClientUtil.postRequest(type, params);
		if ("服务器端异常".equals(str)) {
			return null;
		} else {
			info=new JsonUtil().getInfo(str);
		}
		return info;
	}
	
	/**
	 * 增加或修改用户信息
	 * @param key
	 * @param info
	 * @return
	 */
	public State setUserInfo(String key,UserInfo info){
		State state=new State();
		String type="info";
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("methodID", "2"));
		params.add(new BasicNameValuePair("Key", key));
		params.add(new BasicNameValuePair("name", info.getName()));
		params.add(new BasicNameValuePair("sex", String.valueOf(info.isSex())));
		params.add(new BasicNameValuePair("birthday", info.getBirthday()));
		params.add(new BasicNameValuePair("introduction", info.getIntroduction()));
		params.add(new BasicNameValuePair("address", info.getAddress()));
		params.add(new BasicNameValuePair("profession", info.getProfession()));
		params.add(new BasicNameValuePair("icon", info.getIcon()));
		
		String str=HttpClientUtil.postRequest(type, params);
		if ("服务器端异常".equals(str)) {
			state = new State();
			state.setStateCode(-10);
			state.setMessage("服务器端异常");
		} else {

			state = new JsonUtil().getState(str);
		}
		return state;
	}
}
