package org.mics.lang.calculate;

/**
 * uuid工具类
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
public class UUIDUtil {

    /**
     * 生成uuid
     * @return uuid字符串
     */
    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成指定位数uuid
     * @param length uuid长度
     * @return uuid字符串
     */
    public static String randomUUID(int length) {
        String uuId = randomUUID();
        if (length > uuId.length()) {
            length = uuId.length();
        }
        return uuId.substring(0, length);
    }
}
