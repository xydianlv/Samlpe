package com.example.wyyu.gitsamlpe.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by wyyu on 2018/6/25.
 **/

public class UIUtils {

    // 像素密度
    private static float pixelDensity;
    // 屏幕高度
    private static int screenHeight;
    // 屏幕宽度
    private static int screenWidth;

    public static void init(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();

        if (wm == null) {
            return;
        }

        if (wm.getDefaultDisplay() == null) {
            pixelDensity = 3;
        } else {
            wm.getDefaultDisplay().getMetrics(metrics);
            pixelDensity = metrics.density;
        }

        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(metrics);
        } else {
            display.getMetrics(metrics);
        }
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        screenHeight = width >= height ? width : height;
        screenWidth = width >= height ? height : width;
    }

    public static int dpToPx(float dp) {
        return Math.round(pixelDensity * dp);
    }

    public static int pxToDp(float px) {
        return Math.round(px / pixelDensity);
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getStatusHeightByDimen(Context context) {
        Resources resources = context.getResources();
        int statusHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusHeight;
    }

    public static int getStatusHeightByDecor(Activity context) {
        Rect rectangle = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
}
