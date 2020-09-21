package org.mics.token.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES128Utils {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encrypt(String content, String key) {

        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, genKey(key));
            byte[] result = cipher.doFinal(content.getBytes("UTF-8"));
            // 加密
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String decrypt(String content, String key) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, genKey(key));
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            // 加密
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据密钥获得 SecretKeySpec
     *
     * @return
     */
    private static SecretKeySpec genKey(String strKey) {
        return new SecretKeySpec(strKey.getBytes(), ALGORITHM);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {

        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }

        return result;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        String key = "ijhy1876gbs0978n";
        String en = encrypt("1", key);
        String de = decrypt("38b57869d2285cf019558ad23ac5e917", key);
        Long end = System.currentTimeMillis();

        System.out.println((end - start));
        System.out.println(en);
        System.out.println(de);
    }
}