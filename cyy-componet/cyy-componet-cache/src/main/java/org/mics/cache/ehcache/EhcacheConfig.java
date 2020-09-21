package org.mics.cache.ehcache;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 本地缓存配置
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
@Configuration
public class EhcacheConfig {
	

    /**
     * EhCacheManagerFactoryBean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "cyy.componet.cache",name = "type",havingValue = "ehcache")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    /**
     * EhCacheCacheManager
     * @param bean
     * @return
     */
    @Bean
    @ConditionalOnBean(value = EhCacheManagerFactoryBean.class)
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        return new EhCacheCacheManager(bean.getObject());
    }
    
}
