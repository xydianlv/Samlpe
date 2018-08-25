package com.example.wyyu.gitsamlpe.util.permission;

/**
 * Created by wyyu on 2018/8/25.
 **/

public interface IPermissionObservable {

    // 成功获取权限
    void permissionGranted();

    // 拒绝提供权限
    void permissionDenied();

    // 绑定观察者到发布器
    void attach(IPermissionObserver observer);

    // 观察者解除权限监听
    void detach(IPermissionObserver observer);

    // 释放所有观察者
    void release();
}
