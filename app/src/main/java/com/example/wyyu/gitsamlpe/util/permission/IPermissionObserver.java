package com.example.wyyu.gitsamlpe.util.permission;

/**
 * Created by wyyu on 2018/8/25.
 **/

public interface IPermissionObserver {

    // 成功获取权限
    void permissionGranted(@PermissionItemKey int itemKey);

    // 拒绝提供权限
    void permissionDenied(@PermissionItemKey int itemKey);
}
