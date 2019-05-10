package com.demo.model;

import java.sql.Timestamp;

public class DetectAuth {
	private static final long serialVersionUID = 1L;
	private String bizToken	;
	private String url	;
	private Timestamp create_time;
	private String request_id	;
	private String user_idcard	;
	public String getBizToken() {
		return bizToken;
	}
	public void setBizToken(String bizToken) {
		this.bizToken = bizToken;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getUser_idcard() {
		return user_idcard;
	}
	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}
	
}
