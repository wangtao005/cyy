package org.mics.sms.controller;

import javax.annotation.Resource;

import org.mics.core.response.SaveOrUpdateResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.sms.request.SmsVerificationCodeCheckRequest;
import org.mics.sms.request.SmsVerificationCodeRequest;
import org.mics.sms.service.SmsCodeService;
import org.mics.token.annotation.IgnoreToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 短信验证码
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@RestController
@RequestMapping(value = "/sms")
@Api(tags = "短信接口")
public class SmsCodeController{
	@Resource
	private SmsCodeService smsCodeService;
	
	/**
	 * 发送验证码
	 * @param verificationCodeRequest 验证码请求对象
	 * @return 返回保存操作响应对象
	 */
	@IgnoreToken
    @PostMapping("/sendCode")
	@ApiOperation(value = "发送验证码",notes = "发送验证码到手机")
    public SaveOrUpdateResponse sendCode(@Validated @RequestBody SmsVerificationCodeRequest verificationCodeRequest) {
        String id = smsCodeService.sendCode(verificationCodeRequest);
        return new SaveOrUpdateResponse(id);
    }
	
	
	/**
	 * 校验验证码
	 * @param checkVerificationCodeRequest  验证码请求对象
	 * @return  返回保存操作响应对象
	 */
	@IgnoreToken
    @PostMapping("/checkCode")
    public SimpleResponse checkVerificationCodeRequest(@Validated @RequestBody SmsVerificationCodeCheckRequest checkVerificationCodeRequest) {
		smsCodeService.checkCode(checkVerificationCodeRequest);
        return new SimpleResponse();
    }
	 
	
}
