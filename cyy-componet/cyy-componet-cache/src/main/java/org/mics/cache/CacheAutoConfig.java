package org.mics.cache;

import org.mics.cache.ehcache.EhcacheConfig;
import org.mics.cache.ehcache.EhcacheHelper;
import org.mics.cache.redis.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 *  自定义缓存配置
 * @author mics
 * @date 2020年6月17日
 * @version  1.0
 */
@Configuration
@EnableCaching
@Conditional(value = {CacheDefineCondition.class})
@Import(value = {EhcacheConfig.class, RedisConfig.class})
@AutoConfigureAfter(value = {EhcacheConfig.class, RedisConfig.class})
public class CacheAutoConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAutoConfig.class);
    
    /**
     * 配置系统需要的缓存工具类
     * @return
     */
	@Primary
	@Bean("cacheManager")
	@ConditionalOnProperty(prefix = "cyy.componet.cache",name = "type",havingValue = "redis")
	public CacheManager initRedisCacheManager(RedisCacheManager redisCacheManager) {
		LOGGER.warn("配置全局缓存管理器-redis缓存成功！");
		return redisCacheManager; 
	}
	 
    
    /**
     * 配置系统需要的缓存工具类
     * @return
     */
    @Primary
	@Bean("cacheManager")
    @ConditionalOnProperty(prefix = "cyy.componet.cache",name = "type",havingValue = "ehcache")
    public CacheManager initECacheManager(EhCacheCacheManager ehCacheCacheManager) {
    	LOGGER.warn("配置全局缓存管理器-ehcache缓存成功！");
        return ehCacheCacheManager;
    }
   
    @Bean
    @ConditionalOnProperty(prefix = "cyy.componet.cache",name = "type",havingValue = "ehcache")
    public EhcacheHelper ehcacheHelper(EhCacheCacheManager ehCacheCacheManager){
		EhcacheHelper ehcacheHelper =new EhcacheHelper();
		ehcacheHelper.setEhCacheCacheManager(ehCacheCacheManager);
		return ehcacheHelper;
    }
    
}
 class CacheDefineCondition extends AnyNestedCondition{
		public CacheDefineCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}
		
		@ConditionalOnProperty(prefix = "cyy.componet.cache",name = "type",havingValue = "ehcache")
		static class EchacheProperty{
			
		}
		
		@ConditionalOnProperty(prefix="cyy.componet.cache",name="type",havingValue = "redis")
		static class RedisProperty{
			
		}
 }


