package com.wyyu.click_single;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by wyyu on 2019-11-15.
 **/

@Aspect public class TapClickAspect {

    @Pointcut("execution(@com.wyyu.click_single.TapClick * *(..))") public void methodClick() {
        Log.e("TapClickAspectTest", "methodClick");
    }

    @Before("methodClick()") public void clickBefore(JoinPoint joinPoint) {
        Log.e("TapClickAspectTest", "clickBefore");
    }

    @Around("methodClick()") public void clickArround(JoinPoint joinPoint) {
        Log.e("TapClickAspectTest", "clickArround");
    }

    @After("methodClick()") public void clickAfter(JoinPoint joinPoint) {
        Log.e("TapClickAspectTest", "clickAfter");
    }
}
