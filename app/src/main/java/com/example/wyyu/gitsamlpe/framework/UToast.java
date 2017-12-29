package com.example.wyyu.gitsamlpe.framework;

import android.content.Context;
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

    private static boolean checkToast(Context context) {

        if (toast == null) {
            return true;
        } else if (!context.equals(CONTEXT_S[0])) {
            CONTEXT_S[0] = context;
            toast.cancel();

            return true;
        } else {
            return false;
        }
    }

    private static void showText(String text, int duration) {
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }
}