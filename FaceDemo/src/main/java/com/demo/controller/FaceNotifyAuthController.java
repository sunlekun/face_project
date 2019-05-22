package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.druid.util.StringUtils;
import com.demo.core.TxFaceService;
import com.demo.util.ApplicationContextUtil;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月22日 上午11:25:35
 * @ClassName 类名称
 * @Description 类描述
 */
public class FaceNotifyAuthController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TxFaceService txFaceService = ApplicationContextUtil.getBean(TxFaceService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String BizToken = request.getParameter("BizToken");
		if(!StringUtils.isEmpty(BizToken)){
			txFaceService.notifyProcess(BizToken);
			
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<h1>认证成功</h1>");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request,response);
	}

}
