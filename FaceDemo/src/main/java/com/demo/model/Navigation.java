package com.demo.model;

import java.sql.Timestamp;
import java.util.List;

public class Navigation implements Comparable<Navigation>{
	private static final long serialVersionUID = 1L;
	private int id;
	private int parent_id;
	private int channel_id;
	private String nav_type;
	private String name;
	private String title;
	private String sub_title;
	private String icon_url;
	private String link_url;
	private int sort_id;
	private int is_lock;
	private String remark;
	private String action_type;
	private Timestamp add_time;
	private int is_delete;
	private int level; 
	// 子菜单
	private List<Navigation> children;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getNav_type() {
		return nav_type;
	}
	public void setNav_type(String nav_type) {
		this.nav_type = nav_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}
	public int getIs_lock() {
		return is_lock;
	}
	public void setIs_lock(int is_lock) {
		this.is_lock = is_lock;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public List<Navigation> getChildren() {
		return children;
	}
	public void setChildren(List<Navigation> children) {
		this.children = children;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public int compareTo(Navigation o) {
 
		return this.sort_id-o.sort_id;
	}
	
	 

}
