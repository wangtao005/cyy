package org.mics.log.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mics.core.context.ServletContextHolder;
import org.mics.lang.net.IpUtils;
import org.mics.log.annotation.OperatorLog;
import org.mics.log.factory.LogTaskFactory;
import org.mics.log.helper.LogManager;
import org.springframework.stereotype.Component;

/**
 * 业务日志Aop配置
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
@Aspect
@Component
public class OperatorLogAspect {

    /**
     * 参数最大长度
     */
    private static final int PARAMS_LENGTH = 2000;

    /**
     * 切面入口
     */
    @Pointcut(value = "@annotation(org.mics.log.annotation.OperatorLog)")
    public void logPointCut() {
    }

    /**
     * 切面处理
     *
     * @param point
     */
    @Around("logPointCut()")
    public Object doPointCut(ProceedingJoinPoint point) throws Throwable {
        // 业务开始时间
        long beginTime = System.currentTimeMillis();

        // 先执行业务
        Object result = point.proceed();

        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        // 执行处理
        handle(point, time);

        return result;
    }

    /**
     * 处理日志业务
     *
     * @param point
     */
    private void handle(ProceedingJoinPoint point, long time) {
        // 获取request
        HttpServletRequest request = ServletContextHolder.getHttpServletRequest();

        //登录名
        String LoginName = (String) request.getAttribute("Authorization_Account");

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        OperatorLog businessLog = method.getAnnotation(OperatorLog.class);

        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = className + "." + signature.getName() + "()";

        // 请求参数
        Object[] args = point.getArgs();
        String params = "";
        if (args != null && args.length > 0) {
            params =  args[0].toString();
            if (params.length() > PARAMS_LENGTH) {
                params = params.substring(0, PARAMS_LENGTH);
            }
        }

        LogManager.get().executeLog(LogTaskFactory.operationLogBean(LoginName, businessLog.value(), methodName,
                params, IpUtils.getIpAddr(request), time, businessLog.value()));
    }

}
