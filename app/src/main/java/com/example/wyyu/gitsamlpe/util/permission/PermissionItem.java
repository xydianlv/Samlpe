package com.example.wyyu.gitsamlpe.util.permission;

import java.io.Serializable;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class PermissionItem implements Serializable {

    public String[] permissionArray;
    public String rationalText;
    public String confirmText;
    public String denyText;

    public boolean goSetting;

    public PermissionItem(String... permissionArray) {
        if (permissionArray == null || permissionArray.length <= 0) {
            throw new IllegalArgumentException("permissions must have one content at least");
        }

        this.permissionArray = permissionArray;
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
