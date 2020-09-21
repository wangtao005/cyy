package org.mics.token.annotation;

import java.lang.annotation.*;

/**
 * 忽略token
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreToken{
	
}
