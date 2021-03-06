package com.demo.core;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.bean.DetectAuthReqBean;
import com.demo.bean.DetectAuthRespBean;
import com.demo.bean.GetDetectInfoReq;
import com.demo.model.DetectAuth;
import com.demo.model.SysConfig;
import com.demo.model.User;
import com.demo.model.VideoIdent;
import com.demo.service.DetectAuthService;
import com.demo.service.VideoIdentService;
import com.demo.util.Base64Utils;
import com.demo.util.DateFormatUtil;
import com.demo.util.cache.SysCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tencentcloudapi.common.Credential;
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
	public static Logger log = Logger.getLogger(TxFaceService.class);
	@Autowired
	public DetectAuthService detectAuthService;

	@Autowired
	private VideoIdentService videoIdentService;

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

	/**
	 * 版本号
	 */
	@Value("#{sysConfig.filePuth}")
	public String filePuth;

	/**
	 * 回调地址
	 */
	@Value("#{sysConfig.redirectUrl}")
	public String redirectUrl;
	/**
	 * 照片路径
	 */
	@Value("#{sysConfig.imgPuth}")
	public String imgPuth;

	public DetectAuthRespBean faceProcess(User user) throws Exception {
		DetectAuthRespBean respBean = new DetectAuthRespBean();
		SysConfig sys = SysCache.getSysConfig();
		Credential cred = new Credential(sys.getSecretId(), sys.getSecretKey());
		HttpProfile httpProfile = new HttpProfile();
		httpProfile.setEndpoint(sys.getFaceUrl());
		ClientProfile clientProfile = new ClientProfile();
		clientProfile.setHttpProfile(httpProfile);

		FaceidClient client = new FaceidClient(cred, sys.getRegion(),
				clientProfile);

		// 请求参数
		DetectAuthReqBean reqBean = new DetectAuthReqBean();
		reqBean.setAction(sys.getAction());
		reqBean.setVersion(sys.getVersion());
		reqBean.setRegion(sys.getRegion());
		reqBean.setRuleId(sys.getRuleId());
		reqBean.setImageBase64(Base64Utils.getImageStr(
				imgPuth + user.getImg_url()).replace("\r\n", ""));
		reqBean.setName(user.getUser_name());
		reqBean.setIdCard(user.getUser_idcard());
		reqBean.setRedirectUrl(redirectUrl);
		ObjectMapper mapper = new ObjectMapper();
		String mapJakcson = mapper.writeValueAsString(reqBean);
		DetectAuthRequest req = DetectAuthRequest.fromJsonString(mapJakcson,
				DetectAuthRequest.class);

		DetectAuthResponse resp = client.DetectAuth(req);

		respBean.setRespCode("sucess");
		respBean.setBizToken(resp.getBizToken());
		respBean.setUrl(resp.getUrl());
		DetectAuth da = new DetectAuth();
		da.setBizToken(resp.getBizToken());
		da.setUrl(resp.getUrl());
		da.setCreate_time(new Date());
		da.setRequest_id(resp.getRequestId());
		da.setUser_idcard(user.getUser_idcard());
		detectAuthService.insertDA(da);

		return respBean;
	}

	public String notifyProcess(String BizToken) {
		String rs = "";
		try {
			String userId = detectAuthService.findUserId(BizToken);

			List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(
					Integer.valueOf(userId), DateFormatUtil.getCurrentDT()
							.substring(0, 4));
			if (list.size() > 0) {
				return String.valueOf(list.get(0).getVideo_status());
			}
			SysConfig sys = SysCache.getSysConfig();
			Credential cred = new Credential(sys.getSecretId(),
					sys.getSecretKey());

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint(sys.getFaceUrl());

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			FaceidClient client = new FaceidClient(cred, sys.getRegion(),
					clientProfile);
			// 请求参数
			GetDetectInfoReq reqBean = new GetDetectInfoReq();
			reqBean.setAction("GetDetectInfo");
			reqBean.setVersion(version);
			reqBean.setRegion(sys.getRegion());
			reqBean.setRuleId(sys.getRuleId());
			reqBean.setBizToken(BizToken);
			ObjectMapper mapper = new ObjectMapper();
			String mapJakcson = mapper.writeValueAsString(reqBean);
			GetDetectInfoRequest req = GetDetectInfoRequest.fromJsonString(
					mapJakcson, GetDetectInfoRequest.class);

			GetDetectInfoResponse resp = client.GetDetectInfo(req);
			JsonParser jp = new JsonParser();
			// 将json字符串转化成json对象
			JsonObject jo = jp.parse(resp.getDetectInfo()).getAsJsonObject();
			String status = jo.get("Text").getAsJsonObject().get("ErrCode")
					.toString();
			String msg = jo.get("Text").getAsJsonObject().get("ErrMsg")
					.toString();
			String img = jo.get("BestFrame").getAsJsonObject().get("BestFrame")
					.toString();
			String audio = jo.get("VideoData").getAsJsonObject()
					.get("LivenessVideo").toString();
			String idCard = jo.get("Text").getAsJsonObject().get("IdCard")
					.toString();
			String imgName = idCard.replace("\"", "") + ".jpg";
			String audioName = idCard.replace("\"", "") + ".mp4";
			String basePath = DateFormatUtil.getCurrentDT().substring(0, 4)
					+ "//" + DateFormatUtil.getCurrentDT().substring(4, 6);

			Base64Utils.base64ToFile(img, filePuth + basePath + "//", imgName);
			Base64Utils.base64ToFile(audio, filePuth + basePath + "//",
					audioName);
			VideoIdent videoIdent = new VideoIdent();
			videoIdent.setUser_id(Integer.valueOf(userId));
			videoIdent.setImg_url("/upload/" + basePath + "/" + imgName);
			videoIdent.setVideo_url("/upload/" + basePath + "/" + audioName);
			videoIdent.setAuditors_reason(msg);
			int video_status;
			if ("0".equals(status)) {
				video_status = 2;
			} else {
				video_status = 3;
			}
			rs = String.valueOf(video_status);
			videoIdent.setAdd_time(new Date());
			videoIdent.setVideo_status(video_status);
			videoIdent.setYear(DateFormatUtil.getCurrentDT().substring(0, 4));
			List<VideoIdent> listVideoIdent = videoIdentService.findVideoListByIdAndTime(
					Integer.valueOf(userId), DateFormatUtil.getCurrentDT()
							.substring(0, 4));
			if(listVideoIdent.size()==0){
				videoIdentService.insertVL(videoIdent);
			}
			

		} catch (Exception e) {
			log.error(e);
		}

		return rs;
	}

	public static void main(String[] args) throws JsonProcessingException {
		try {
			Credential cred = new Credential(
					"AKIDd6MM9hgkESSEmptIjvsa4MPgjY8TihbI",
					"d3FDwQgjY8VeYDdpmSwCE2bv2vZx61iA");
			// Credential cred = new Credential("",
			// "d3FDwQgjY8VeYDdpmSwCE2bv2vZx61iA");
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("faceid.tencentcloudapi.com");
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			FaceidClient client = new FaceidClient(cred, "ap-guangzhou",
					clientProfile);

			// 请求参数
			DetectAuthReqBean reqBean = new DetectAuthReqBean();
			reqBean.setAction("DetectAuth");
			reqBean.setVersion("2018-03-01");
			reqBean.setRegion("ap-guangzhou");
			reqBean.setIdCard("340826198806280833");
			reqBean.setName("孙乐焜");
			reqBean.setRuleId("1");
			String img = Base64Utils.imageToBase64Str("E://1//123.png")
					.replace("\r\n", "");
			// System.out.println(img);
			// Base64Utils.base64ToFile(img,"E://","111000000000.jpg");
			reqBean.setImageBase64(img);
			ObjectMapper mapper = new ObjectMapper();
			String mapJakcson = mapper.writeValueAsString(reqBean);
			System.out.println(mapJakcson);
			DetectAuthRequest req = DetectAuthRequest.fromJsonString(
					mapJakcson.toString(), DetectAuthRequest.class);

			DetectAuthResponse resp = client.DetectAuth(req);
			System.out.println(resp.getUrl());
			System.out.println(resp.getBizToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
