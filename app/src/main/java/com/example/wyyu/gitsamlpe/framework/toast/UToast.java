package com.example.wyyu.gitsamlpe.framework.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class UToast {

    private static final Context[] CONTEXT_S = new Context[1];
    private static Toast toast;

    public static void showShort(Context context, String text) {
        if (checkToast(context)) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            showText(text, Toast.LENGTH_SHORT);
        }
    }

    public static void showLong(Context context, String text) {
        if (checkToast(context)) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        } else {
            showText(text, Toast.LENGTH_LONG);
        }
    }

    public static void showViewShort(Context context, View view) {
        if (checkToast(context)) {
            toast = new Toast(context);
            showView(view, Toast.LENGTH_SHORT);
        } else {
            showView(view, Toast.LENGTH_SHORT);
        }
    }

    public static void showViewLong(Context context, View view) {
        if (checkToast(context)) {
            toast = new Toast(context);
            showView(view, Toast.LENGTH_LONG);
        } else {
            showView(view, Toast.LENGTH_LONG);
        }
    }

    private static boolean checkToast(Context context) {
        if (toast == null) {
            return true;
        }
        if (!context.equals(CONTEXT_S[0])) {
            CONTEXT_S[0] = context;
            toast.cancel();
            return true;
        }
        return false;
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