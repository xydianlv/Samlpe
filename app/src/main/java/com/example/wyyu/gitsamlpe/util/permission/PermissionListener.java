package com.example.wyyu.gitsamlpe.util.permission;

/**
 * Created by wyyu on 2018/8/25.
 **/

public interface PermissionListener {

    // 成功获取权限
    void permissionGranted();

    // 拒绝提供权限
    void permissionDenied();
}
