package org.mics.auth.annotion;

import java.lang.annotation.*;

/**
 * 权限
 * @author mics
 * @date 2020年6月12日
 * @version  1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    /**
     * 角色英文名称
     * @return
     */
    String[] value() default {};
}

