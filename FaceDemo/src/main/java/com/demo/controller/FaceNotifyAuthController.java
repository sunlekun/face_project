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
		String status = "";
		if(!StringUtils.isEmpty(BizToken)){
			status=txFaceService.notifyProcess(BizToken);
			
		}
		/*response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<h1>认证完成，详细认证结果请咨询当地乡镇部门</h1>");*/
		String message="";
		String img="";
		if("0".equals(status)){
        	message="验证成功";
        	img="ok.jpg";
        }else{
        	message="验证失败";
        	img="err.jpg";
        }
		request.setAttribute("message",message);
		request.setAttribute("img",img);
		response.sendRedirect("FaceDemo/user/result.jsp");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request,response);
	}

}
