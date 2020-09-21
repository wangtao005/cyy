package org.mics.pay.weixin;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.mics.lang.file.XmlUtils;
import org.mics.pay.weixin.constant.WXPayConstants;
import org.mics.pay.weixin.constant.WXPayConstants.SignType;
import org.mics.pay.weixin.exception.SignException;

/**
 * 微信支付签名工具类
 * @author mics
 * @date 2020年7月9日
 * @version  1.0
 */
public class SignUtil {
	

    /**
     * 生成带有 sign 的 XML 格式字符串
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) {
        return generateSignedXml(data, key, SignType.MD5);
    }
    

    /**
     * 生成带有 sign 的 XML 格式字符串
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType)  {
        String sign = generateSignature(data, key, signType);
        data.put(WXPayConstants.FIELD_SIGN, sign);
        return XmlUtils.mapToXmlStr(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = XmlUtils.xmlToMap(xmlStr);
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, SignType.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, SignType signType)  {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        } else {
            throw new SignException(String.format("Invalid sign_type: %s", signType));
        }
    }




    /**
     * 生成 MD5
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data)  {
        java.security.MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(data.getBytes("UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (byte item : array) {
	            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
	        }
	        return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new SignException("生成签名时加密出错！",e);
		}
       
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key)  {
        Mac sha256_HMAC;
		try {
			sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
	        sha256_HMAC.init(secret_key);
	        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (byte item : array) {
	            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
	        }
	        return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            throw new SignException("生成签名时加密出错！",e);

		}
       
    }


    

}
