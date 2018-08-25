package com.example.wyyu.gitsamlpe.util.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.framework.dialog.PermissionDialog;
import com.example.wyyu.gitsamlpe.framework.dialog.SDAlertDialog;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class ActivityCheckPermission extends AppCompatActivity {

    public static final int REQ_CODE_REQUEST_SYSTEM_ALERT_WINDOW = 120;
    public static final int REQ_CODE_REQUEST_WRITE_SETTING = 121;

    public static final String KEY_PERMISSION_ARRAY = "permissionArray";
    public static final String KEY_RATIONALE_TEXT = "rationale_text";
    public static final String KEY_CONFIRM_TEXT = "confirm_text";
    public static final String KEY_GO_SETTING = "go_setting";
    public static final String KEY_DENY_TEXT = "deny_text";

    private static final String VALUE_DENIED_PERMISSION = "你拒绝了该权限";

    public static void open(Context context, String[] permissionArray, String rationaleText,
        String confirmText, String denyText, boolean goSetting) {
        Intent intent = new Intent(context, ActivityCheckPermission.class);
        intent.putExtra(KEY_PERMISSION_ARRAY, permissionArray);
        intent.putExtra(KEY_RATIONALE_TEXT, rationaleText);
        intent.putExtra(KEY_CONFIRM_TEXT, confirmText);
        intent.putExtra(KEY_DENY_TEXT, denyText);
        intent.putExtra(KEY_GO_SETTING, goSetting);
        context.startActivity(intent);
    }

    private PermissionRequestListener requestListener;
    private PermissionListener permissionListener;

    private String[] permissionArray;
    private String rationaleMessage;
    private String packageName;
    private String confirmText;
    private String denyMessage;

    private boolean hasRequestedSystemAlertWindow;
    private String permissionSystemAlertWindow;
    private boolean hasRequestedWriteSettings;
    private String permissionWriteSettings;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivityWindow();
        initBasicValue();
        dispatchIntent();
    }

    private void initActivityWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.alpha = 0.f;
        window.setAttributes(params);
    }

    private void initBasicValue() {

        hasRequestedSystemAlertWindow = false;
        hasRequestedWriteSettings = false;
        permissionSystemAlertWindow = null;
        permissionWriteSettings = null;

        packageName = getPackageName();
    }

    private void dispatchIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        permissionArray = intent.getStringArrayExtra(KEY_PERMISSION_ARRAY);
        rationaleMessage = intent.getStringExtra(KEY_RATIONALE_TEXT);
        confirmText = intent.getStringExtra(KEY_CONFIRM_TEXT);
        denyMessage = intent.getStringExtra(KEY_DENY_TEXT);

        if (intent.getBooleanExtra(KEY_GO_SETTING, false)) {
            new PermissionDialog(this, Arrays.asList(permissionArray)).show();
        } else {
            checkPermissionArray(false);
        }
    }

    private void checkPermissionArray(boolean isRequested) {

        List<String> needPermissionList =
            PermissionUtil.findDeniedPermissions(this, permissionArray);

        boolean showRationale = false;
        for (String permission : needPermissionList) {
            if (!hasRequestedSystemAlertWindow && permission.equals(
                Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                permissionSystemAlertWindow = Manifest.permission.SYSTEM_ALERT_WINDOW;
            } else if (!hasRequestedWriteSettings && permission.equals(
                Manifest.permission.WRITE_SETTINGS)) {
                permissionWriteSettings = Manifest.permission.WRITE_SETTINGS;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showRationale = true;
            }
        }

        if (!PermissionUtil.isOverMarshmallow()) {
            permissionGranted();
        } else if (needPermissionList.isEmpty()) {
            permissionGranted();
        } else if (isRequested) {
            permissionDenied();
        } else if (showRationale && !TextUtils.isEmpty(rationaleMessage)) {
            showRationaleDialog(needPermissionList);
        } else {
            requestPermissions(needPermissionList);
        }
    }

    private void permissionGranted() {
        if (permissionListener != null) {
            permissionListener.permissionGranted();
            permissionListener = null;
        }

        if (requestListener != null) {
            requestListener.onRequestFinished(permissionArray);
            requestListener = null;
        }

        PermissionObservable.getObservable().permissionGranted();

        finish();
        overridePendingTransition(0, 0);
    }

    private void permissionDenied() {
        if (permissionListener != null) {
            permissionListener.permissionDenied();
            permissionListener = null;
        }

        if (requestListener != null) {
            requestListener.onRequestFinished(permissionArray);
            requestListener = null;
        }

        PermissionObservable.getObservable().permissionDenied();

        finish();
        overridePendingTransition(0, 0);
    }

    private void showRationaleDialog(final List<String> permissionList) {
        new SDAlertDialog.Builder(this).setMessage(rationaleMessage)
            .setCancel("", null)
            .setConfirm(confirmText, new View.OnClickListener() {
                @Override public void onClick(View v) {
                    requestPermissions(permissionList);
                }
            })
            .show();
    }

    private void showPermissionDenyDialog() {
        UToast.showShort(this,
            TextUtils.isEmpty(denyMessage) ? VALUE_DENIED_PERMISSION : denyMessage);

        permissionDenied();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions(List<String> needPermissions) {
        if (!hasRequestedSystemAlertWindow && !TextUtils.isEmpty(permissionSystemAlertWindow)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + packageName));
            startActivityForResult(intent, REQ_CODE_REQUEST_SYSTEM_ALERT_WINDOW);
        } else if (!hasRequestedWriteSettings && !TextUtils.isEmpty(permissionWriteSettings)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:" + packageName));
            startActivityForResult(intent, REQ_CODE_REQUEST_WRITE_SETTING);
        } else {
            ActivityCompat.requestPermissions(this,
                needPermissions.toArray(new String[needPermissions.size()]),
                PermissionDialog.REQ_CODE_REQUEST_SETTING);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        List<String> deniedPermissionList = new ArrayList<>(permissions.length);
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];

            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissionList.add(permission);
            }
        }
        if (deniedPermissionList.isEmpty()) {
            permissionGranted();
        } else {
            showPermissionDenyDialog();
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PermissionDialog.REQ_CODE_REQUEST_SETTING:
                checkPermissionArray(true);
                break;
            case REQ_CODE_REQUEST_SYSTEM_ALERT_WINDOW:
                hasRequestedSystemAlertWindow = true;
                checkPermissionArray(false);
                break;
            case REQ_CODE_REQUEST_WRITE_SETTING:
                hasRequestedWriteSettings = true;
                checkPermissionArray(false);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
