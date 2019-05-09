package com.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月8日 下午4:36:19
 * @ClassName 类名称
 * @Description 类描述：实名核身鉴权请求接口bean
 */
public class DetectAuthReqBean {
	
	@JsonProperty
	private String Action;
	@JsonProperty
	private String Version;
	@JsonProperty
	private String Region;
	@JsonProperty
	private String RuleId;
	@JsonProperty
	private String TerminalType;
	@JsonProperty
	private String IdCard;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String RedirectUrl;
	@JsonProperty
	private String Extra;
	@JsonProperty
	private String ImageBase64;
	
	@JsonIgnore
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
	public String getTerminalType() {
		return TerminalType;
	}
	@JsonIgnore
	public void setTerminalType(String terminalType) {
		TerminalType = terminalType;
	}
	@JsonIgnore
	public String getIdCard() {
		return IdCard;
	}
	@JsonIgnore
	public void setIdCard(String idCard) {
		IdCard = idCard;
	}
	@JsonIgnore
	public String getName() {
		return Name;
	}
	@JsonIgnore
	public void setName(String name) {
		Name = name;
	}
	@JsonIgnore
	public String getRedirectUrl() {
		return RedirectUrl;
	}
	@JsonIgnore
	public void setRedirectUrl(String redirectUrl) {
		RedirectUrl = redirectUrl;
	}
	@JsonIgnore
	public String getExtra() {
		return Extra;
	}
	@JsonIgnore
	public void setExtra(String extra) {
		Extra = extra;
	}
	@JsonIgnore
	public String getImageBase64() {
		return ImageBase64;
	}
	@JsonIgnore
	public void setImageBase64(String imageBase64) {
		ImageBase64 = imageBase64;
	}

	@Override
	public String toString() {
		return "DetectAuthReqBean [Action=" + Action + ", Version=" + Version
				+ ", Region=" + Region + ", RuleId=" + RuleId
				+ ", TerminalType=" + TerminalType + ", IdCard=" + IdCard
				+ ", Name=" + Name + ", RedirectUrl=" + RedirectUrl
				+ ", Extra=" + Extra + ", ImageBase64=" + ImageBase64 + "]";
	}
	
	
}
