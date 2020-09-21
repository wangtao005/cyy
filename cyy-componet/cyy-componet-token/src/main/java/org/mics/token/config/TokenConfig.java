package org.mics.token.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * token配置文件
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
@ConfigurationProperties(prefix = "cyy.componet.token.jwt")
public class TokenConfig {
    /**
     * 私钥
     */
    private String secret;

    /**
     * iss
     */
    private String iss;

    /**
     * 开启token加密
     */
    private Boolean wrapperSwitch;

    /**
     * 加密/解密时的key
     */
    private String wrapperKey;

    /**
     * 开始请求时间校验
     */
    private Boolean verifyTime;

    /**
     * 请求有效时间
     */
    private Integer requestDurationMiles;

    /**
     * 请求有效时间
     */
    private Boolean duplicate;

    /**
     * 请求有效时间
     */
    private String defaultToken;

    /**
     * token缓存时间
     */
    private Integer tokenCacheTime;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public Boolean getWrapperSwitch() {
		return wrapperSwitch;
	}

	public void setWrapperSwitch(Boolean wrapperSwitch) {
		this.wrapperSwitch = wrapperSwitch;
	}

	public String getWrapperKey() {
		return wrapperKey;
	}

	public void setWrapperKey(String wrapperKey) {
		this.wrapperKey = wrapperKey;
	}

	public Boolean getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Boolean verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Integer getRequestDurationMiles() {
		return requestDurationMiles;
	}

	public void setRequestDurationMiles(Integer requestDurationMiles) {
		this.requestDurationMiles = requestDurationMiles;
	}

	public Boolean getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}

	public String getDefaultToken() {
		return defaultToken;
	}

	public void setDefaultToken(String defaultToken) {
		this.defaultToken = defaultToken;
	}

	public Integer getTokenCacheTime() {
		return tokenCacheTime;
	}

	public void setTokenCacheTime(Integer tokenCacheTime) {
		this.tokenCacheTime = tokenCacheTime;
	}
    
    

}
