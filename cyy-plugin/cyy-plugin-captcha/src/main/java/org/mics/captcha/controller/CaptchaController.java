package org.mics.captcha.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.mics.captcha.service.CaptchaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码
 * @author mics
 * @date 2020年6月5日
 * @version  1.0
 */
@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController {
	@Resource
	private CaptchaService captchaService;
	
	/**
     * @param @return 参数说明
     * @return BaseRestResult 返回类型
     * @Description: 生成滑块拼图验证码
     */
    @RequestMapping(value = "/getImageVerifyCode", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Map<String,Object> getImageVerifyCode() {
    	return captchaService.getImageVerifyCode();
    }
    
}
