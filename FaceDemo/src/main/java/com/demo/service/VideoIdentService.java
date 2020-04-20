package com.demo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.demo.model.VideoIdent;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

 
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface VideoIdentService {
	public List<String>  findVidoIdentYears();
	public VideoIdent findVideoIdentById(Integer id);
	public List<VideoIdent> findVideoListByIdAndTime(int id, String time);
	public List<VideoIdent> findVideoListByUserId( Integer user_id);
	public List<VideoIdent> findVideoByUserId( Integer user_id);
	void insertVL(VideoIdent videoIdent);
	public List<VideoIdent> findVideoListByMultiCondition(Map map);
	public Page<VideoIdent> findVideoListByMultiCondition(Map map,Integer pageSize,Integer pageNumber);
	public void deleteVideoIdentBatch(String[] ids);
	VideoIdent findVideoListByVideoIdentId(Integer id);
	public void updateById(Integer id, String auditors_reason,
			String txt_remarks, Integer video_status,String auditors_txt,String txt_img);
	public void delrepeat(); 
}
