package com.wyyu.click_expand;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by wyyu on 2019-11-15.
 **/

@Aspect public class SingleClickAspect {

    @Pointcut("execution(@com.wyyu.click_expand.SingleClick * *(..))") public void methodClick() {

    }

    @Before("methodClick()") public void clickBefore(JoinPoint joinPoint) {

    }

    @Around("methodClick()") public void clickArround(JoinPoint joinPoint) {
    }

    @After("methodClick()") public void clickAfter(JoinPoint joinPoint) {

    }
}
