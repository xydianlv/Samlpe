package com.example.wyyu.gitsamlpe.framework.toast;

import com.example.wyyu.gitsamlpe.framework.application.AppController;
import android.widget.Toast;
import android.view.View;

/**
 * Created by wyyu on 2018/8/27.
 **/

public class TToast {

    private static Toast toast;

    public static void showShort(String text) {
        if (toast == null) {
            toast = Toast.makeText(AppController.getAppContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            showText(text, Toast.LENGTH_SHORT);
        }
    }

    public static void showLong(String text) {
        if (toast == null) {
            toast = Toast.makeText(AppController.getAppContext(), text, Toast.LENGTH_LONG);
            toast.show();
        } else {
            showText(text, Toast.LENGTH_LONG);
        }
    }

    public static void showViewShort(View view) {
        if (toast == null) {
            toast = new Toast(AppController.getAppContext());
            showView(view, Toast.LENGTH_SHORT);
        } else {
            showView(view, Toast.LENGTH_SHORT);
        }
    }

    public static void showViewLong(View view) {
        if (toast == null) {
            toast = new Toast(AppController.getAppContext());
            showView(view, Toast.LENGTH_LONG);
        } else {
            showView(view, Toast.LENGTH_LONG);
        }
    }

    private static void showText(String text, int duration) {
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }

    private static void showView(View view, int duration) {
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }
}
