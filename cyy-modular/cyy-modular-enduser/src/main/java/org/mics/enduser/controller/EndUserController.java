package org.mics.enduser.controller;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.mics.core.response.OneDataResponse;
import org.mics.core.response.SaveOrUpdateResponse;
import org.mics.core.response.SimpleResponse;
import org.mics.email.enums.EmailEnums;
import org.mics.email.request.EmailVerificationCodeRequest;
import org.mics.email.service.EmailCodeService;
import org.mics.enduser.request.ForgetRequest;
import org.mics.enduser.request.LoginRequest;
import org.mics.enduser.request.RegisterRequest;
import org.mics.enduser.request.SLoginRequest;
import org.mics.enduser.request.VerificationCodeRequest;
import org.mics.enduser.service.EndUserService;
import org.mics.enduser.vo.EndUserTokenVO;
import org.mics.enduser.vo.EndUserVO;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.sms.enums.SmsEnums;
import org.mics.sms.request.SmsVerificationCodeRequest;
import org.mics.sms.service.SmsCodeService;
import org.mics.token.annotation.IgnoreToken;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 终端用户
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@RestController
@RequestMapping("/endUser")
@Api(tags = "[客户端]-用户")
public class EndUserController {
	@Resource
	private EndUserService endUserService;
	@Resource
	private EmailCodeService emailCodeService;
	@Resource
	private SmsCodeService smsCodeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EndUserController.class);
	
    @ApiOperation(value = "发送注册验证码")
	@IgnoreToken
	@PostMapping("/sendCode")
	public SaveOrUpdateResponse sendCode(@Validated @RequestBody VerificationCodeRequest verificationCodeRequest) {
		String regexp = "^1(3|4|5|6|7|8|9)\\d{9}$";
		if(Pattern.matches(regexp,verificationCodeRequest.getAccount())){
			SmsVerificationCodeRequest smsVerificationCodeRequest = BeanConvertUtil.convert(verificationCodeRequest, SmsVerificationCodeRequest.class);
			smsVerificationCodeRequest.setType(SmsEnums.CodeType.REGISTERED);
			String id = smsCodeService.sendCode(smsVerificationCodeRequest);
	        return new SaveOrUpdateResponse(id);
		}else{
			EmailVerificationCodeRequest emailVerificationCodeRequest = BeanConvertUtil.convert(verificationCodeRequest, EmailVerificationCodeRequest.class);
			emailVerificationCodeRequest.setType(EmailEnums.CodeType.REGISTERED);
			String id = emailCodeService.sendCode(emailVerificationCodeRequest);
	        return new SaveOrUpdateResponse(id);
		}
	}
	
    @ApiOperation(value = "用户注册")
	@IgnoreToken
	@PostMapping("/register")
	public SimpleResponse register(@Validated @RequestBody  RegisterRequest registerRequest) {
		LOGGER.info("用户注册[account:{}]", registerRequest.getAccount());
        endUserService.register(registerRequest);
        LOGGER.debug("用户注册成功");
        return new SimpleResponse();
	}
	
    @ApiOperation("账号+密码登录")
	@IgnoreToken
    @PostMapping("/login")
	public SimpleResponse login(@Validated @RequestBody LoginRequest loginRequest) {
		LOGGER.info("账号密码登录[account:{}]", loginRequest.getAccount());
		EndUserVO endUserVO = endUserService.login(loginRequest);
		LOGGER.debug("账号密码登录成功");
		return new OneDataResponse<EndUserVO>(endUserVO);
	}
    
    
    
    @ApiOperation("账号+验证码")
	@IgnoreToken
    @PostMapping("/slogin")
	public SimpleResponse slogin(@Validated @RequestBody SLoginRequest sLoginRequest) {
		LOGGER.info("账号验证码登录[account:{}]", sLoginRequest.getAccount());
		EndUserVO endUserVO = endUserService.slogin(sLoginRequest);
		LOGGER.debug("账号验证码登录成功");
		return new OneDataResponse<EndUserVO>(endUserVO);
	}
	
	
	@ApiOperation("获取用户信息")
    @GetMapping("/info")
	@Cacheable(cacheNames = "userCache",key = "#currentUser.id")
    public SimpleResponse info(@LoginUser CurrentUser currentUser) {
        EndUserTokenVO vo = endUserService.info(currentUser.getId());
        LOGGER.debug("根据token获取用户信息成功");
        return new OneDataResponse<EndUserTokenVO>(vo);
    }
	
	
    @ApiOperation("忘记密码")
	@IgnoreToken
    @PostMapping("/forget")
    public SimpleResponse forget(@Validated @RequestBody ForgetRequest forgetRequest) {
		LOGGER.info("重置密码[account:{}]", forgetRequest.getAccount());
        endUserService.forget(forgetRequest);
        LOGGER.debug("重置密码成功");
        return new SimpleResponse();
    }
	
	
}
