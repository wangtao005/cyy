package org.mics.email.controller;

import javax.annotation.Resource;

import org.mics.core.response.SaveOrUpdateResponse;
import org.mics.email.request.EmailVerificationCodeRequest;
import org.mics.email.service.EmailCodeService;
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
@RequestMapping(value = "/email")
@Api(tags = "邮件接口")
public class EmailController {
	@Resource
	private EmailCodeService  emailCodeService;
	
	/**
	 * 发送验证码
	 * @param verificationCodeRequest 验证码请求对象
	 * @return 返回保存操作响应对象
	 */
	@IgnoreToken
    @PostMapping("/sendCode")
	@ApiOperation(value = "发送验证码",notes = "发送验证码到手机")
    public SaveOrUpdateResponse sendCode(@Validated @RequestBody EmailVerificationCodeRequest emailVerificationCodeRequest) {
        String id = emailCodeService.sendCode(emailVerificationCodeRequest);
        return new SaveOrUpdateResponse(id);
    }
}
