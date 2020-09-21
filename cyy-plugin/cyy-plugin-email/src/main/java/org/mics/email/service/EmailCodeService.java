package org.mics.email.service;

import org.mics.email.request.EmailVerificationCodeCheckRequest;
import org.mics.email.request.EmailVerificationCodeRequest;

/**
 * 邮件服务
 * @author mics
 * @date 2020年6月29日
 * @version  1.0
 */
public interface EmailCodeService {

	/**
	 * 发送验证码
	 * @param emailVerificationCodeRequest 邮箱验证信息
     * @return 验证码记录id
	 */
	String sendCode(EmailVerificationCodeRequest emailVerificationCodeRequest);

	/**
	 * 校验验证码
	 * @param emailVerificationCodeCheckRequest 邮箱验证信息
	 */
	void checkCode(EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest);

}
