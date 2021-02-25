package com.example.wyyu.gitsamlpe.test.keyboard;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.util.ViewUtil;

/**
 * Created by wyyu on 2018/11/2.
 **/

public class KPSwitchConflictUtilTest {

    public static void attach(final View panelLayout,
                              /* Nullable **/final View switchPanelKeyboardBtn,
                              /* Nullable **/final View focusView) {
        attach(panelLayout, switchPanelKeyboardBtn, focusView, null);
    }

    public static void attach(final View panelLayout,
                              /* Nullable **/final View switchPanelKeyboardBtn,
                              /* Nullable **/final View focusView,
                              /* Nullable **/
        final KPSwitchConflictUtilTest.SwitchClickListener switchClickListener) {
        final Activity activity = (Activity) panelLayout.getContext();

        if (switchPanelKeyboardBtn != null) {
            switchPanelKeyboardBtn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    final boolean switchToPanel = switchPanelAndKeyboard(panelLayout, focusView);
                    if (switchClickListener != null) {
                        switchClickListener.onClickSwitch(switchToPanel);
                    }
                }
            });
        }

        if (isHandleByPlaceholder(activity)) {
            focusView.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility") @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        /*
                         * Show the fake empty keyboard-same-height panel to fix the conflict when
                         * keyboard going to show.
                         * @see KPSwitchConflictUtil#showKeyboard(View, View)
                         */
                        panelLayout.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
        }
    }

    public static void attach(final View panelLayout, final View focusView,
        KPSwitchConflictUtilTest.SubPanelAndTrigger... subPanelAndTriggers) {
        attach(panelLayout, focusView, null, subPanelAndTriggers);
    }

    public static void attach(final View panelLayout, final View focusView,
        final KPSwitchConflictUtilTest.SwitchClickListener switchClickListener,
        KPSwitchConflictUtilTest.SubPanelAndTrigger... subPanelAndTriggers) {
        final Activity activity = (Activity) panelLayout.getContext();

        for (KPSwitchConflictUtilTest.SubPanelAndTrigger subPanelAndTrigger : subPanelAndTriggers) {

            bindSubPanel(subPanelAndTrigger, subPanelAndTriggers, focusView, panelLayout,
                switchClickListener);
        }

        if (KPSwitchConflictUtilTest.isHandleByPlaceholder(activity)) {
            focusView.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility") @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        panelLayout.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
        }
    }

    static class SubPanelAndTrigger {
        final View subPanelView;
        final View triggerView;

        SubPanelAndTrigger(View subPanelView, View triggerView) {
            this.subPanelView = subPanelView;
            this.triggerView = triggerView;
        }
    }

    private static void showPanel(final View panelLayout) {
        final Activity activity = (Activity) panelLayout.getContext();
        panelLayout.setVisibility(View.VISIBLE);
        if (activity.getCurrentFocus() != null) {
            KeyboardUtil.hideKeyboard(activity.getCurrentFocus());
        }
    }

    private static void showKeyboard(final View panelLayout, final View focusView) {
        final Activity activity = (Activity) panelLayout.getContext();

        KeyboardUtil.showKeyboard(focusView);
        if (isHandleByPlaceholder(activity)) {
            panelLayout.setVisibility(View.INVISIBLE);
        }
    }

    private static boolean switchPanelAndKeyboard(final View panelLayout, final View focusView) {
        boolean switchToPanel = panelLayout.getVisibility() != View.VISIBLE;
        if (!switchToPanel) {
            showKeyboard(panelLayout, focusView);
        } else {
            showPanel(panelLayout);
        }

        return switchToPanel;
    }

    static void hidePanelAndKeyboard(final View panelLayout) {
        final Activity activity = (Activity) panelLayout.getContext();

        final View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            KeyboardUtil.hideKeyboard(activity.getCurrentFocus());
            focusView.clearFocus();
        }

        panelLayout.setVisibility(View.GONE);
    }

    public interface SwitchClickListener {
        void onClickSwitch(boolean switchToPanel);
    }

    private static boolean isHandleByPlaceholder(boolean isFullScreen, boolean isTranslucentStatus,
        boolean isFitsSystem) {
        return isFullScreen || (isTranslucentStatus && !isFitsSystem);
    }

    private static boolean isHandleByPlaceholder(final Activity activity) {
        return isHandleByPlaceholder(ViewUtil.isFullScreen(activity),
            ViewUtil.isTranslucentStatus(activity),
            KPSwitchConflictUtilTest.isFitsSystemWindows(activity));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static boolean isFitsSystemWindows(final Activity activity) {
        //noinspection SimplifiableIfStatement
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0).
                getFitsSystemWindows();
        }

        return false;
    }

    private static void bindSubPanel(
        final KPSwitchConflictUtilTest.SubPanelAndTrigger subPanelAndTrigger,
        final KPSwitchConflictUtilTest.SubPanelAndTrigger[] subPanelAndTriggers,
        final View focusView, final View panelLayout,
                                     /* Nullable */
        final KPSwitchConflictUtilTest.SwitchClickListener switchClickListener) {

        final View triggerView = subPanelAndTrigger.triggerView;
        final View boundTriggerSubPanelView = subPanelAndTrigger.subPanelView;

        triggerView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Boolean switchToPanel = null;
                if (panelLayout.getVisibility() == View.VISIBLE) {
                    // panel is visible.

                    if (boundTriggerSubPanelView.getVisibility() == View.VISIBLE) {

                        // bound-trigger panel is visible.
                        // to show keyboard.
                        KPSwitchConflictUtilTest.showKeyboard(panelLayout, focusView);
                        switchToPanel = false;
                    } else {
                        // bound-trigger panel is invisible.
                        // to show bound-trigger panel.
                        showBoundTriggerSubPanel(boundTriggerSubPanelView, subPanelAndTriggers);
                    }
                } else {
                    // panel is gone.
                    // to show panel.
                    KPSwitchConflictUtilTest.showPanel(panelLayout);
                    switchToPanel = true;

                    // to show bound-trigger panel.
                    showBoundTriggerSubPanel(boundTriggerSubPanelView, subPanelAndTriggers);
                }

                if (switchClickListener != null && switchToPanel != null) {
                    switchClickListener.onClickSwitch(switchToPanel);
                }
            }
        });
    }

    private static void showBoundTriggerSubPanel(final View boundTriggerSubPanelView,
        final KPSwitchConflictUtilTest.SubPanelAndTrigger[] subPanelAndTriggers) {
        // to show bound-trigger panel.
        for (KPSwitchConflictUtilTest.SubPanelAndTrigger panelAndTrigger : subPanelAndTriggers) {
            if (panelAndTrigger.subPanelView != boundTriggerSubPanelView) {
                // other sub panel.
                panelAndTrigger.subPanelView.setVisibility(View.GONE);
            }
        }
        boundTriggerSubPanelView.setVisibility(View.VISIBLE);
    }
}
