package org.mics.lang.calculate;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机数工具类
 * @author mics
 * @date 2019年11月9日
 * @version  1.0
 */
public class RandomUtils {
	/**
	 * 产生随机集合
	 */
    private static final String codeseq = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字
     */
    private static final char[] numberArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 颜RGB色最大值
     */
    private static final int MAX_COLOR_VALUE = 255;

    /**
     * 随机对象
     */
    private static Random rm = new SecureRandom();
    

    /**
     * 生成指定位数英+字母
     *
     * @param length 字符串长度
     * @return 随机数字加字母字符串
     */
    public static final String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(codeseq.charAt(rm.nextInt(codeseq.length())));
        }
        return sb.toString();
    }

    /**
     * 生成指定位数随机数字
     * @param length 字符串长度
     * @return 随机数字
     */
    public static final String randomNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(numberArray[rm.nextInt(numberArray.length)]);
        }
        return sb.toString();
    }

    /**
     * 生成随机颜色值
     *
     * @param fc fc
     * @param bc bc
     * @return
     */
    public static Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        //防止越界
        f = Math.min(f, MAX_COLOR_VALUE);
        b = Math.min(b, MAX_COLOR_VALUE);

        return new Color(f + rm.nextInt(b - f), f + rm.nextInt(b - f), f + rm.nextInt(b - f));
    }


    /**
     * 随机数组中的一个数
     * @param array 传入数组
     * @return 随机返回传入数组中的一个
     */
    public static String randomString(String[] array) {
        Random rm = new Random();
        return array[rm.nextInt(array.length)];
    }

    
}
