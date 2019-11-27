package com.example.wyyu.gitsamlpe.util.permission;

import android.content.Context;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.util.LinkedList;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class PermissionCheck {

    private static class CheckHolder {
        private static PermissionCheck permissionCheck = new PermissionCheck();
    }

    public static PermissionCheck getInstance() {
        return CheckHolder.permissionCheck;
    }

    // 一组需要判断的权限集合，一般按照产品功能模块儿组合出一组权限添加进来
    private LinkedList<PermissionItem> permissionItemList;
    private PermissionRequestListener requestListener;

    private PermissionItem permissionItem;

    private PermissionCheck() {
        requestListener = new PermissionRequestListener() {
            @Override public boolean onRequestFinished(String[] permissionArray) {
                permissionItem = permissionItemList.poll();
                if (permissionItem != null) {
                    requestPermission();
                }
                return permissionItem != null;
            }
        };
        permissionItemList = new LinkedList<>();
    }

    public void check(PermissionItem permissionItemFun) {
        if (permissionItemFun == null) {
            return;
        }
        if (!PermissionUtil.isOverMarshmallow()) {
            onPermissionGranted(permissionItemFun);
        } else {
            permissionItemList.add(permissionItemFun);
            if (permissionItem == null) {
                permissionItem = permissionItemList.poll();
                requestPermission();
            }
        }
    }

    private void onPermissionGranted(PermissionItem permissionItem) {

        if (permissionItem == null) {
            return;
        }

        PermissionObservable.getObservable().permissionGranted(permissionItem.itemKey);

        requestListener.onRequestFinished(permissionItem.permissionArray);
    }

    private void requestPermission() {
        if (permissionItem == null) {
            return;
        }

        if (PermissionUtil.hasSelfPermissions(AppController.getAppContext(),
            permissionItem.permissionArray)) {
            onPermissionGranted(permissionItem);
        } else {
            ActivityCheckPermission.open(AppController.getAppContext(), permissionItem);
        }
    }

    public void check(Context context, PermissionItem permissionItemFun) {
        if (permissionItemFun == null) {
            return;
        }
        if (!PermissionUtil.isOverMarshmallow()) {
            onPermissionGranted(permissionItemFun);
        } else {
            permissionItemList.add(permissionItemFun);
            if (permissionItem == null) {
                permissionItem = permissionItemList.poll();
                requestPermission(context);
            }
        }
    }

    private void requestPermission(Context context) {
        if (permissionItem == null) {
            return;
        }

        if (PermissionUtil.hasSelfPermissions(AppController.getAppContext(),
            permissionItem.permissionArray)) {
            onPermissionGranted(permissionItem);
        } else {
            ActivityCheckPermission.open(context, permissionItem);
        }
    }

    // 当 ActivityCheckPermission 判断完一组权限后，告诉 PermissionCheck 检测是否还需要继续判断
    void onPermissionCheckFinished(@PermissionItemKey int itemKey) {
        if (permissionItemList == null || permissionItemList.isEmpty()) {
            return;
        }
        for (PermissionItem permissionItem : permissionItemList) {
            if (permissionItem.itemKey == itemKey) {
                permissionItemList.remove(permissionItem);
                break;
            }
        }
        if (permissionItemList.isEmpty()) {
            return;
        }
        permissionItem = permissionItemList.poll();
        if (permissionItem != null) {
            requestPermission();
        }
    }
}
