package org.mics.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mics.auth.cache.SessionManager;
import org.mics.token.constant.TokenConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 做上下文处理  不做token的验证
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
public class AuthorizationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private SessionManager sessionManager;

    public AuthorizationTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 1.从header中获取token
        String token = request.getHeader(TokenConstant.TOKEN_STR);

        // 1.1如果header中不存在token，则从参数中获取token
        if (token == null || token.trim().length() == 0) {
            token = request.getParameter(TokenConstant.TOKEN_STR);
        }

        // 2.有token 并且没有设置security上下文，就设置上下文
        if (token != null && token.trim().length() != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {

            //从缓存中拿userDetails
            UserDetails userDetails = sessionManager.getSession(token);
            if (userDetails == null) {
                chain.doFilter(request, response);
                return;
            }

            //创建当前登录上下文
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
