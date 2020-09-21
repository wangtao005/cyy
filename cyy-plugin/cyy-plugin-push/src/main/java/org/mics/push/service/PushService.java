package org.mics.push.service;

import org.mics.push.request.BasePushRequest;

/**
 * 推送service
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public interface PushService {

    /**
     * 推送消息
     *
     * @param basePushRequest 推送请求
     */
    void jPush(BasePushRequest basePushRequest);

}
