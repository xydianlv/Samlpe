package com.example.wyyu.gitsamlpe.util.permission;

import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.util.LinkedList;
import junit.framework.Assert;

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

    private LinkedList<PermissionWrapper> wrapperLinkedList;
    private PermissionRequestListener requestListener;

    private PermissionWrapper permissionWrapper;

    private PermissionCheck() {
        requestListener = new PermissionRequestListener() {
            @Override public boolean onRequestFinished(String[] permissionArray) {
                permissionWrapper = wrapperLinkedList.poll();
                if (permissionWrapper != null) {
                    requestPermission();
                }
                return permissionWrapper != null;
            }
        };
        wrapperLinkedList = new LinkedList<>();
    }

    public void check(PermissionItem permissionItem, PermissionListener permissionListener) {
        if (permissionItem == null || permissionListener == null) {
            return;
        }
        if (!PermissionUtil.isOverMarshmallow()) {
            onPermissionGranted(permissionItem, permissionListener);
        } else {
            wrapperLinkedList.add(new PermissionWrapper(permissionItem, permissionListener));
            if (permissionWrapper == null) {
                permissionWrapper = wrapperLinkedList.poll();
                requestPermission();
            }
        }
    }

    private void onPermissionGranted(PermissionItem permissionItem,
        PermissionListener permissionListener) {

        Assert.assertNotNull(permissionItem);

        if (permissionListener != null) {
            permissionListener.permissionGranted();
        }

        requestListener.onRequestFinished(permissionItem.permissionArray);
    }

    private void requestPermission() {
        if (permissionWrapper == null) {
            return;
        }

        PermissionListener listener = permissionWrapper.permissionListener;
        PermissionItem item = permissionWrapper.permissionItem;

        if (PermissionUtil.hasSelfPermissions(AppController.getAppContext(),
            item.permissionArray)) {
            onPermissionGranted(item, listener);
        } else {
            ActivityCheckPermission.open(AppController.getAppContext(), item.permissionArray,
                item.rationalText, item.confirmText, item.denyText, item.goSetting);
        }
    }
}
