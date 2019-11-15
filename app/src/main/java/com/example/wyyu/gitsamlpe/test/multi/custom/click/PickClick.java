package com.example.wyyu.gitsamlpe.test.multi.custom.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wyyu on 2019-11-15.
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PickClick {

    // 单击间隔时长
    long time() default 600L;
}
