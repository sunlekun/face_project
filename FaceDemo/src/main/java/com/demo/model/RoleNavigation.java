package com.demo.model;

import java.sql.Timestamp;
import java.util.List;

public class RoleNavigation {
	private static final long serialVersionUID = 1L;
	private int id;
	private int role_id;
	private int navigation_id;
	private String nav_name;
	private String action_type; 
	private Timestamp add_time;
	private int is_delete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getNavigation_id() {
		return navigation_id;
	}
	public void setNavigation_id(int navigation_id) {
		this.navigation_id = navigation_id;
	}
	public String getNav_name() {
		return nav_name;
	}
	public void setNav_name(String nav_name) {
		this.nav_name = nav_name;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
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
