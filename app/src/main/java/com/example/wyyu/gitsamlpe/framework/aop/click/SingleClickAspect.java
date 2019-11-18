package com.example.wyyu.gitsamlpe.framework.aop.click;

import android.view.View;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by wyyu on 2019-11-18.
 *
 * 该 Aspect 为防止重复点击设立，只在需要判断重复点击的地方用 @SingleClick 修饰方法，否则出错
 **/

@Aspect public class SingleClickAspect {

    @Pointcut("execution(@SingleClick * *(..))") public void methodClick() {
    }

    @Around("methodClick()") public void clickArround(ProceedingJoinPoint joinPoint)
        throws Throwable {
        // JoinPoint 为 null 直接退出
        if (joinPoint == null) {
            return;
        }
        // 参数列表为 null 或 empty 直接退出
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length <= 0) {
            return;
        }
        // 获取方法参数
        View view = null;
        for (Object object : args) {
            if (object instanceof View) {
                view = (View) object;
                break;
            }
        }
        // 无 View 类型参数直接退出
        if (view == null) {
            return;
        }
        // 获取方法注解
        Signature signature = joinPoint.getSignature();
        Method method =
            signature instanceof MethodSignature ? ((MethodSignature) signature).getMethod() : null;
        // 方法非 SingleClick 修饰，直接退出
        if (method == null || !method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        // 判断单击时间间隔
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        if (!(SingleClickUtil.hasClick(view.getId(), singleClick.time()))) {
            joinPoint.proceed(args);
        }
    }
}