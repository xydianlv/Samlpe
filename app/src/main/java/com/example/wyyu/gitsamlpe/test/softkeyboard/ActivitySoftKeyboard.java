package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;
import com.example.wyyu.gitsamlpe.util.SoftInputMonitor;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class ActivitySoftKeyboard extends ToolbarActivity implements SoftInputMonitor.Listener {

    @BindView(R.id.soft_test_touch_layout) TouchListenerLayout touchLayout;
    @BindView(R.id.soft_test_publish_view) BottomPublishView publishView;

    private SoftInputMonitor softInputMonitor;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_keyboard);

        initActivity();
    }

    @Override protected void onResume() {
        super.onResume();
        if (softInputMonitor != null) {
            softInputMonitor.setListener(this);
            softInputMonitor.startMonitoring(this);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (softInputMonitor != null) {
            softInputMonitor.setListener(null);
            softInputMonitor.stopMonitoring();
        }
    }

    private void initActivity() {

        initToolbar("SoftKeyboard", 0xffffffff, 0xff84919b);
        //isKeyboardShowing = false;

        touchLayout.setOnPressListener(new OnPressListenerAdapter() {
            @Override public void onPressDown() {
                super.onPressDown();
                if (publishView != null) {
                    publishView.hideInfoView();
                }
            }
        });

        softInputMonitor = new SoftInputMonitor();
    }

    @OnClick({
        R.id.soft_test_switch_custom, R.id.soft_test_open_other, R.id.soft_test_dialog,
        R.id.soft_test_switch
    }) public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.soft_test_switch_custom:
                ActivityCustomSwitchPanel.open(ActivitySoftKeyboard.this);
                break;
            case R.id.soft_test_open_other:
                ActivityShowKeyboard.open(ActivitySoftKeyboard.this);
                break;
            case R.id.soft_test_dialog:
                showBottomSheetDialog();
                break;
            case R.id.soft_test_switch:
                ActivitySwitchPanelTest.open(ActivitySoftKeyboard.this);
                break;
        }
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        View contentView =
            LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_dialog_test, null);
        dialog.setContentView(contentView);

        dialog.show();
    }

    @Override public void onSoftInputVisibilityChanged(boolean visible, int softInputHeight,
        int statusBarHeight) {
        if (visible) {
            publishView.showInfoView();
        } else {
            publishView.hideInfoView();
        }
    }
}
