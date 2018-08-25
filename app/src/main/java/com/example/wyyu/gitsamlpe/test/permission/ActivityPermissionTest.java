package com.example.wyyu.gitsamlpe.test.permission;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.util.permission.IPermissionObserver;
import com.example.wyyu.gitsamlpe.util.permission.PermissionCheck;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItemCreator;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItemKey;
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
        PermissionCheck.getInstance().check(PermissionItemCreator.createStoragePermission());
    }

    @SuppressLint("SwitchIntDef") @Override public void permissionGranted(int itemKey) {
        switch (itemKey) {
            case PermissionItemKey.拍照:
                UToast.showShort(ActivityPermissionTest.this, "您已获取该权限");
                break;
        }
    }

    @SuppressLint("SwitchIntDef") @Override public void permissionDenied(int itemKey) {
        switch (itemKey) {
            case PermissionItemKey.拍照:
                UToast.showShort(ActivityPermissionTest.this, "权限获取失败");
                break;
        }
    }
}
