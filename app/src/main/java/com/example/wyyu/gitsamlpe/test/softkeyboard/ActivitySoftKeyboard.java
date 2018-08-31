package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;
import com.example.wyyu.gitsamlpe.util.CommonUtil;
import com.example.wyyu.gitsamlpe.util.SoftInputMonitor;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class ActivitySoftKeyboard extends ToolbarActivity implements SoftInputMonitor.Listener {

    //private static final int MINI_KEYBOARD_HEIGHT = CommonUtil.dpToPx(150);

    @BindView(R.id.soft_test_touch_layout) TouchListenerLayout touchLayout;
    @BindView(R.id.soft_test_publish_view) BottomPublishView publishView;

    @BindView(R.id.soft_test_open_other) View openOther;
    @BindView(R.id.soft_test_root_view) View rootView;
    @BindView(R.id.soft_test_dialog) View dialog;

    private SoftInputMonitor softInputMonitor;
    //private boolean isKeyboardShowing;

    //ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener =
    //    new ViewTreeObserver.OnGlobalLayoutListener() {
    //        @Override public void onGlobalLayout() {
    //            if (rootView == null) {
    //                return;
    //            }
    //            boolean fitsSystemWindows = ViewCompat.getFitsSystemWindows(rootView);
    //            boolean isKeyBoarShow;
    //            if (fitsSystemWindows && rootView.getPaddingBottom() > 0) {
    //                isKeyBoarShow = rootView.getPaddingBottom() > MINI_KEYBOARD_HEIGHT;
    //            } else {
    //                isKeyBoarShow = rootView.getRootView().getHeight() - rootView.getHeight()
    //                    > MINI_KEYBOARD_HEIGHT;
    //            }
    //            if (isKeyboardShowing == isKeyBoarShow || publishView == null) {
    //                return;
    //            }
    //            if (isKeyBoarShow) {
    //                publishView.showInfoView();
    //            } else {
    //                publishView.hideInfoView();
    //            }
    //            isKeyboardShowing = isKeyBoarShow;
    //        }
    //    };

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
        //if (rootView != null && rootView.getViewTreeObserver() != null) {
        //    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        //    rootView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        //}
    }

    @Override protected void onPause() {
        super.onPause();
        if (softInputMonitor != null) {
            softInputMonitor.setListener(null);
            softInputMonitor.stopMonitoring();
        }
        //if (rootView != null && rootView.getViewTreeObserver() != null) {
        //    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        //}
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

        openOther.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityShowKeyboard.open(ActivitySoftKeyboard.this);
            }
        });

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        softInputMonitor = new SoftInputMonitor();
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
