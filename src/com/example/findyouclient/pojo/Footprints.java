package com.example.findyouclient.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 足迹类
 * @author 陈智磊
 *
 */
public class Footprints implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//用户id
	private Date now_date;//当前日期
	private double x_position;//x坐标
	private double y_position;//y坐标
	private String location;//位置
	private String description;//描述
//	private byte[] img;//图片
	private String img;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getNow_date() {
		return now_date;
	}
	public void setNow_date(Date now_date) {
		this.now_date = now_date;
	}
	public double getX_position() {
		return x_position;
	}
	public void setX_position(double x_position) {
		this.x_position = x_position;
	}
	public double getY_position() {
		return y_position;
	}
	public void setY_position(double y_position) {
		this.y_position = y_position;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public byte[] getImg() {
//		return img;
//	}
//	public void setImg(byte[] img) {
//		this.img = img;
//	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
	
}
