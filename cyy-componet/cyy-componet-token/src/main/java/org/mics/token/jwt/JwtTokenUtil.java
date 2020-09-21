package org.mics.token.jwt;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.mics.token.config.TokenConfig;
import org.mics.token.enums.TokenEnum;
import org.mics.token.exception.TokenException;
import org.mics.token.utils.AES128Utils;
import org.mics.token.utils.DateTimeUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

/**
 * token 工具
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
public class JwtTokenUtil {

    //加密算法
    private static final String HEADER_ALG = "HS256";
    //加密算法
    private static final String HEADER_TYPE = "jwt";

    /**
     * 自动生成token
     *
     * @param payload
     * @return
     */
    public static String generate(JwtPayLoad payload, TokenConfig tokenConfig) {

        Date iat = new Date();
        Date exp = getExp(payload);

        JWTCreator.Builder builder = JWT.create()
                .withHeader(getHeader())
                .withIssuer(tokenConfig.getIss())
                .withIssuedAt(iat)
                .withExpiresAt(exp)
                .withJWTId(payload.getUserId());

        try {
            builder = buildClaim(payload, builder);
        } catch (IllegalAccessException e) {
            throw new TokenException("token生成异常");
        }

        return builder.sign(Algorithm.HMAC256(tokenConfig.getSecret()));
    }

    /**
     * 根据token解析出Payload数据
     *
     * @param token token
     * @return Payload
     */
    public static String getPayloadStringByToken(String token) {

        try {
            String[] split = token.split("\\.");
            String payLoadString = split[1];
            byte[] bytes = Base64.decodeBase64(payLoadString);
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new TokenException("请求错误，请重新登录");
        }
    }

    /**
     * 解析token并获取LoginUser对象
     *
     * @param token
     * @param tokenConfig
     * @return
     */
    public static JwtPayLoad getPayLoadByToken(String token, TokenConfig tokenConfig) {
        token = unWrapperToken(token, tokenConfig);
        String payload = getPayloadStringByToken(token);

        // 常用字段
        JwtPayLoad payLoad = JsonIterator.deserialize(payload, JwtPayLoad.class);

        // 自定义字段


        return payLoad;
    }

    /**
     * 解析token并获取source
     *
     * @param token
     * @param tokenConfig
     * @return
     */
    public static String getSourcesByToken(String token, TokenConfig tokenConfig) {
        token = unWrapperToken(token, tokenConfig);
        String payload = getPayloadStringByToken(token);
        return JsonIterator.deserialize(payload).get("sources").toString();
    }

    /**
     * 解析token并获取userId
     *
     * @param token
     * @param tokenConfig
     * @return
     */
    public static String getUserIdByToken(String token, TokenConfig tokenConfig) {
        token = unWrapperToken(token, tokenConfig);
        String payload = getPayloadStringByToken(token);
        return JsonIterator.deserialize(payload).get("sources").toString();
    }


    /**
     * 解析token并获取LoginUser对象
     *
     * @param token
     * @param tokenConfig
     * @return
     */
    public static Any getCustomField(String token, String fileName, TokenConfig tokenConfig) {
        token = unWrapperToken(token, tokenConfig);
        String payload = getPayloadStringByToken(token);

        return JsonIterator.deserialize(payload).get(fileName);
    }

    /**
     * jwt 头信息
     *
     * @return
     */
    private static Map<String,Object> getHeader() {
        Map<String, Object> headerClaims = new HashMap<>(2);
        headerClaims.put(PublicClaims.ALGORITHM, HEADER_ALG);
        headerClaims.put(PublicClaims.TYPE, HEADER_TYPE);
        return headerClaims;
    }

    /**
     * 获取过期时间
     *
     * @param payload
     * @return
     */
    private static Date getExp(JwtPayLoad payload) {
        Date exp = null;
        if (payload.getSource() == TokenEnum.TokenSource.ADMIN) {
            //过期时间为第二天的凌晨三点钟
            exp = DateTimeUtil.getThreeOclockAMOfTheNextDay();
        } else if (payload.getSource() == TokenEnum.TokenSource.END_USER) {
            //过期时间为第七天的凌晨三点钟
            exp = DateTimeUtil.getThreeOclockAMOfSeventhDay();
        } else if (payload.getSource() == TokenEnum.TokenSource.WX) {
            //过期时间为第七天的凌晨三点钟
            exp = DateTimeUtil.getThreeOclockAMOfSeventhDay();
        }
        return exp;
    }

    /**
     * 加密包装token
     *
     * @param token token
     * @return wrappedToken：包装后的token
     */
    public static String wrapperToken(String token, TokenConfig tokenConfig) {

        //需要包装
        if (tokenConfig.getWrapperSwitch()) {
            return AES128Utils.encrypt(token, tokenConfig.getWrapperKey());
        }

        return token;
    }

    /**
     * 去除token包装
     *
     * @param token token
     * @return token
     */
    public static String unWrapperToken(String token, TokenConfig tokenConfig) {

        //需要解包
        if (tokenConfig.getWrapperSwitch()) {
            String wrappedToken = token.split("\\.")[0];
            return AES128Utils.decrypt(wrappedToken, tokenConfig.getWrapperKey());
        }

        return token;
    }

    /**
     * 通过反射设置token的字段
     *
     * @param payload
     * @param builder
     * @return
     * @throws IllegalAccessException
     */
    private static JWTCreator.Builder buildClaim(JwtPayLoad payload, JWTCreator.Builder builder) throws IllegalAccessException {

        // 反射获取对象全部的字段
        Field[] fieldlist = payload.getClass().getDeclaredFields();
        // 逐个加入   builder中
        for (Field field : fieldlist) {
            // 字段名
            String fieldName = field.getName();

            // 获取字段的值
            field.setAccessible(true);
            Object fieldValue = field.get(payload);

            if (fieldValue == null) {
                continue;
            }

            // 设值
            builder = setClaim(builder, fieldName, fieldValue);

        }

        return builder;
    }

    /**
     * 设值
     *
     * @param builder
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private static JWTCreator.Builder setClaim(JWTCreator.Builder builder, String fieldName, Object fieldValue) {
        // Integer
        if (fieldValue instanceof Integer) {
            builder.withClaim(fieldName, (Integer) fieldValue);
        } else if (fieldValue instanceof Long) {
            // Integer
            builder.withClaim(fieldName, (Boolean) fieldValue);
        } else if (fieldValue instanceof Double) {
            // Double
            builder.withClaim(fieldName, (Double) fieldValue);
        } else if (fieldValue instanceof Boolean) {
            // Boolean
            builder.withClaim(fieldName, (Boolean) fieldValue);
        } else if (fieldValue instanceof String) {
            // String
            builder.withClaim(fieldName, String.valueOf(fieldValue));
        } else if (fieldValue instanceof Date) {
            // Date
            builder.withClaim(fieldName, (Date) fieldValue);
        } else if (fieldValue instanceof TokenEnum.TokenSource) {
            // 枚举
            TokenEnum.TokenSource source = (TokenEnum.TokenSource) fieldValue;
            builder.withClaim(fieldName, source.name());
        } else if (fieldValue instanceof Map) {
            // map 为自定义字段
            @SuppressWarnings("unchecked")
			Map<String, Object> mapVale = (Map<String, Object>) fieldValue;
            for (Map.Entry<String, Object> entry : mapVale.entrySet()) {
                builder = setClaim(builder, entry.getKey(), entry.getValue());
            }
        } else {
            // 不支持其他类型
            throw new TokenException("token生成异常");
        }
        return builder;
    }


    public static void main(String[] args) {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("departmentId", "12312414141412");

        JwtPayLoad payLoad = new JwtPayLoad("12", "18200389020", "五个中文字", "12312", TokenEnum.TokenSource.ADMIN, customFields);
        TokenConfig tokenConfig = new TokenConfig();
        tokenConfig.setIss("");
        tokenConfig.setDuplicate(true);
        tokenConfig.setDefaultToken("124");
        tokenConfig.setRequestDurationMiles(60000);
        tokenConfig.setVerifyTime(false);
        tokenConfig.setWrapperKey("");
        tokenConfig.setWrapperSwitch(false);
        tokenConfig.setSecret("123");

        String token = generate(payLoad, tokenConfig);
        System.out.println(token);
    }
}
