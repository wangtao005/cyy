package org.mics.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cyy.plugin.file.oss")
public class OSSProperties {
	
	/**
	 * 域名
	 */
	private String host;
	/**
	 * 访问key
	 */
	private String accessKeyId;
	/**
	 * 访问密码
	 */
	private String accessKeySecret;
	/**
	 * 存储空间名称
	 */
	private String bucketName;
	/**
	 * 外部访问连接 
	 */
	private String outSite;
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getOutSite() {
		return outSite;
	}
	public void setOutSite(String outSite) {
		this.outSite = outSite;
	}
	
	
	
	
}
