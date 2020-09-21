package org.mics.cache.ehcache;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * 缓存帮助类
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
public class EhcacheHelper {
	private EhCacheCacheManager ehCacheCacheManager;
	
	
	public EhCacheCacheManager getEhCacheCacheManager() {
		return ehCacheCacheManager;
	}


	public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
		this.ehCacheCacheManager = ehCacheCacheManager;
	}


	/**
	 * 添加缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存key
	 * @param value 缓存value
	 */
    public void set(String cacheName,String key,String value){
        Cache cache = ehCacheCacheManager.getCache(cacheName);
        cache.put(key, value);
    }


    /**
     * 获取缓存
     * @param <T>
     * @param cacheName 缓存名称
     * @param key  缓存 key
     * @param type  转化类型
     * @return
     */
    public <T> T get(String cacheName,String key,Class<T> type){
        Cache cache = ehCacheCacheManager.getCache(cacheName);
        return cache.get(key, type);
    }

    
}
