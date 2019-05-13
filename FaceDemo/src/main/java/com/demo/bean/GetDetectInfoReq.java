package com.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月13日 下午4:46:42
 * @ClassName 类名称
 * @Description 类描述
 */
public class GetDetectInfoReq {
	@JsonProperty
	private String Action;
	@JsonProperty
	private String Version;
	@JsonProperty
	private String Region;
	@JsonProperty
	private String RuleId;
	@JsonProperty
	private String BizToken;
	@JsonProperty
	private String InfoType;
	public String getAction() {
		return Action;
	}
	@JsonIgnore
	public void setAction(String action) {
		Action = action;
	}
	@JsonIgnore
	public String getVersion() {
		return Version;
	}
	@JsonIgnore
	public void setVersion(String version) {
		Version = version;
	}
	@JsonIgnore
	public String getRegion() {
		return Region;
	}
	@JsonIgnore
	public void setRegion(String region) {
		Region = region;
	}
	@JsonIgnore
	public String getRuleId() {
		return RuleId;
	}
	@JsonIgnore
	public void setRuleId(String ruleId) {
		RuleId = ruleId;
	}
	@JsonIgnore
	public String getBizToken() {
		return BizToken;
	}
	@JsonIgnore
	public void setBizToken(String bizToken) {
		BizToken = bizToken;
	}
	@JsonIgnore
	public String getInfoType() {
		return InfoType;
	}
	@JsonIgnore
	public void setInfoType(String infoType) {
		InfoType = infoType;
	}
	
	
}
