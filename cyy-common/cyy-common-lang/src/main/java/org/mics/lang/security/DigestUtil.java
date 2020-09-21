/**
 * Copyright (c) 2005-2012 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.mics.lang.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;

/**
 * 支持SHA/MD5消息摘要的工具类.
 * 
 */
public class DigestUtil {

    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * 对字节数组进行sha1哈希计算.
     * @param bytes 字节数组
     * @param 经过哈希结算的散列结果 
     */
    public static byte[] sha1(byte[] bytes) {
        return digest(bytes, SHA1, null);
    }
   
   
    
    /**
     *对字节数组进行sha1加盐哈希计算.
     * @param bytes  字节数组
     * @param salt 盐
     * @return
     */
    public static byte[] sha1(byte[] bytes, byte[] salt) {
        return digest(bytes, SHA1, salt);
    }

    /**
     * 对字节数组进行MD5哈希计算.
     * @param bytes 字节数组
     * @param 经过哈希结算的散列结果 
     */
    public static byte[] MD5(byte[] bytes) {
        return digest(bytes, MD5, null);
    }
    
    /**
     * 对字节数组进行MD5哈希计算.
     * @param bytes 字节数组
     * @param 经过哈希结算的散列结果 
     */
    public static byte[] MD5(byte[] bytes, byte[] salt) {
        return digest(bytes, MD5, salt);
    }
    
    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                digest.update(salt);
            }
            byte[] result = digest.digest(input);
            return result;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)" , numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对文件进行md5散列.
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列.
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    /**
     * 对输入流加密
     * @param input
     * @param algorithm
     * @return
     * @throws IOException
     */
    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
