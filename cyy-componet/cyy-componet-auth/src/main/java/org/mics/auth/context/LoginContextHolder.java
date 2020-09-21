package org.mics.auth.context;

import org.mics.core.context.SpringContextHolder;

/**
 * 登陆上下文
 * @author mics
 * @date 2020年6月12日
 * @version  1.0
 */
public class LoginContextHolder {

    public static LoginContext getContext() {
        return SpringContextHolder.getBean(LoginContext.class);
    }

}
