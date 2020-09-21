package org.mics.push.enums;

/**
 * 推送枚举
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public class PushEnums {

    /**
     * 设备类型
     */
    public enum DeviceType {
        //所有
        ALL,
        //安卓
        ANDROID,
        //ios
        IOS
    }

    /**
     * 推送目标类型
     */
    public enum PushAimsType {
        //标签:多个标签之间是 OR 的关系，即取并集。
        TAG,
        //多个标签之间是 AND 关系，即取交集。
        TAG_AND,
        //别名
        ALIAS,
        //注册ID
        REGISTRATION_ID,
        //用户分群 ID
        SEGMENT,
        //所有
        ALL
    }
}
