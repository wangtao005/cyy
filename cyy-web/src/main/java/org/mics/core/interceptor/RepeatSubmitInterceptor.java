package org.mics.core.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.mics.cache.CacheType;
import org.mics.cache.redis.RedisHelper;
import org.mics.core.exception.RepeatSubmitException;
import org.mics.lang.net.IpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * token拦截器
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
@Component
@ConditionalOnProperty(prefix = "cyy.componet",name ="cache" )
public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private RedisHelper redisHelper ;
	@Value(value = "${cyy.componet.cache.type}")
	private String cacheType;
	private static final  long repeatRequestTime = 1L;
	
	/**
	 * 一秒钟只能请求一次
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    	String url = request.getRequestURI();
    	String ip = IpUtils.getIpAddr(request);
    	//参数解析
    	StringBuilder param = new StringBuilder();
    	param.append(request.getQueryString()!=null?request.getQueryString():"");
    	
    	if(StringUtils.isNotBlank(cacheType) && cacheType.equals(CacheType.redis.name())) {
    		 boolean exsits = redisHelper.hasKey(ip+url+param.toString());
    			if(!exsits){
    				redisHelper.set(ip+url, 0, repeatRequestTime);
    	            return true;
    			}else{
    				throw new RepeatSubmitException("请求太过频繁请稍后再试");
    		        
    			}
    	}
    	return true;
       
    }
}