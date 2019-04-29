package com.demo.model;

import java.sql.Timestamp;

public class User {
	private static final long serialVersionUID = 1L;
	private int	id;
	private String user_name;
	private String user_idcard;
	private String user_township;
	private String user_village;
	private String data_type;
	private String mobile;
	private String img_url;
	private Timestamp add_time;
	private Timestamp update_time;
	private int is_delete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getUser_township() {
		return user_township;
	}
	public void setUser_township(String user_township) {
		this.user_township = user_township;
	}
	public String getUser_village() {
		return user_village;
	}
	public void setUser_village(String user_village) {
		this.user_village = user_village;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

}
