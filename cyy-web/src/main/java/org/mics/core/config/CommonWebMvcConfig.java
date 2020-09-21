package org.mics.core.config;

import java.util.Arrays;
import java.util.List;

import org.mics.core.interceptor.PlatformInterceptor;
import org.mics.core.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "cyy.componet",name ="cache" )
public class CommonWebMvcConfig  implements WebMvcConfigurer{
	 @Autowired
	 private RepeatSubmitInterceptor repeatSubmitInterceptor;
	 
	 @Autowired
	 private PlatformInterceptor platformInterceptor;
	 
	 private List<String> list = Arrays.asList("/admin/css/**","/admin/dist/**","/admin/js/**","/admin/templates/**","/platform/user/login","/platform/user/logout");

    /**
     * 自定义拦截规则
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(repeatSubmitInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/upload/**","/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg","/**/*.html");//排除upload
      //平台端权限
        registry.addInterceptor(platformInterceptor)
        .addPathPatterns("/platform/**")
        .addPathPatterns("/admin/**")
        .excludePathPatterns(list) ;
    }
	
}
