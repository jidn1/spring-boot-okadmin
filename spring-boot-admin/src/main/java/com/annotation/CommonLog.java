package com.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/26 13:41
 * @Description:
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface CommonLog {

    String name();
}
