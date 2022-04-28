package com.upedge.sms.aspect;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {

    String key() default "";

    /**
     * 过期时间
     */
    int expire() default 5;
}
