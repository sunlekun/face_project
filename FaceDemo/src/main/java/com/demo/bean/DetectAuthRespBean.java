package com.demo.bean;
/**
 * @author lekun.sun
 * @version 创建时间：2019年5月8日 下午4:48:29
 * @ClassName 类名称
 * @Description 类描述:实名核身鉴权返回接口接口bean
 */
public class DetectAuthRespBean {
	
	private String respCode;
	
	private String Url;
	
	private String BizToken;
	
	private String RequestId;

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getBizToken() {
		return BizToken;
	}

	public void setBizToken(String bizToken) {
		BizToken = bizToken;
	}

	public String getRequestId() {
		return RequestId;
	}

	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	
}
