package org.mics.token.resolver;

import org.mics.token.annotation.LoginUser;
import org.mics.token.constant.TokenConstant;
import org.mics.token.exception.TokenException;
import org.mics.token.model.CurrentUser;
import org.mics.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用户登陆验证
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentUser.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //从header中获取token
        String token = request.getHeader(TokenConstant.TOKEN_STR);
        //如果header中不存在token，则从参数中获取token
        if (token == null || token.trim().length() == 0) {
            token = request.getParameter(TokenConstant.TOKEN_STR);
        }
        if (token == null || token.trim().length() == 0) {
            throw new TokenException("登陆失效,请重新登陆！");
        }

        return tokenService.getUser(token);

    }
}
