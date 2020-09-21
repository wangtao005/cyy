package org.mics.auth.aop;


import java.lang.reflect.Method;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mics.auth.annotion.Permission;
import org.mics.auth.context.LoginContext;
import org.mics.auth.enums.AuthExceptionState;
import org.mics.auth.exception.AuthException;
import org.mics.auth.service.AuthService;
import org.mics.core.context.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Aspect
@Component
public class PermissionAop {
    @Autowired
    private AuthService authService;

    /**
     * 切面入口
     */
    @Pointcut(value = "@annotation(org.mics.auth.annotion.Permission)")
    private void cutPermission() {
    }

    /**
     * 切面处理
     *
     * @param point
     */
    @Around("cutPermission()")
    private Object doPermission(ProceedingJoinPoint point) throws Throwable {
        //获取切面对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        //执行前校验是否进行了上下文设置
        if (!SpringContextHolder.getBean(LoginContext.class).hasLogin()) {
            throw new AuthException(AuthExceptionState.LOGIN_EXPPIRED.getCode(), AuthExceptionState.LOGIN_EXPPIRED.getMessage());
        }

        //获取切面参数
        String[] permissions = permission.value();

        //逻辑处理
        boolean checkResult;
        if (permissions != null && permissions.length > 0) {
            //1、指定角色
            checkResult = authService.checkAuthByRoles(permissions);
        } else {
            //2、不指定角色
            checkResult = authService.checkAuth();
        }

        //如果有权限则放行，没有则跑出异常
        if (!checkResult) {
            throw new AuthException( AuthExceptionState.NO_PERMISSION.getCode(),AuthExceptionState.NO_PERMISSION.getMessage());
        }

        return point.proceed();
    }
}
