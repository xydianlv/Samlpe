package com.example.wyyu.gitsamlpe.test.multi.custom.click;

import android.util.Log;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by wyyu on 2019-11-15.
 **/

@Aspect public class PickClickAspect {

    @Pointcut("execution(@PickClick * *(..))") public void methodClick() {
        Log.e("TapClickAspectTest", "methodClick");
    }

    @Before("methodClick()") public void clickBefore(ProceedingJoinPoint joinPoint) {
        // 这里的 joinPoint 为 null
    }

    @Around("methodClick()") public void clickArround(ProceedingJoinPoint joinPoint)
        throws Throwable {
        Log.e("TapClickAspectTest", "clickArround");

        Log.e("TapClickAspectTest", "clickBefore");
        Object[] args = joinPoint.getArgs();
        /*分带参数执行和无参数执行*/
        if (args != null && args.length > 0) {
            Log.d("timeEva", "have args:" + Arrays.toString(args));
            joinPoint.proceed(args);
        } else {
            Log.d("timeEva", "have no args");
            joinPoint.proceed();
        }
    }

    @After("methodClick()") public void clickAfter(ProceedingJoinPoint joinPoint) {
        Log.e("TapClickAspectTest", "clickAfter");
    }
}
