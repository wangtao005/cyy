package org.mics.core.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * servlet上下文
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class ServletContextHolder {

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
