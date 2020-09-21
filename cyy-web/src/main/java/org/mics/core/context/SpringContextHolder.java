package org.mics.core.context;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 以静态变量形式获取spring中bean
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }


    /**
     * 取得存储在静态变量中的ApplicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }


    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * 如果有多个Bean符合Class, 取出第一个.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        @SuppressWarnings("rawtypes")
        Map beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return (T) beanMaps.values().iterator().next();
        } else {
            return null;
        }
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        applicationContext = null;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
