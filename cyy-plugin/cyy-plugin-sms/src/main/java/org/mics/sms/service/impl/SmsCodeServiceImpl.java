package org.mics.sms.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.mics.lang.calculate.RandomUtils;
import org.mics.lang.date.DateUtils;
import org.mics.sms.config.AliYunSmsProperties;
import org.mics.sms.config.VerificationCodeProperties;
import org.mics.sms.entity.SmsCodeDO;
import org.mics.sms.enums.SmsEnums;
import org.mics.sms.exception.SmsException;
import org.mics.sms.helper.AliYunSmsUtils;
import org.mics.sms.repository.SmsCodeRepository;
import org.mics.sms.request.SmsVerificationCodeCheckRequest;
import org.mics.sms.request.SmsVerificationCodeRequest;
import org.mics.sms.service.SmsCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 发送短信验证码实现
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService{
	@Resource
	private AliYunSmsProperties aliYunSmsProperties;
	@Resource
	private VerificationCodeProperties verificationCodeProperties;
	@Resource
	private SmsCodeRepository smsCodeRepository;
	/**
	 * 短信成功状态
	 */
    private static final String SUCCESS = "OK";
    /**
     * 分钟
     */
    private static final Integer MINUTE = 60000;
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsCodeServiceImpl.class);





	@Transactional
	public String sendCode(SmsVerificationCodeRequest verificationCodeRequest) {
		//校验请求
        checkRequestRule(verificationCodeRequest);
        //获取6位随机数
        String code = RandomUtils.randomNumberString(verificationCodeProperties.getSmsLength());
		/*
		 * if("linkai".equals(sendSmsProvider)){ //发送短信 String myinfo
		 * ="【易资询】您正在申请手机注册，验证码为：" + code+"，5分钟内有效！"; String gbkCode = null;//发送内容 try {
		 * gbkCode = URLEncoder.encode(myinfo, "GBK"); } catch
		 * (UnsupportedEncodingException e) { e.printStackTrace(); } String strcon =
		 * "CorpID=" + corp_id + "&" + "Pwd=" + pass_wd + "&" + "Mobile=" +
		 * verificationCodeRequest.getAccount() + "&" + "Content=" + gbkCode;
		 * LOGGER.info("调用短信接口参数:"+strcon); String contentStr = this.getResult(lk_url,
		 * strcon, "UTF-8"); if (Integer.parseInt(contentStr) > 0) { //添加验证码短信记录 String
		 * id = saveSmsCode(verificationCodeRequest, code); return id; } else {
		 * LOGGER.info("调用短信接口返回值:"+contentStr); throw new
		 * SmsException(String.valueOf("短信发送失败")); } }else{ //发送短信 String templeteCode =
		 * null; if(verificationCodeRequest.getType() == SmsEnums.CodeType.LOG_IN) {
		 * templeteCode = aliYunSmsProperties.getLogInCode(); }else
		 * if(verificationCodeRequest.getType() == SmsEnums.CodeType.REGISTERED) {
		 * templeteCode = aliYunSmsProperties.getRegisteredCode(); }else
		 * if(verificationCodeRequest.getType() == SmsEnums.CodeType.RETRIEVE_PASSWORD)
		 * { templeteCode = aliYunSmsProperties.getRetrievePasswordCode(); }else
		 * if(verificationCodeRequest.getType() == SmsEnums.CodeType.MODIFY_PASSWORD) {
		 * templeteCode = aliYunSmsProperties.getModifyPasswordCode(); } String
		 * templeteParam = "{\"code\":\""+code+"\"}"; Map<String,Object> result =
		 * AliYunSmsUtils.sendSms(aliYunSmsProperties,
		 * verificationCodeRequest.getAccount(),templeteCode,templeteParam);
		 * if(SUCCESS.equals(result.get("Code"))) { //添加验证码短信记录 String id =
		 * saveSmsCode(verificationCodeRequest, code); return id; }else { throw new
		 * SmsException(String.valueOf(result.get("Message"))); }
		 * 
		 * }
		 */
      //发送短信
		String templeteCode = null;
		if(verificationCodeRequest.getType() == SmsEnums.CodeType.LOG_IN) {
			templeteCode = aliYunSmsProperties.getLogInCode();
		}else if(verificationCodeRequest.getType() == SmsEnums.CodeType.REGISTERED) {
			templeteCode = aliYunSmsProperties.getRegisteredCode();
		}else if(verificationCodeRequest.getType() == SmsEnums.CodeType.RETRIEVE_PASSWORD) {
			templeteCode = aliYunSmsProperties.getRetrievePasswordCode();
		}else if(verificationCodeRequest.getType() == SmsEnums.CodeType.MODIFY_PASSWORD) {
			templeteCode = aliYunSmsProperties.getModifyPasswordCode();
		}
		String templeteParam = "{\"code\":\""+code+"\"}";
		Map<String,Object> result = AliYunSmsUtils.sendSms(aliYunSmsProperties, verificationCodeRequest.getAccount(),templeteCode,templeteParam);
		if(SUCCESS.equals(result.get("Code"))) {
			//添加验证码短信记录
			String id = saveSmsCode(verificationCodeRequest, code);
			return id;
		}else {
			throw new SmsException(String.valueOf(result.get("Message")));
		}
	}

	/**
	 * 保存发送记录
	 * @param commonResponse
	 * @param verificationCodeRequest
	 * @param code
	 * @return
	 */
	@Transactional
	private String saveSmsCode(SmsVerificationCodeRequest verificationCodeRequest,String code) {
		 //记录验证码发送记录
        SmsCodeDO smsCodeDO = new SmsCodeDO();
        smsCodeDO.setPhone(verificationCodeRequest.getAccount());
        smsCodeDO.setMessage(code);
        smsCodeDO.setStatus(SmsEnums.CodeStatus.NOT_VERIFIED);
        smsCodeDO.setType(verificationCodeRequest.getType());
        smsCodeDO.setCode(code);
        SmsCodeDO result = smsCodeRepository.save(smsCodeDO);
        return result.getId();
	}

	/**
     * 检查参数
     * @param smsSendRequest  短信发送验证码请求
     * @param parameterConfig 参数
     */
    private void checkRequestRule(SmsVerificationCodeRequest smsSendRequest) {
        //当天日期
        String createDate = DateUtils.getDateYMDS(new Date(), "yyyy-MM-dd");
        //验证手机号是否达到最大发送量
        List<SmsCodeDO> smsCodeDOS = smsCodeRepository.findByPhoneAndCreateDateLike(smsSendRequest.getAccount(), createDate);
        if (smsCodeDOS.size() >= verificationCodeProperties.getMaxCountPerPhone()) {
            throw new SmsException("此手机号申请数量已达当日上限");
        }
        
		//根据手机号查找未删除的最后一条短信验证码记录
        SmsCodeDO smsCodeDO = smsCodeRepository.findTop1ByPhoneOrderByCreateDateDesc(smsSendRequest.getAccount()); 
        //如果不是当天第一条，就判断间隔 
        if (smsCodeDO != null && System.currentTimeMillis() - smsCodeDO.getCreateDate().getTime() < verificationCodeProperties.getMinDuration()) {
        	throw new SmsException("此手机号申请间隔至少需要" +verificationCodeProperties.getMinDuration() / MINUTE + "分钟");
        }
		 
    }
    
	@Override
	public void checkCode(SmsVerificationCodeCheckRequest checkVerificationCodeRequest) {
		LOGGER.debug("校验验证码-service-开始[checkVerificationCodeRequest:{}]", checkVerificationCodeRequest);

        SmsCodeDO smsCodeDO = smsCodeRepository.getById(checkVerificationCodeRequest.getId());

        //校验验证码
        verifyVerificationCode(smsCodeDO, checkVerificationCodeRequest);

        //验证码状态(0:未校验;1:已校验)
        smsCodeDO.setStatus(SmsEnums.CodeStatus.VERIFIED);
        smsCodeRepository.save(smsCodeDO);

        LOGGER.info("校验验证码-service-结束");
		
	}
	
	/**校验验证码
	 * @param smsCodeDO                    短信记录
	 * @param checkVerificationCodeRequest 校验短信验证码请求
	 */
	private void verifyVerificationCode(SmsCodeDO smsCodeDO, SmsVerificationCodeCheckRequest checkVerificationCodeRequest) {
	    //验证码不存在
		if (smsCodeDO == null) {
			LOGGER.error("校验验证码失败,验证码存不存在");
		    throw new SmsException("验证码不存在");
		}
		
		//验证或手机不匹配，验证失败
		if (!smsCodeDO.getCode().equals(checkVerificationCodeRequest.getCode())) {
			LOGGER.error("校验验证码失败,验证码错误[smsLogDO:{}]", smsCodeDO);
		    throw new SmsException("验证码错误");
		}
		
		//判断验证码是否被使用
		//验证码状态(0:未校验;1:已校验)
		if (smsCodeDO.getStatus() == SmsEnums.CodeStatus.VERIFIED) {
			LOGGER.error("校验验证码失败,验证码已被使用过了[smsLogDO:{}]", smsCodeDO);
		    throw new SmsException( "验证码已被使用过了");
		}
		
		//验证码已过期
		if (System.currentTimeMillis() - smsCodeDO.getCreateDate().getTime() > verificationCodeProperties.getValidDuration()) {
			LOGGER.error("校验验证码失败,验证码已过期[smsLogDO:{}]", smsCodeDO);
		    throw new SmsException( "验证码已过期");
	    }
	
	}
	/**
	 * @param urlStr 请求的地址 @param content  请求的参数 格式为：name=xxx&pwd=xxx @param encoding 服务器端请求编码。如GBK,UTF-8等 @return
	 */
	public static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();  /* 新建连接实例*/
			connection.setConnectTimeout(2000);                     /* 设置连接超时时间，单位毫秒*/
			connection.setReadTimeout(2000);                        /* 设置读取数据超时时间，单位毫秒*/
			connection.setDoOutput(true);                           /* 是否打开输出流 true|false*/
			connection.setDoInput(true);                            /* 是否打开输入流true|false*/
			connection.setRequestMethod("POST");                    /* 提交方法POST|GET*/
			connection.setUseCaches(false);                         /* 是否缓存true|false                    connection.connect();                                   // 打开连接端口*/
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());/* 打开输出流往对端服务器写数据*/
			out.writeBytes(content);                                /* 写数据,也就是提交你的表单 name=xxx&pwd=xxx*/
			out.flush();                                            /* 刷新*/
			out.close();                                            /* 关闭输出流*/
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));/* 往对端写完数据对端服务器返回数据 ,以BufferedReader流来读取*/
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) buffer.append(line);
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();                            /* 关闭连接*/
			}
		}
		return null;
	}
}
