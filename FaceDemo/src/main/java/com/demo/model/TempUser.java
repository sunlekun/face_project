package com.demo.model;

import java.sql.Timestamp;

public class TempUser {
	private static final long serialVersionUID = 1L;
	private int	id;
	private String user_name;
	private String user_idcard;
	private String user_township;
	private String user_village;
	private String data_type;
	private String mobile;
	
	private int status;
	private String status_reason;
	
	private String thumb_path;//信息采集图片上传路径
	private String original_path;
	
	private Timestamp add_time;
	private Timestamp audit_time;
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
	public int getStatus() {
		return status;
	}
	public String getStatuss() {
		if (status == 3)
			return "审核未通过";
		else if (status == 2)
			return "审核通过";
		else
			return "待审核";
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_reason() {
		return status_reason;
	}
	public void setStatus_reason(String status_reason) {
		this.status_reason = status_reason;
	}
	public String getThumb_path() {
		return thumb_path;
	}
	public void setThumb_path(String thumb_path) {
		this.thumb_path = thumb_path;
	}
	public String getOriginal_path() {
		return original_path;
	}
	public void setOriginal_path(String original_path) {
		this.original_path = original_path;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	
	public Timestamp getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Timestamp audit_time) {
		this.audit_time = audit_time;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	

}
