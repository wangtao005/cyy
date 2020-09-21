package org.mics.captcha.service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.mics.captcha.config.CaptchaConfig;
import org.mics.lang.image.ImageUtils;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService{
	@Resource
	private CaptchaConfig captchaConfig;
	
	
    public Map<String,Object> getImageVerifyCode(){
		/*
		 * BufferedImage orginImage =
		 * ImageUtils.getRandomImage(captchaConfig.getImagePath(),
		 * captchaConfig.getImageType());
		 * 
		 * Random random = new Random(); int randomx = random.nextInt(50); int x =
		 * captchaConfig.getPointx() + randomx; int randomy = random.nextInt(20); int y
		 * = captchaConfig.getPointy()+randomy;
		 */
        
		/*
		 * BufferedImage cutImage = ImageUtils.cutImage(new FileInputStream(file),
		 * captchaConfig.getImageType(),x ,y, captchaConfig.getCutWidth(),
		 * captchaConfig.getCutHeight());
		 * 
		 * // 剪切之后原图加背影 int[][] markData =
		 * ImageUtils.getCutAreaData(orginalImage.getWidth(),orginalImage.getHeight(),x,
		 * y, captchaConfig.getCutWidth(), captchaConfig.getCutHeight());
		 * ImageUtils.cutByTemplate(orginalImage, markData);
		 * 
		 * Map<String,Object> resultMap = new HashMap<String, Object>();
		 * resultMap.put("bigImage",
		 * ImageUtils.imageToBase64(orginalImage,captchaConfig.getImageType()));//大图
		 * resultMap.put("smallImage",
		 * ImageUtils.imageToBase64(cutImage,captchaConfig.getImageType()));//小图
		 * resultMap.put("xWidth",x); resultMap.put("yHeight",y);
		 */
		Map<String,Object> resultMap = new HashMap<String, Object>();
		return resultMap;
    }
}
