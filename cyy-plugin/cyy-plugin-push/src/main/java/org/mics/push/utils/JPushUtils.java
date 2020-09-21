package org.mics.push.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mics.push.config.PushConfig;
import org.mics.push.enums.PushEnums;
import org.mics.push.exception.PushException;
import org.mics.push.request.BasePushRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;

/**
 * 极光推送工具类
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public class JPushUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JPushUtils.class);
	
    /**
     * 推送
     * @param basePushRequest 基础推送请求
     * @param pushConfig      推送配置
     */
    public static void jPush(BasePushRequest basePushRequest, PushConfig pushConfig) {
        //校验推送参数
        checkJPush(basePushRequest);

        Boolean androidResult = false;
        Boolean iosResult = false;
        switch (basePushRequest.getDeviceType()) {
            case ANDROID:
                //推送消息-android
                androidResult = jPushByAndroid(basePushRequest, pushConfig);
                break;
            case IOS:
                //推送消息-ios
                iosResult = jPushByIos(basePushRequest, pushConfig);
                break;
            default:
                //推送消息-android, 推送消息-ios
                androidResult = jPushByAndroid(basePushRequest, pushConfig);
                iosResult = jPushByIos(basePushRequest, pushConfig);
        }

        if (androidResult && iosResult) {
            LOGGER.info("极光推送android和ios成功");
        } else if (androidResult) {
            LOGGER.info("极光推送android成功[basePushRequest:{}]", basePushRequest);
        } else if (iosResult) {
            LOGGER.info("极光推送ios成功[basePushRequest:{}]", basePushRequest);
        } else {
            LOGGER.info("极光推送失败[basePushRequest:{}]", basePushRequest);
        }

    }

    /**
     * 校验推送
     *
     * @param basePushRequest 推送请求
     */
    private static void checkJPush(BasePushRequest basePushRequest) {

        //判断推送目标类型
        if (basePushRequest.getPushAimsType() != PushEnums.PushAimsType.ALL && CollectionUtils.isEmpty(basePushRequest.getPushAimsList())) {
            LOGGER.error("极光推送失败,推送目标类型不是所有时,需要填写推送目标集合");
            throw new PushException(HttpStatus.BAD_REQUEST.value(), "极光推送失败,推送目标类型不是所有时,需要填写推送目标集合");
        }

        //判断附件参数不为空时,value为null改成"";
        if (!basePushRequest.getParam().isEmpty()) {
            for (Map.Entry<String, String> entry : basePushRequest.getParam().entrySet()) {
                if (StringUtils.isBlank(entry.getValue())) {
                    basePushRequest.getParam().put(entry.getKey(), "");
                }
            }
        }
    }

    /**
     * 推送消息-ios
     *
     * @param basePushRequest 基础推送请求
     * @param pushConfig      push配置文件
     * @return true-成功; false-失败
     */
    private static Boolean jPushByIos(BasePushRequest basePushRequest, PushConfig pushConfig) {
        JPushClient jPushClient = new JPushClient(pushConfig.getIosMasterSecret(), pushConfig.getIosAppKey());

        //平台
        Platform platform = Platform.ios();
        //平台通知
        PlatformNotification platformNotification = IosNotification.newBuilder()
                //消息体
                .setAlert(basePushRequest.getText())
                //应用角标: 如果不填，表示不改变角标数字，否则把角标数字改为指定的数字
                .setBadge(+1)
                //ios提示音
                .setSound(pushConfig.getSound())
                //附加参数
                .addExtras(basePushRequest.getParam())
                .build();

        return push(basePushRequest, pushConfig, platformNotification, jPushClient, platform);
    }

    /**
     * 推送消息-android
     *
     * @param basePushRequest 基础推送请求
     * @param pushConfig      push配置文件
     * @return true-成功; false-失败
     */
    private static Boolean jPushByAndroid(BasePushRequest basePushRequest, PushConfig pushConfig) {

        JPushClient jPushClient = new JPushClient(pushConfig.getAndroidMasterSecret(), pushConfig.getAndroidAppKey());
        //发送平台
        Platform platform = Platform.android();
        //通知
        PlatformNotification platformNotification = AndroidNotification.newBuilder()
                //附加参数
                .addExtras(basePushRequest.getParam())
                //消息体
                .setAlert(basePushRequest.getText())
                //标题
                .setTitle(basePushRequest.getTitle())
                //通知栏样式 ID(1,2,4)
                .setBuilderId(1)
                //通知栏样式类型(默认为 0，还有 1，2，3 可选)
                .setStyle(3)
                //图片
                .setBigPicPath(basePushRequest.getBigPicPath())
                .build();
        return push(basePushRequest, pushConfig, platformNotification, jPushClient, platform);
    }


    /**
     * 推送消息
     *
     * @param basePushRequest      基础推送请求
     * @param pushConfig           push配置文件
     * @param platformNotification 平台通知
     * @param jPushClient          jPushClient
     * @param platform             平台
     * @return true-成功; false-失败
     */
    private static Boolean push(BasePushRequest basePushRequest, PushConfig pushConfig, PlatformNotification platformNotification, JPushClient jPushClient, Platform platform) {
        //获取推送目标
        Audience audience = getAudience(basePushRequest);
        //创建option
        PushPayload payload = PushPayload.newBuilder()
                //平台
                .setPlatform(platform)
                //获取推送目标
                .setAudience(audience)
                //通知内容体(notification,message二者必须有其一，可以二者并存)
                .setNotification(
                        Notification.newBuilder()
                                .addPlatformNotification(platformNotification)
                                .build())
                //指定开发环境 true为生产模式 false 为测试模式 (android不区分模式,ios区分模式)
                .setOptions(Options.newBuilder().setApnsProduction(true).setTimeToLive(pushConfig.getTimToLive()).build())
                //消息内容体
                .setMessage(Message.newBuilder().setMsgContent(basePushRequest.getText()).addExtras(basePushRequest.getParam()).build())
                .build();

        try {
            //推送:如果没有ios或者安卓没有设备也会报错
            PushResult result = jPushClient.sendPush(payload);
            return result != null && result.statusCode == 0;

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("发送消息异常[e:{}]", e.getMessage());
            return false;
        }
    }


    /**
     * 获取推送目标
     *
     * @param basePushRequest 基础推送请求
     * @return 推送目标
     */
    private static Audience getAudience(BasePushRequest basePushRequest) {
        //获取推送目标
        switch (basePushRequest.getPushAimsType()) {
            case ALL:
                return Audience.all();
            case TAG:
                return Audience.tag(basePushRequest.getPushAimsList());
            case TAG_AND:
                return Audience.tag_and(basePushRequest.getPushAimsList());
            case ALIAS:
                return Audience.alias(basePushRequest.getPushAimsList());
            case REGISTRATION_ID:
                return Audience.registrationId(basePushRequest.getPushAimsList());
            case SEGMENT:
                return Audience.segment(basePushRequest.getPushAimsList());
            default:
                LOGGER.error("极光推送失败,推送目标类型不存在");
                throw new PushException(HttpStatus.BAD_REQUEST.value(), "极光推送失败,推送目标类型不存在");
        }
    }

}
