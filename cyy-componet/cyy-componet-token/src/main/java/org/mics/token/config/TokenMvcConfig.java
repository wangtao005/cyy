package org.mics.token.config;


import java.util.List;

import org.mics.token.interceptor.TokenInterceptor;
import org.mics.token.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * token 拦截注入注入
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
@Configuration
public class TokenMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    /**
     * 自定义拦截规则
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/error", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/pub-resource/**", "/resource/**");
    }

    /**
     * 自定义参数解析器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
}
