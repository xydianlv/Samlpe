package com.example.wyyu.gitsamlpe.util.permission;

/**
 * Created by wyyu on 2018/8/25.
 **/

class PermissionWrapper {

    PermissionListener permissionListener;
    PermissionItem permissionItem;

    PermissionWrapper(PermissionItem permissionItem, PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
        this.permissionItem = permissionItem;
    }
}
