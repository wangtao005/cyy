
package org.mics.token.service.impl;

import org.mics.cache.redis.RedisHelper;
import org.mics.token.config.TokenConfig;
import org.mics.token.constant.TokenConstant;
import org.mics.token.enums.TokenEnum;
import org.mics.token.exception.TokenException;
import org.mics.token.jwt.JwtPayLoad;
import org.mics.token.jwt.JwtTokenUtil;
import org.mics.token.model.CurrentUser;
import org.mics.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * token
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
@Service
public class TokenServiceImpl implements TokenService {
	 /**
     * 生成token后台用户类型
     */
    private static final String USER_TYPE_ORDINAL = "userTypeOrdinal";
    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RedisHelper redisService;
    private static final Logger LOGGER =  LoggerFactory.getLogger(TokenServiceImpl.class);
    
    /**
     * 生成token
     *
     * @param jwtPayLoad 主要生成的module
     * @return
     */
    @Override
    public String generate(JwtPayLoad jwtPayLoad) {
        LOGGER.debug("生成token-service开始[jwtPayLoad:{}]", jwtPayLoad);

        //参数校验
        verifyPayLoad(jwtPayLoad);

        //生成token
        String token = JwtTokenUtil.generate(jwtPayLoad, tokenConfig);

        token = JwtTokenUtil.wrapperToken(token, tokenConfig) + ".";

        //是否开启单机登录
        if (!tokenConfig.getDuplicate() && jwtPayLoad.getSource() == TokenEnum.TokenSource.ADMIN) {
            //判断是否是超级管理员
            Integer userTypeOrdinal = Integer.valueOf(getCustomField(token, USER_TYPE_ORDINAL).toString());

            if (userTypeOrdinal == TokenEnum.UserType.SUPER_ADMIN.ordinal()) {
                String key = new StringBuffer(TokenConstant.USER_TOKEN).append(jwtPayLoad.getAccount()).append("_").append(jwtPayLoad.getUserId()).toString();
                boolean result = redisService.set(key, token, tokenConfig.getTokenCacheTime());
                LOGGER.info("生成token-service开始[key:{}, value:{}, result{}]", key, token, result);
            }
        }

        LOGGER.debug("生成token成功[token:{}]", token);
        return token;
    }

    /**
     * 校验token
     *
     * @param token token
     * @return token
     */
    @Override
    public String verify(String token) {
    	LOGGER.debug("校验token-service开始[token:{}]", token);

        //默认token用于测试或者服务间调用
        if (tokenConfig.getDefaultToken().equals(token)) {
            return "系统调用";
        }

        JwtPayLoad payLoad;
        try {
            payLoad = JwtTokenUtil.getPayLoadByToken(token, tokenConfig);
        } catch (Exception ex) {
            LOGGER.error("Token校验失败， token不合法");
            throw new TokenException(HttpStatus.BAD_REQUEST.value(), "token不合法");
        }

        // 校验请求时间
        verifyTime(token);

        //是否开启单机登录
        if (!tokenConfig.getDuplicate() && payLoad.getSource() == TokenEnum.TokenSource.ADMIN) {
            verifyDup(token, payLoad.getUserId(), payLoad.getAccount());
        }

        //app端
        if (payLoad.getSource() == TokenEnum.TokenSource.END_USER) {
            //添加用户id(获取活跃用户数量)
            redisService.set(TokenConstant.END_USER_ID + payLoad.getUserId(), payLoad.getUserId(), 10 * 60);
        }

        LOGGER.debug("token校验成功");
        return payLoad.getAccount();
    }

    /**
     * 通过token获取用户信息
     * 1、不能用在默认token上
     * 2、理论上不会出现为空的可能，都是进过token检验后才能调用该方法
     *
     * @param token
     * @return
     */
    @Override
    public CurrentUser getUser(String token) {
        JwtPayLoad payLoad = JwtTokenUtil.getPayLoadByToken(token, tokenConfig);
        return new CurrentUser(payLoad.getUserId(), payLoad.getAccount(), payLoad.getName(), payLoad.getAvatar());
    }

    /**
     * 仅需要ID的时候
     *
     * @param token
     * @return
     */
    @Override
    public String getUserId(String token) {
        JwtPayLoad payLoad = JwtTokenUtil.getPayLoadByToken(token, tokenConfig);
        return payLoad.getUserId();
    }

    /**
     * 获取自定义字段的值
     *
     * @param token
     * @param fieldName
     * @return
     */
    @Override
    public Object getCustomField(String token, String fieldName) {
        return JwtTokenUtil.getCustomField(token, fieldName, tokenConfig);
    }

    /**
     * 开启单机登录后的验证
     *
     * @param token
     * @param userId
     * @param source
     */
    public void verifyDup(String token, String userId, String source) {
        //判断是否是超级管理员
        Integer userTypeOrdinal = Integer.valueOf(getCustomField(token, USER_TYPE_ORDINAL).toString());
        if (userTypeOrdinal != TokenEnum.UserType.SUPER_ADMIN.ordinal()) {
            return;
        }

        String tokenKey = new StringBuffer(TokenConstant.USER_TOKEN).append(source).append("_").append(userId).toString();
        // 缓存中的数据
        String redisToken = (String) redisService.get(tokenKey);
        // 如果缓存不存在该用户token
        if (redisToken == null || redisToken.length() == 0) {
            // Token 超时
        	LOGGER.info("单机验证,token不存在[redisToken:{}]", redisToken);
            throw new TokenException("单机验证,token不存在");
        }
        // token被替换
        if (!token.equals(redisToken)) {
            LOGGER.info("单机验证,token被替换[token:{}, redisToken:{}]", token, redisToken);
            throw new TokenException("单机验证,token不存在");
        }
    }

    /**
     * 校验请求时间戳
     *
     * @param token token
     * @return true:请求有效；false：请求无效
     */
    private void verifyTime(String token) {
        //需要校验时间
        if (tokenConfig.getVerifyTime()) {

            //从token中获取请求时间戳
            Long times = parseTime(token);

            //检查请求时间
            if (!checkRequestTime(times)) {
                LOGGER.error("token校验失败，请求超时");
                throw new TokenException( "token错误,请求超时");
            }
        }
    }


    /**
     * 参数校验
     *
     * @param jwtPayLoad
     */
    private static void verifyPayLoad(JwtPayLoad jwtPayLoad) {
        if (jwtPayLoad == null || jwtPayLoad.getSource() == null || StringUtils.isEmpty(jwtPayLoad.getUserId())) {
            LOGGER.error("token生成异常");
            throw new TokenException("系统异常，请联系管理员");
        }
    }

    /**
     * 检查请求时间
     *
     * @param requestTime 请求时间戳
     * @return 当前请求是否有效
     */
    private boolean checkRequestTime(Long requestTime) {
        long duration = System.currentTimeMillis() - requestTime;
        if (duration > tokenConfig.getRequestDurationMiles() || duration < 0) {
        	LOGGER.info("校验时间失败，请求超时无效[duration:{}]", duration);
            return false;
        }
        //请求有效
        return true;
    }

    /**
     * 从token解析时间戳
     *
     * @param token token
     * @return 请求时间戳
     */
    private Long parseTime(String token) {
        Long times;
        try {
            //获取16进制字符串
            String timeHex = token.split("\\.")[1];
            //将10进制时间戳解析成long
            times = Long.valueOf(timeHex, 16);
        } catch (Exception e) {
        	LOGGER.error("token校验失败，解析token时间错误[e:{}]", e);
            throw new TokenException("token错误");
        }

        //返回解析出的时间
        return times;
    }

	
}
