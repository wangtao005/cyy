package org.mics.pay.weixin.config;

import org.mics.pay.weixin.WeiXinPayTemplete;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置
 * @author mics
 * @date 2020年7月9日
 * @version  1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "cyy.plugin.pay.weixin",name = "app_id")
@EnableConfigurationProperties(value = WXPayProperties.class)
public class WXPayAutoConfig {
	
	/**
	 * 初始化微信支付帮助类
	 * @param wxPayProperties
	 */
	@Bean
	public WeiXinPayTemplete  initWeiXinPayHelper(WXPayProperties wxPayProperties) {
		WeiXinPayTemplete weiXinPayHelper = new WeiXinPayTemplete(wxPayProperties);
		return weiXinPayHelper;
	}
}
