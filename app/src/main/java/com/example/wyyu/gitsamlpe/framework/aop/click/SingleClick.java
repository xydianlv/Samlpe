package com.example.wyyu.gitsamlpe.framework.aop.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wyyu on 2019-11-18.
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {

    // 单击间隔时长
    long time() default SingleClickUtil.TIME_DIVIDE;
}
