package com.example.wyyu.gitsamlpe.test.permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.util.permission.IPermissionObserver;
import com.example.wyyu.gitsamlpe.util.permission.PermissionCheck;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItem;
import com.example.wyyu.gitsamlpe.util.permission.PermissionListener;
import com.example.wyyu.gitsamlpe.util.permission.PermissionObservable;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class ActivityPermissionTest extends ToolbarActivity implements IPermissionObserver {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_test);

        PermissionObservable.getObservable().attach(this);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        PermissionObservable.getObservable().detach(this);
    }

    private void initActivity() {

        initToolbar("CheckPermission", 0xffffffff, 0xff84919b);

        findViewById(R.id.check_permission_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        PermissionItem permissionItem =
            new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION).rationalText(
                "开启存储权限才能选图和视频").confirmText("确认").denyText("取消").goSetting(true);
        PermissionCheck.getInstance().check(permissionItem, new PermissionListener() {
            @Override public void permissionGranted() {
                UToast.showShort(ActivityPermissionTest.this, "您已获取该权限");
            }

            @Override public void permissionDenied() {
                UToast.showShort(ActivityPermissionTest.this, "权限获取失败");
            }
        });
    }

    @Override public void permissionGranted() {
        UToast.showShort(ActivityPermissionTest.this, "您已获取该权限");
    }

    @Override public void permissionDenied() {
        UToast.showShort(ActivityPermissionTest.this, "权限获取失败");
    }
}
