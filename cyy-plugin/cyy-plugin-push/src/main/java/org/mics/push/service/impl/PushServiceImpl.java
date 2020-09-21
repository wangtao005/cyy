package org.mics.push.service.impl;

import org.mics.push.config.PushConfig;
import org.mics.push.request.BasePushRequest;
import org.mics.push.service.PushService;
import org.mics.push.utils.JPushUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 推送service
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Service
public class PushServiceImpl implements PushService {
    @Autowired
    private PushConfig pushConfig;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

    /**
     * 推送消息
     *
     * @param basePushRequest 推送请求
     */
    @Async
    @Override
    public void jPush(BasePushRequest basePushRequest) {
    	LOGGER.debug("推送消息-service-开始[basePushRequest:{}]", basePushRequest);

        JPushUtils.jPush(basePushRequest, pushConfig);

        LOGGER.debug("推送消息-service-开始");
    }


}
