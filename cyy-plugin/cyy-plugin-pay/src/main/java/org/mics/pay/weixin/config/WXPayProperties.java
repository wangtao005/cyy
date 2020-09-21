package org.mics.pay.weixin.config;

import java.io.InputStream;

import org.mics.pay.weixin.WXPayDomain;
import org.springframework.boot.context.properties.ConfigurationProperties;


 
@ConfigurationProperties(prefix = "cyy.plugin.pay.weixin")
public  class WXPayProperties {
	
	/**
	 * 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String appId;
	
	/**
	 * 商户号
	 */
	private String mchId;
	
	/**
	 * 证书
	 */
	private InputStream certStream;
	
	/**
	 * 商户号密钥
	 */
	private String key;
	
	/**
	 * HTTP(S) 连接超时时间，单位毫秒
	 */
	private int httpConnectTimeoutMs;
	
	/**
	 * HTTP(S) 读数据超时时间，单位毫秒
	 */
	private int httpReadTimeoutMs;
	
	/**
	 * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
	 */
	private boolean shouldAutoReport;
	
	/**
	 * 进行健康上报的线程的数量
	 */
	private int reportWorkerNum=6;
	
    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     */
	private int reportQueueMaxSize=10000;
	
	/**
	 * 批量上报，一次最多上报多个数据
	 */
	private int reportBatchSize=10;
	
	
	private WXPayDomain wxPayDomain;
	
	private String notifyUrl;

	
	

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public WXPayDomain getWxPayDomain() {
		return wxPayDomain;
	}

	public void setWxPayDomain(WXPayDomain wxPayDomain) {
		this.wxPayDomain = wxPayDomain;
	}

	public InputStream getCertStream() {
		return certStream;
	}

	public void setCertStream(InputStream certStream) {
		this.certStream = certStream;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getHttpConnectTimeoutMs() {
		return httpConnectTimeoutMs;
	}

	public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
		this.httpConnectTimeoutMs = httpConnectTimeoutMs;
	}

	public int getHttpReadTimeoutMs() {
		return httpReadTimeoutMs;
	}

	public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
		this.httpReadTimeoutMs = httpReadTimeoutMs;
	}

	public boolean isShouldAutoReport() {
		return shouldAutoReport;
	}

	public void setShouldAutoReport(boolean shouldAutoReport) {
		this.shouldAutoReport = shouldAutoReport;
	}

	public int getReportWorkerNum() {
		return reportWorkerNum;
	}

	public void setReportWorkerNum(int reportWorkerNum) {
		this.reportWorkerNum = reportWorkerNum;
	}

	public int getReportQueueMaxSize() {
		return reportQueueMaxSize;
	}

	public void setReportQueueMaxSize(int reportQueueMaxSize) {
		this.reportQueueMaxSize = reportQueueMaxSize;
	}

	public int getReportBatchSize() {
		return reportBatchSize;
	}

	public void setReportBatchSize(int reportBatchSize) {
		this.reportBatchSize = reportBatchSize;
	}

	




}
