package org.mics.email.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.mics.email.config.EmailProperties;
import org.mics.email.entity.EmailCodeDO;
import org.mics.email.enums.EmailEnums;
import org.mics.email.exception.EmailException;
import org.mics.email.repository.EmailCodeRepository;
import org.mics.email.request.EmailVerificationCodeCheckRequest;
import org.mics.email.request.EmailVerificationCodeRequest;
import org.mics.email.service.EmailCodeService;
import org.mics.lang.calculate.RandomUtils;
import org.mics.lang.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件服务
 * @author mics
 * @date 2020年6月29日
 * @version  1.0
 */
@Service
public class EmailCodeServiceImpl implements EmailCodeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailCodeServiceImpl.class);
	@Resource
	private EmailCodeRepository emailCodeRepository;
	@Resource
	private JavaMailSender  javaMailSender;
	@Resource
	private EmailProperties emailProperties;
	@Resource
	private MailProperties mailProperties;
	
	 /**
     * 分钟
     */
    private static final Integer MINUTE = 60000;
	
	public EmailProperties getEmailProperties() {
		return emailProperties;
	}

	public void setEmailProperties(EmailProperties emailProperties) {
		this.emailProperties = emailProperties;
	}

	
	@Override
	@Transactional
	public String sendCode(EmailVerificationCodeRequest emailVerificationCodeRequest) {
		//校验请求
        checkRequestRule(emailVerificationCodeRequest);
        
        //获取6位随机数
        String code = RandomUtils.randomNumberString(emailProperties.getCodeLength());
        
        //发送到阿里云短信
        String content = "";
        if(emailVerificationCodeRequest.getType() == EmailEnums.CodeType.LOG_IN) {
        	content = "您的验证码为："+code+",如非本人操作请忽略！";
        }else if(emailVerificationCodeRequest.getType() == EmailEnums.CodeType.REGISTERED) {
        	content = "你的验证码为："+code+",如非本人操作请忽略！";
        }else if(emailVerificationCodeRequest.getType() == EmailEnums.CodeType.RETRIEVE_PASSWORD) {
        	content = "你的验证码为"+code+",如非本人操作请忽略！";
        }else if(emailVerificationCodeRequest.getType() == EmailEnums.CodeType.MODIFY_PASSWORD) {
        	content = "你的验证码为"+code+",如非本人操作请忽略！";
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(emailVerificationCodeRequest.getAccount());
        message.setSubject("验证码");
        message.setText(content);
        javaMailSender.send(message);
        
        //添加验证码邮件记录
        String id = saveEmailCode(emailVerificationCodeRequest, code);
        return id;
        
	}
	
	@Transactional
	private String saveEmailCode(EmailVerificationCodeRequest emailVerificationCodeRequest, String code) {
		 //记录验证码发送记录
        EmailCodeDO emailCodeDO = new EmailCodeDO();
        emailCodeDO.setEmail(emailVerificationCodeRequest.getAccount());
        emailCodeDO.setMessage(code);
        emailCodeDO.setStatus(EmailEnums.CodeStatus.NOT_VERIFIED);
        emailCodeDO.setType(emailVerificationCodeRequest.getType());
        emailCodeDO.setCode(code);
        EmailCodeDO result = emailCodeRepository.save(emailCodeDO);
        return result.getId();
	}

	/**
     * 检查参数
     * @param smsSendRequest  短信发送验证码请求
     * @param parameterConfig 参数
     */
    private void checkRequestRule(EmailVerificationCodeRequest emailVerificationCodeRequest) {
        //当天日期
        String createDate = DateUtils.getDateYMDS(new Date(), "yyyy-MM-dd");
        //验证手机号是否达到最大发送量
        List<EmailCodeDO> smsCodeDOS = emailCodeRepository.findByEmailAndCreateDateLike(emailVerificationCodeRequest.getAccount(), createDate);
        if (smsCodeDOS.size() >= emailProperties.getMaxCountPerEmail()) {
            throw new EmailException("此邮箱申请数量已达当日上限");
        }
        
		//根据手机号查找未删除的最后一条短信验证码记录
        EmailCodeDO smsCodeDO = emailCodeRepository.findTop1ByEmailOrderByCreateDateDesc(emailVerificationCodeRequest.getAccount()); 
        //如果不是当天第一条，就判断间隔 
        if (smsCodeDO != null && System.currentTimeMillis() - smsCodeDO.getCreateDate().getTime() < emailProperties.getMinDuration()) {
        	throw new EmailException("此邮箱申请间隔至少需要" +emailProperties.getMinDuration() / MINUTE + "分钟");
        }
		 
    }

	@Override
	public void checkCode(EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest) {
		LOGGER.debug("校验验证码-service-开始[checkVerificationCodeRequest:{}]", emailVerificationCodeCheckRequest);

        EmailCodeDO emailCodeDO = emailCodeRepository.getById(emailVerificationCodeCheckRequest.getId());

        //校验验证码
        verifyVerificationCode(emailCodeDO, emailVerificationCodeCheckRequest);

        //验证码状态(0:未校验;1:已校验)
        emailCodeDO.setStatus(EmailEnums.CodeStatus.VERIFIED);
        emailCodeRepository.save(emailCodeDO);

        LOGGER.info("校验验证码-service-结束");
	}
	
	/**校验验证码
	 * @param smsCodeDO                    短信记录
	 * @param checkVerificationCodeRequest 校验短信验证码请求
	 */
	private void verifyVerificationCode(EmailCodeDO emailCodeDO, EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest) {
	    //验证码不存在
		if (emailCodeDO == null) {
			LOGGER.error("校验验证码失败,验证码存不存在");
		    throw new EmailException("验证码不存在");
		}
		
		//验证或手机不匹配，验证失败
		if (!emailCodeDO.getCode().equals(emailVerificationCodeCheckRequest.getCode())) {
			LOGGER.error("校验验证码失败,验证码错误[smsLogDO:{}]", emailCodeDO);
		    throw new EmailException("验证码错误");
		}
		
		//判断验证码是否被使用
		//验证码状态(0:未校验;1:已校验)
		if (emailCodeDO.getStatus() == EmailEnums.CodeStatus.VERIFIED) {
			LOGGER.error("校验验证码失败,验证码已被使用过了[smsLogDO:{}]", emailCodeDO);
		    throw new EmailException( "验证码已被使用过了");
		}
		
		//验证码已过期
		if (System.currentTimeMillis() - emailCodeDO.getCreateDate().getTime() > emailProperties.getValidDuration()) {
			LOGGER.error("校验验证码失败,验证码已过期[smsLogDO:{}]", emailCodeDO);
		    throw new EmailException( "验证码已过期");
	    }
	
	}

}
