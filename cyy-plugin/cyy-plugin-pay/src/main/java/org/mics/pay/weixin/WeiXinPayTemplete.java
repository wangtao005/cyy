package org.mics.pay.weixin;


import static org.mics.pay.weixin.constant.WXPayConstants.USER_AGENT;

import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.mics.lang.calculate.UUIDUtil;
import org.mics.lang.file.XmlUtils;
import org.mics.pay.weixin.config.WXPayProperties;
import org.mics.pay.weixin.exception.WeiXinPayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信支付
 * @author mics
 * @date 2020年8月24日
 * @version  1.0
 */
public class WeiXinPayTemplete {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinPayTemplete.class);
	/**
	 * 微信支付配置
	 */
	private WXPayProperties wxPayProperties;
	
  
	public WeiXinPayTemplete(WXPayProperties config){
        this.wxPayProperties = config;
    }
	
    /**
     * 请求，只请求一次，不做重试
     * @param domain
     * @param urlSuffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert 是否使用证书，针对退款、撤销等操作
     * @return
     * @throws Exception
     */
    private String requestOnce(final String domain, String urlSuffix, String data, boolean useCert) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = wxPayProperties.getMchId().toCharArray();
            InputStream certStream = wxPayProperties.getCertStream();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            
            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        String url = "https://" + domain + urlSuffix;
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(wxPayProperties.getHttpConnectTimeoutMs()).setConnectTimeout(wxPayProperties.getHttpReadTimeoutMs()).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT + " " + wxPayProperties.getMchId());
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }


    private String request(String urlSuffix,  String data, boolean useCert, boolean autoReport) throws Exception {
        Exception exception = null;
        long elapsedTimeMillis = 0;
        long startTimestampMs = getCurrentTimestampMs();
        boolean firstHasDnsErr = false;
        boolean firstHasConnectTimeout = false;
        boolean firstHasReadTimeout = false;
        if(wxPayProperties.getWxPayDomain() == null){
            throw new Exception("WXPayConfig.getWXPayDomain().getDomain() is empty or null");
        }
        try {
            String result = requestOnce(wxPayProperties.getWxPayDomain().getDomain(), urlSuffix,data,  useCert);
            elapsedTimeMillis = getCurrentTimestampMs()-startTimestampMs;
           // config.getIwxPayDomain().report(config.getWxPayDomain().domain, elapsedTimeMillis, null);
            WXPayReport.getInstance(wxPayProperties).report(
                    UUIDUtil.randomUUID(),
                    elapsedTimeMillis,
                    wxPayProperties.getWxPayDomain().domain,
                    wxPayProperties.getWxPayDomain().primaryDomain,
                    wxPayProperties.getHttpConnectTimeoutMs(),
                    wxPayProperties.getHttpReadTimeoutMs(),
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
            return result;
        }
        catch (UnknownHostException ex) {  // dns 解析错误，或域名不存在
            exception = ex;
            firstHasDnsErr = true;
            elapsedTimeMillis = getCurrentTimestampMs()-startTimestampMs;
            LOGGER.warn("UnknownHostException for config.getWxPayDomain() {}", wxPayProperties.getWxPayDomain());
            WXPayReport.getInstance(wxPayProperties).report(
            		UUIDUtil.randomUUID(),
                    elapsedTimeMillis,
                    wxPayProperties.getWxPayDomain().domain,
                    wxPayProperties.getWxPayDomain().primaryDomain,
                    wxPayProperties.getHttpConnectTimeoutMs(),
                    wxPayProperties.getHttpReadTimeoutMs(),
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout
            );
        }
        catch (ConnectTimeoutException ex) {
            exception = ex;
            firstHasConnectTimeout = true;
            elapsedTimeMillis = getCurrentTimestampMs()-startTimestampMs;
            LOGGER.warn("connect timeout happened for config.getWxPayDomain() {}", wxPayProperties.getWxPayDomain());
            WXPayReport.getInstance(wxPayProperties).report(
            		UUIDUtil.randomUUID(),
                    elapsedTimeMillis,
                    wxPayProperties.getWxPayDomain().domain,
                    wxPayProperties.getWxPayDomain().primaryDomain,
                    wxPayProperties.getHttpConnectTimeoutMs(),
                    wxPayProperties.getHttpReadTimeoutMs(),
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout
            );
        }
        catch (SocketTimeoutException ex) {
            exception = ex;
            firstHasReadTimeout = true;
            elapsedTimeMillis = getCurrentTimestampMs()-startTimestampMs;
            LOGGER.warn("timeout happened for config.getWxPayDomain() {}", wxPayProperties.getWxPayDomain());
            WXPayReport.getInstance(wxPayProperties).report(
            		UUIDUtil.randomUUID(),
                    elapsedTimeMillis,
                    wxPayProperties.getWxPayDomain().domain,
                    wxPayProperties.getWxPayDomain().primaryDomain,
                    wxPayProperties.getHttpConnectTimeoutMs(),
                    wxPayProperties.getHttpReadTimeoutMs(),
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
        }
        catch (Exception ex) {
            exception = ex;
            elapsedTimeMillis = getCurrentTimestampMs()-startTimestampMs;
            WXPayReport.getInstance(wxPayProperties).report(
            		UUIDUtil.randomUUID(),
                    elapsedTimeMillis,
                    wxPayProperties.getWxPayDomain().domain,
                    wxPayProperties.getWxPayDomain().primaryDomain,
                    wxPayProperties.getHttpConnectTimeoutMs(),
                    wxPayProperties.getHttpReadTimeoutMs(),
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
        }
       // config.getWXPayDomain().report(config.getWxPayDomain().domain, elapsedTimeMillis, exception);
        throw exception;
    }


    /**
     * 可重试的，非双向认证的请求
     * @param urlSuffix 不带域名的url地址
     * @param uuid 
     * @param data
     * @return
     */
    public Map<String,String> requestWithoutCert(String urlSuffix,Map<String,String> dataMap, boolean autoReport) {
        try {
			String result =  this.request(urlSuffix, getData(dataMap),  false, autoReport);
			Map<String,String> resultMap = XmlUtils.xmlToMap(result);
			return resultMap;
		} catch (Exception e) {
			throw new WeiXinPayException("可重试的，非双向认证的请求失败",e);
		}
    }


    /**
     * 可重试的，双向认证的请求
     * @param urlSuffix 不带域名的url地址
     * @param uuid
     * @param data
     * @return
     */
    public Map<String,String> requestWithCert(String urlSuffix, Map<String,String> dataMap, boolean autoReport) {
    	try {
    		String result =  this.request(urlSuffix, getData(dataMap),  true, autoReport);
    		Map<String,String> resultMap = XmlUtils.xmlToMap(result);
			return resultMap;
	    } catch (Exception e) {
			throw new WeiXinPayException("可重试的，非双向认证的请求失败",e);
		}
    }

    
    private String getData( Map<String,String> dataMap) {
    	try {
    		dataMap.put("appid", wxPayProperties.getAppId());
    		dataMap.put("mch_id", wxPayProperties.getMchId());
    		dataMap.put("nonce_str", UUIDUtil.randomUUID());
			return SignUtil.generateSignedXml(dataMap, wxPayProperties.getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    private static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }
	    
}
