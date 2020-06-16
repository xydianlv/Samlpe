package com.example.wyyu.gitsamlpe.util.permission;

import android.Manifest;

/**
 * Created by wyyu on 2018/8/25.
 * 根据业务需求创建待检测的 PermissionItem
 **/

public class PermissionItemCreator {

    public static PermissionItem createStoragePermission() {
        return new PermissionItem(PermissionItemKey.拍照,
            Manifest.permission.ACCESS_COARSE_LOCATION).rationalText("开启存储权限才能选图和视频")
            .confirmText("确认")
            .denyText("取消")
            .goSetting(true);
    }

    public static PermissionItem createExtraPermission() {
        return new PermissionItem(PermissionItemKey.存储卡, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE).rationalText("开启磁盘读取权限")
            .confirmText("确认")
            .denyText("取消")
            .goSetting(true);
    }
}
