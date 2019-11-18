package com.example.wyyu.gitsamlpe.framework.aop.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by wyyu on 2019-11-18.
 **/

@Aspect public class AopTestAspect {

    @Pointcut("execution(@AopTest * *(..))") public void methodClick() {
        // 截取所有以 @AopTest 修饰的方法
    }

    @Before("methodClick()") public void clickBefore(ProceedingJoinPoint joinPoint) {
        // 在方法执行之前执行
    }

    @Around("methodClick()") public void clickArround(ProceedingJoinPoint joinPoint)
        throws Throwable {
        // 通过以下代码在这里执行原方法
        Object[] args = joinPoint.getArgs();
        /*分带参数执行和无参数执行*/
        if (args != null && args.length > 0) {
            joinPoint.proceed(args);
        } else {
            joinPoint.proceed();
        }
        //在执行原方法的前后可选择加入一些特殊的逻辑，来决定原方法是否继续执行
    }

    @After("methodClick()") public void clickAfter(ProceedingJoinPoint joinPoint) {
        // 在方法执行结束之后执行
    }
}
