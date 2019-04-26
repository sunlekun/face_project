package com.demo.model;

import java.sql.Timestamp;

public class Role {
	private static final long serialVersionUID = 1L;
	private int id;
	private String role_name;
	private int role_type;
	private int is_sys;
	private  Timestamp add_time;
	private int is_delete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getRole_type() {
		return role_type;
	}
	public void setRole_type(int role_type) {
		this.role_type = role_type;
	}
	public int getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(int is_sys) {
		this.is_sys = is_sys;
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

	 

}
