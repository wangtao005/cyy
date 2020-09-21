package org.mics.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cyy.plugin.sms.linkai")
public class LinkaiSmsProperties {
	
	/**
	 * URL
	 */
	private String lkUrl;
	
	/**
	 * 账号
	 */
	private String corpId;
	
	/**
	 * 密码
	 */
	private String passWd;

	public String getLkUrl() {
		return lkUrl;
	}

	public void setLkUrl(String lkUrl) {
		this.lkUrl = lkUrl;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getPassWd() {
		return passWd;
	}

	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}
	
	
	
	
}
