package com.example.wyyu.gitsamlpe.util;

import android.content.Context;
import android.text.format.Formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class CommonUtil {

    public static String getFileDateTime(long changeTime) {
        Date date = new Date(changeTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String formatFileSize(Context context, long fileS) {
        return Formatter.formatFileSize(context, fileS).replace("", "");
    }
}
