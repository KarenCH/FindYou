package com.example.findyouclient.pojo;

public class Friend {

	private int id;//用户id
	private int friend_id;//好友id
	private String friend_name;//好友名
	private boolean state;//状态
	private String group;//分组
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	
}
