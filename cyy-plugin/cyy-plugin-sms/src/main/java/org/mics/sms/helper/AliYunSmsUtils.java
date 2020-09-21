package org.mics.sms.helper;

import java.util.Map;

import org.mics.sms.config.AliYunSmsProperties;
import org.mics.sms.exception.SmsException;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 阿里云短信发送
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public class AliYunSmsUtils {
	
	/**
	 * 发送短信验证码
	 * @param phoneNumber 手机号码 
	 * @param templateCode 模板code
	 * @param templateParam 模板参数
	 * @return 发送结果
	 */
	public static Map<String,Object> sendSms(AliYunSmsProperties aliYunSmsConfig,String phoneNumber,String templateCode,String templateParam) {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliYunSmsConfig.getAccessKeyId(),aliYunSmsConfig.getAccessKeySecret());
	    IAcsClient client = new DefaultAcsClient(profile);
	    CommonRequest request = new CommonRequest();
	    request.setSysMethod(MethodType.POST);
	    request.setSysDomain("dysmsapi.aliyuncs.com");
	    request.setSysVersion("2017-05-25");
	    request.setSysAction("SendSms");
	    request.putQueryParameter("RegionId", aliYunSmsConfig.getRegionId());
	    request.putQueryParameter("PhoneNumbers", phoneNumber);
	    request.putQueryParameter("SignName", aliYunSmsConfig.getSignName());
	    request.putQueryParameter("TemplateCode", templateCode);
	    request.putQueryParameter("TemplateParam", templateParam);
	    try {
	        CommonResponse response = client.getCommonResponse(request);
	        Gson gson = new GsonBuilder().create();
	        @SuppressWarnings("unchecked")
			Map<String,Object> result = gson.fromJson(response.getData(), Map.class);
	        return result;
	    } catch (ServerException e) {
	    	throw new SmsException("短信服务端异常",e);
	    } catch (ClientException e) {
	    	throw new SmsException("短信客户端异常",e);
	    }
	}


	  
	 
}
