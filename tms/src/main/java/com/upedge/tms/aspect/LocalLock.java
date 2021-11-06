package com.upedge.tms.aspect;

import java.lang.annotation.*;

/**
 * Created by jiaqi on 2019/9/15.
 */
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
