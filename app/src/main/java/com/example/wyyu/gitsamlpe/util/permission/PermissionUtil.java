package com.example.wyyu.gitsamlpe.util.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.PermissionChecker;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/8/25.
 **/

class PermissionUtil {

    static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    static boolean hasSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (isOverMarshmallow() && permission.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                if (!Settings.canDrawOverlays(context)) {
                    return false;
                }
            } else if (isOverMarshmallow() && permission.equals(
                Manifest.permission.WRITE_SETTINGS)) {
                if (!Settings.System.canWrite(context)) {
                    return false;
                }
            } else if (PermissionChecker.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        if (!isOverMarshmallow()) {
            return denyPermissions;
        }
        for (String value : permission) {
            if (isOverMarshmallow() && value.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                if (!Settings.canDrawOverlays(activity)) {
                    denyPermissions.add(value);
                }
            } else if (isOverMarshmallow() && value.equals(Manifest.permission.WRITE_SETTINGS)) {
                if (!Settings.System.canWrite(activity)) {
                    denyPermissions.add(value);
                }
            } else if (PermissionChecker.checkSelfPermission(activity, value)
                != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }
}
