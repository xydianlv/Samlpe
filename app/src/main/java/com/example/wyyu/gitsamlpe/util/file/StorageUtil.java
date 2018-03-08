package com.example.wyyu.gitsamlpe.util.file;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class StorageUtil {

    public static String getExternalStoragePath() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String getInnerStoragePath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    public static String getFileDateTime(long changeTime) {
        Date date = new Date(changeTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String formatFileSize(Context context, long fileS) {
        return Formatter.formatFileSize(context, fileS).replace("", "");
    }

    public static String getRandomString(int length) {

        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder stringBuffer = new StringBuilder();
        int index;

        for (int i = 0; i < length; i++) {
            index = random.nextInt(base.length());
            stringBuffer.append(base.charAt(index));
        }

        return stringBuffer.toString();
    }
}
