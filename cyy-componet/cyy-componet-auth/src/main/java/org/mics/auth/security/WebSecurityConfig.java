package org.mics.auth.security;

import org.mics.auth.filter.AuthorizationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security相关配置
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthorizationTokenFilter authorizationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                // 开启跨域
                .cors().and()
                // 自定义退出
                .logout().disable()
                // 禁用匿名用户
                .anonymous().disable()
                // 全局不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                // 调用api不需要拦截
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源放开过滤
        web.ignoring()
                .antMatchers(
                        "/error", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/pub-resource/**", "/v2/api-docs", "/csrf", "/**"
                );

    }
}
