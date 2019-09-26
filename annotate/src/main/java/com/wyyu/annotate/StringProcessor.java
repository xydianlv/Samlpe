package com.wyyu.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wyyu on 2019-09-26.
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface StringProcessor {

    String value() default "StringProcessor";
}
