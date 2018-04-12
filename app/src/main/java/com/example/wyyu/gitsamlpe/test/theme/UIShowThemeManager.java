package com.example.wyyu.gitsamlpe.test.theme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

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

    private static void showTargetActivity(Context context, Class activity) {
        context.startActivity(new Intent(context, activity));
    }
}
