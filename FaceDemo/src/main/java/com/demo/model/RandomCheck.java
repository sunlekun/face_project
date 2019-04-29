package com.demo.model;

import java.sql.Timestamp;

public class RandomCheck {
	private static final long serialVersionUID = 1L;
	private int id;
	private String video_id;
	private String user_id; 
	private int status;
	private Timestamp add_time;
	private int is_delete;
	
	//为了在界面上显示使用
	private String user_name;
	private String user_idcard;
	private String img_url;
	private String  year;
	private Timestamp vadd_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_idcard() {
		return user_idcard;
	}
	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Timestamp getVadd_time() {
		return vadd_time;
	}
	public void setVadd_time(Timestamp vadd_time) {
		this.vadd_time = vadd_time;
	}
 
	 
	
	
}
