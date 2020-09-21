package org.mics.captcha.service;

import java.util.Map;

/**
 * 验证码
 * @author mics
 * @date 2020年6月6日
 * @version  1.0
 */
public interface CaptchaService {
	
	/**
	 * 获取验证图片
	 * @return
	 */
    public  Map<String,Object> getImageVerifyCode();
}
