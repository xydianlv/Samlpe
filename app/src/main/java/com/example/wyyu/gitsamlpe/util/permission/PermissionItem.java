package com.example.wyyu.gitsamlpe.util.permission;

import java.io.Serializable;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class PermissionItem implements Serializable {

    // 业务需要的一组权限
    public String[] permissionArray;
    public String rationalText;
    public String confirmText;
    public String denyText;

    // 是否需要打开手机设置界面
    public boolean goSetting;

    // 用来标示这组权限所对应的业务，方便在回调中检索使用
    public  @PermissionItemKey int itemKey;

    public PermissionItem(@PermissionItemKey int itemKey, String... permissionArray) {
        if (permissionArray == null || permissionArray.length <= 0) {
            throw new IllegalArgumentException("permissions must have one content at least");
        }

        this.permissionArray = permissionArray;
        this.itemKey = itemKey;
    }

    public PermissionItem rationalText(String rationalText) {
        this.rationalText = rationalText;

        return this;
    }

    public PermissionItem confirmText(String confirmText) {
        this.confirmText = confirmText;

        return this;
    }

    public PermissionItem denyText(String denyText) {
        this.denyText = denyText;

        return this;
    }

    public PermissionItem goSetting(boolean goSetting) {
        this.goSetting = goSetting;

        return this;
    }
}
