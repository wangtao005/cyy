package org.mics.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * redis缓存帮助类
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
@Component
public class RedisHelper {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final Logger log = LoggerFactory.getLogger(RedisHelper.class);
	
	/**
	 * 指定缓存失效时间
	 *
	 * @param key  键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
	    try {
	        if (time > 0) {
	            redisTemplate.expire(key, time, TimeUnit.SECONDS);
	        }
	        return true;
	    } catch (Exception e) {
	        log.error("指定缓存失效时间错误：{}", e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * 根据key 获取过期时间
	 *
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
	    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
	    try {
	        return redisTemplate.hasKey(key);
	    } catch (Exception e) {
	        log.error("判断key是否存在错误：{}", e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * 删除缓存
	 *
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
	    if (key != null && key.length > 0) {
	        if (key.length == 1) {
	            redisTemplate.delete(key[0]);
	        } else {
	            redisTemplate.delete(CollectionUtils.arrayToList(key));
	        }
	    }
	}
	
	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
	    return key == null ? null : redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 普通缓存放入
	 *
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
	    try {
	        redisTemplate.opsForValue().set(key, value);
	        return true;
	    } catch (Exception e) {
	        log.error("普通缓存放入错误：{}", e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long time) {
	    try {
	        if (time > 0) {
	            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	        } else {
	            set(key, value);
	        }
	        return true;
	    } catch (Exception e) {
	        log.error("普通缓存放入并设置时间错误：{}", e.getMessage());
	        return false;
	    }
	}
	
	
	
	/**
	 * 查询所有的keys
	 *
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
	    return redisTemplate.keys(pattern);
	}
	
	/**
	 * 删除keys
	 *
	 * @param keys
	 */
	public void delKeys(Set<String> keys) {
	    redisTemplate.delete(keys);
	}
	
	/**
	 * 获取redis数量
	 * @param pattern 前缀
	 * @return 数量
	 */
	public Long getSize(String pattern) {
	    Set<String> keys = redisTemplate.keys(pattern + "*");
	    if (keys.isEmpty()) {
	        return 0L;
	    }
	    return (long) keys.size();
	}
	
	public Long setList(String key,Object value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}
	
	public Object listLeftPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}
	
	public List<Object> listAllPop(String key) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		List<Object> array = new ArrayList<>();
		while(list.size(key)>0) {
			array.add(list.leftPop(key));
		}
		return array;
	}
}
