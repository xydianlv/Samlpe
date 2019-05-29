package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wyyu on 2018/4/12.
 **/

class UIShowThemeManager {

    static void showDialogActivity(Context context) {
        showTargetActivity(context, ActivityDialogTheme.class);
    }

    static void showWallPaperActivity(Context context) {
        showTargetActivity(context, ActivityWallPaperTheme.class);
    }

    static void showTranslucentActivity(Activity context) {
        showTargetActivity(context, ActivityTranslucentActivity.class);
    }

    static void showPanelActivity(Context context) {
        showTargetActivity(context, ActivityPanelTheme.class);
    }

    static void showNoDisplayActivity(Context context) {
        showTargetActivity(context, ActivityDisplayTheme.class);
    }

    static void showBottomActivity(Context context) {
        showTargetActivity(context, ActivityFromBottom.class);
    }

    static void showStatusBarActivity(Context context) {
        showTargetActivity(context, ActivityTransparentStatus.class);
    }

    static void showSwipeBackActivity(Context context) {
        ActivitySwipeRight.open(context);
    }

    static void showResultTestActivity(Activity activity, int requestCode) {
        ActivityResultTest.openForResult(activity, requestCode);
    }

    private static void showTargetActivity(Context context, Class activity) {
        context.startActivity(new Intent(context, activity));
    }
}
