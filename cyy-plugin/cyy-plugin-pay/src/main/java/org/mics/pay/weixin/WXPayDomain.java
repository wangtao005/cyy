package org.mics.pay.weixin;

/**
 * 域名管理，实现主备域名自动切换
 */
public class  WXPayDomain {
	 public String domain;       //域名
     public boolean primaryDomain;     //该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名

     
     public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isPrimaryDomain() {
		return primaryDomain;
	}

	public void setPrimaryDomain(boolean primaryDomain) {
		this.primaryDomain = primaryDomain;
	}

	
    @Override
	public String toString() {
		return "IWXPayDomain [domain=" + domain + ", primaryDomain=" + primaryDomain + "]";
	}

	/**
     * 上报域名网络状况
     * @param domain 域名。 比如：api.mch.weixin.qq.com
     * @param elapsedTimeMillis 耗时
     * @param ex 网络请求中出现的异常。
     *           null表示没有异常
     *           ConnectTimeoutException，表示建立网络连接异常
     *           UnknownHostException， 表示dns解析异常
     */
    void report(final String domain, long elapsedTimeMillis, final Exception ex) {
	}



}