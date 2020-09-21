package org.mics.sms.service;

import org.mics.sms.request.SmsVerificationCodeCheckRequest;
import org.mics.sms.request.SmsVerificationCodeRequest;

/**
 * 发送短信验证码
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public interface SmsCodeService {

	 /**
     * 发送验证码
     * @param smsSendRequest smsSendRequest
     * @return 验证码记录id
     */
    String sendCode(SmsVerificationCodeRequest verificationCodeRequest);

    /**
     * 校验验证码
     * @param checkVerificationCodeRequest checkVerificationCodeRequest
     */
    void checkCode(SmsVerificationCodeCheckRequest checkVerificationCodeRequest);

    
}
