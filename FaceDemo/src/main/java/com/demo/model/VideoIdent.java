package com.demo.model;

import java.util.Date;

public class VideoIdent {
	private static final long serialVersionUID = 1L;
	private int id;
	private String year;
	private int user_id;
	/*private User user;*/
	private String video_url;
	private String video_code;
	private String img_url;
	private String audio_url;
	private String audio_txt;
	private int video_num;
	private int face_score;
	private int video_status;
	private int audio_status;
	private String auditors_txt;
	private String auditors_reason;
	private String txt_remarks;
	private String txt_img;

	private Date add_time;
	private int is_delete;

	// 为了在界面上显示使用
	private String user_name;
	private String user_idcard;
	private String user_township;
	private String user_village;
	private String user_mobile;
	private String user_img;
	private String data_type;
	private String mobile;

	/*
	 * //为了显示使用 private String user_name; private String user_idcard; private
	 * String mobile; private String img_url;
	 */
	
	public int getId() {
		return id;
	}

/*	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getVideo_code() {
		return video_code;
	}

	public void setVideo_code(String video_code) {
		this.video_code = video_code;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getAudio_url() {
		return audio_url;
	}

	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}

	public String getAudio_txt() {
		return audio_txt;
	}

	public void setAudio_txt(String audio_txt) {
		this.audio_txt = audio_txt;
	}

	public int getVideo_num() {
		return video_num;
	}

	public void setVideo_num(int video_num) {
		this.video_num = video_num;
	}

	public int getFace_score() {
		return face_score;
	}

	public void setFace_score(int face_score) {
		this.face_score = face_score;
	}

	public int getVideo_status() {
		return video_status;
	}
	public String getVideo_statuss() {
		if (video_status == 3)
			return "匹配失败";
		else if (video_status == 2)
			return "审核通过";
		else if (video_status == 4)
			return "黑名单";
		else
			return "待审核";

	}
	public void setVideo_status(int video_status) {
		this.video_status = video_status;
	}

	public int getAudio_status() {
		return audio_status;
	}

	public void setAudio_status(int audio_status) {
		this.audio_status = audio_status;
	}

	public String getAuditors_txt() {
		return auditors_txt;
	}

	public void setAuditors_txt(String auditors_txt) {
		this.auditors_txt = auditors_txt;
	}

	public String getAuditors_reason() {
		return auditors_reason;
	}

	public void setAuditors_reason(String auditors_reason) {
		this.auditors_reason = auditors_reason;
	}

	public String getTxt_remarks() {
		return txt_remarks;
	}

	public void setTxt_remarks(String txt_remarks) {
		this.txt_remarks = txt_remarks;
	}

	public String getTxt_img() {
		return txt_img;
	}

	public void setTxt_img(String txt_img) {
		this.txt_img = txt_img;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
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

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	@Override
	public String toString() {
		//"姓名,身份证号码,类型,乡镇办,村名,手机号,是否审核通过,备注"
		return  user_name + ","+ user_idcard + "\t," +data_type+ ","+user_township
				+ "," + user_village + ","+ mobile+","+getVideo_statuss()+","+txt_remarks;
	}
	

}
