package com.demo.bean;
/**
 * @author lekun.sun
 * @version 创建时间：2019年5月8日 下午4:36:19
 * @ClassName 类名称
 * @Description 类描述：实名核身鉴权请求接口bean
 */
public class DetectAuthReqBean {
	
	private String Action;
	
	private String Version;
	
	private String Region;
	
	private String RuleId;
	
	private String TerminalType;
	
	private String IdCard;
	
	private String Name;
	
	private String RedirectUrl;
	
	private String Extra;
	
	private String ImageBase64;

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getRuleId() {
		return RuleId;
	}

	public void setRuleId(String ruleId) {
		RuleId = ruleId;
	}

	public String getTerminalType() {
		return TerminalType;
	}

	public void setTerminalType(String terminalType) {
		TerminalType = terminalType;
	}

	public String getIdCard() {
		return IdCard;
	}

	public void setIdCard(String idCard) {
		IdCard = idCard;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRedirectUrl() {
		return RedirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		RedirectUrl = redirectUrl;
	}

	public String getExtra() {
		return Extra;
	}

	public void setExtra(String extra) {
		Extra = extra;
	}

	public String getImageBase64() {
		return ImageBase64;
	}

	public void setImageBase64(String imageBase64) {
		ImageBase64 = imageBase64;
	}
	
	
}
