package org.mics.log.annotation;

import java.lang.annotation.*;

/**
 * 业务操作日志
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatorLog {

    /**
     * 业务的名称,例如:"修改用户信息"
     */
    String value() default "";
}
