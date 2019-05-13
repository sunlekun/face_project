package com.demo.core;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.bean.DetectAuthReqBean;
import com.demo.bean.DetectAuthRespBean;
import com.demo.bean.GetDetectInfoReq;
import com.demo.model.DetectAuth;
import com.demo.model.TempUser;
import com.demo.service.DetectAuthService;
import com.demo.service.VideoIdentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.DetectAuthRequest;
import com.tencentcloudapi.faceid.v20180301.models.DetectAuthResponse;
import com.tencentcloudapi.faceid.v20180301.models.GetDetectInfoRequest;
import com.tencentcloudapi.faceid.v20180301.models.GetDetectInfoResponse;

/**
 * @author lekun.sun
 * @version 创建时间：2019年4月28日 下午2:43:01
 * @ClassName 类名称
 * @Description 类描述：腾讯人脸识别调用服务
 */
@Component
public class TxFaceService {
	public static Logger log= Logger.getLogger(TxFaceService.class);
	@Autowired
	public DetectAuthService detectAuthService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	/**
	 * 密钥ID
	 */
	@Value("#{sysConfig.SecretId}")
    public String secretId;
	/**
	 * 密钥值
	 */
	@Value("#{sysConfig.SecretKey}")
	public String secretKey;
	/**
	 * 请求地址
	 */
	@Value("#{sysConfig.faceUrl}")
    public String url;
	/**
	 * 地区
	 */
	@Value("#{sysConfig.region}")
    public String region;
	
	/**
	 * 业务编号
	 */
	@Value("#{sysConfig.ruleId}")
    public String ruleId;
	
	/**
	 * 版本号
	 */
	@Value("#{sysConfig.version}")
    public String version;
	
	/**
	 * 接口名称
	 */
	@Value("#{sysConfig.action}")
    public String action;
	
	public DetectAuthRespBean faceProcess(TempUser tempUser){
		DetectAuthRespBean respBean = new DetectAuthRespBean();
		try {
		Credential cred = new Credential(secretId, secretKey);
		HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(url);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);            
        
        FaceidClient client = new FaceidClient(cred, region, clientProfile);
        
       	//请求参数
        DetectAuthReqBean reqBean = new DetectAuthReqBean();
        reqBean.setAction(action);
        reqBean.setVersion(version);
        reqBean.setRegion(region);
        reqBean.setRuleId(ruleId);
        reqBean.setImageBase64(tempUser.getOriginal_path());
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(reqBean);
        log.info(mapJakcson);
        DetectAuthRequest req = DetectAuthRequest.fromJsonString(mapJakcson, DetectAuthRequest.class);
        
        DetectAuthResponse resp = client.DetectAuth(req);
        
        respBean.setRespCode("sucess");
        respBean.setBizToken(resp.getBizToken());
        respBean.setUrl(resp.getUrl());
        DetectAuth da = new DetectAuth();
        da.setBizToken(resp.getBizToken());
        da.setUrl(resp.getUrl());
        da.setCreate_time(new Date());
        da.setRequest_id(resp.getRequestId());
        da.setUser_idcard(tempUser.getUser_idcard());
        detectAuthService.insertDA(da);
		} catch (Exception e) {
			log.error("请求实名核身接口失败",e);
			respBean.setRespCode("error");
			return respBean;
		}
        
		return respBean;
	}
	
	public void notifyProcess(String BizToken){
		   try{

			   Credential cred = new Credential(secretId, secretKey);
	            
	            HttpProfile httpProfile = new HttpProfile();
	            httpProfile.setEndpoint(url);

	            ClientProfile clientProfile = new ClientProfile();
	            clientProfile.setHttpProfile(httpProfile);            
	            
	            FaceidClient client = new FaceidClient(cred, region, clientProfile);
	        	//请求参数
		        GetDetectInfoReq reqBean = new GetDetectInfoReq();
		        reqBean.setAction(action);
		        reqBean.setVersion(version);
		        reqBean.setRegion(region);
		        reqBean.setRuleId(ruleId);
		        reqBean.setBizToken(BizToken);
		        ObjectMapper mapper = new ObjectMapper();
		        String mapJakcson = mapper.writeValueAsString(reqBean);
	            GetDetectInfoRequest req = GetDetectInfoRequest.fromJsonString(mapJakcson, GetDetectInfoRequest.class);
	            
	            GetDetectInfoResponse resp = client.GetDetectInfo(req);
	            String respStr = resp.getDetectInfo();
	            
	            JsonParser jp = new JsonParser();
	    		//将json字符串转化成json对象
	            JsonObject jo = jp.parse(resp.getDetectInfo()).getAsJsonObject();
	            String status = jo.get("Text").getAsJsonObject().get("ErrCode").toString();
	            String img =jo.get("BestFrame").getAsJsonObject().get("BestFrame").toString();
	            String audio = jo.get("VideoData").getAsJsonObject().get("LivenessVideo").toString();
	        } catch (Exception e) {
	                System.out.println(e.toString());
	        }
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		try {
			Credential cred = new Credential("AKIDd6MM9hgkESSEmptIjvsa4MPgjY8TihbI", "d3FDwQgjY8VeYDdpmSwCE2bv2vZx61iA");
//			Credential cred = new Credential("", "d3FDwQgjY8VeYDdpmSwCE2bv2vZx61iA");
			HttpProfile httpProfile = new HttpProfile();
	        httpProfile.setEndpoint("faceid.tencentcloudapi.com");
	        ClientProfile clientProfile = new ClientProfile();
	        clientProfile.setHttpProfile(httpProfile);            
	        
	        FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
	        
	       	//请求参数
	        GetDetectInfoReq reqBean = new GetDetectInfoReq();
	        reqBean.setAction("GetDetectInfo");
	        reqBean.setVersion("2018-03-01");
	        reqBean.setRegion("ap-beijing");
	        reqBean.setRuleId("1");
	        reqBean.setInfoType("134");
	        reqBean.setBizToken("119FB220-C657-4EB4-86F3-6E6AF5FB004F");
	        ObjectMapper mapper = new ObjectMapper();
	        String mapJakcson = mapper.writeValueAsString(reqBean);
	        GetDetectInfoRequest req = GetDetectInfoRequest.fromJsonString(mapJakcson, GetDetectInfoRequest.class);
            
            GetDetectInfoResponse resp = client.GetDetectInfo(req);
            
//            System.out.println(resp.getDetectInfo());
            JsonParser jp = new JsonParser();
    		//将json字符串转化成json对象
                JsonObject jo = jp.parse(resp.getDetectInfo()).getAsJsonObject();
                System.out.println("结果状态===="+jo.get("Text").getAsJsonObject().get("ErrCode"));
                System.out.println("最佳照片===="+jo.get("BestFrame").getAsJsonObject().get("BestFrame"));
                System.out.println("返回视频===="+jo.get("VideoData").getAsJsonObject().get("LivenessVideo"));
//	        System.out.println(DetectAuthRequest.toJsonString(resp));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
