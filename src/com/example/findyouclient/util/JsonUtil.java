package com.example.findyouclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import com.example.findyouclient.pojo.Footprints;
import com.example.findyouclient.pojo.Friend;
import com.example.findyouclient.pojo.State;
import com.example.findyouclient.pojo.UserInfo;

public class JsonUtil {

	/**
	 * 把访问结果的字符串转换成State类并返回
	 * 
	 * @param state
	 *            访问结果的字符串
	 * @return 解析结果
	 */
	public State getState(String state) {
		State myState = new State();
		try {
			JSONObject obj = new JSONObject(state);
			myState.setStateCode(obj.getInt("stateCode"));
			myState.setMessage(obj.getString("Message"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myState;
	}
	
	/**
	 * 获取好友列表
	 * @param str
	 * @return 好友集合
	 */
	public List<Friend> getFriendList(String str){
		List<Friend> friendlist=new ArrayList<Friend>();
		try {
			JSONArray array=new JSONArray(str);
			for(int count=0;count<array.length();count++){
				Friend friend=new Friend();
				friend.setId(Integer.parseInt(array.getJSONObject(count).getString("id")));
				friend.setFriend_id(Integer.parseInt(array.getJSONObject(count).getString("friend_id")));
				friend.setState(Boolean.parseBoolean(array.getJSONObject(count).getString("state")));
				friend.setFriend_name(array.getJSONObject(count).getString("friend_name"));
				friend.setGroup(array.getJSONObject(count).getString("group"));
				friendlist.add(friend);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friendlist;
		
	}
	
	/**
	 * 获取分组集合
	 * @param str
	 * @return
	 */
	public List<String> getGroupList(String str){
		List<String> groupList=new ArrayList<String>();
		try {
			JSONArray array=new JSONArray(str);
			for(int count=0;count<array.length();count++){
				groupList.add(array.getJSONObject(count).getString("groupName"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupList;
		
	}
	
	/**
	 * 获取足迹集合
	 */
	@SuppressLint("SimpleDateFormat")
	public List<Footprints> getFootList(String str){
		
		List<Footprints> footList=new ArrayList<Footprints>();
		
		try {
			JSONArray array=new JSONArray(str);
			for(int count=0;count<array.length();count++){
				
				Footprints footprints=new Footprints();
				
				footprints.setId(array.getJSONObject(count).getInt("id"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(array.getJSONObject(count).getString("now_date"));
				footprints.setNow_date(date);
				footprints.setX_position(array.getJSONObject(count).getDouble("x_position"));
				footprints.setY_position(array.getJSONObject(count).getDouble("y_position"));
				footprints.setLocation(array.getJSONObject(count).getString("location"));
				footprints.setDescription(array.getJSONObject(count).getString("description"));

				footprints.setImg(array.getJSONObject(count).getString("img"));
				footList.add(footprints);
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return footList;
		
	}
	
	
	public UserInfo getInfo(String str){
		UserInfo info=new UserInfo();
		
		try {
			JSONObject obj=new JSONObject(str);
			info.setName(obj.getString("name"));
			info.setSex(obj.getString("sex")=="true"?true:false);
			info.setAge(Integer.parseInt(obj.getString("age")));
			info.setBirthday(obj.getString("birthday"));
			info.setIntroduction(obj.getString("introduction"));
			info.setAddress(obj.getString("address"));
			info.setProfession(obj.getString("profession"));
			info.setIcon(obj.getString("icon"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return info;
	}
}
