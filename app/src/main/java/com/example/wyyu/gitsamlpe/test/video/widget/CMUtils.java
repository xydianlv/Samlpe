package com.example.wyyu.gitsamlpe.test.video.widget;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.math.BigDecimal;
import java.util.Formatter;
import java.util.Locale;
import java.util.Random;

/**
 * Created by wyyu on 2020-07-28.
 **/

public class CMUtils {

    // 正常模式的图片占位色
    public static final int[] THUMB_COLOR = new int[] { 0xfffbebeb, 0xffeaeffb, 0xfffff0e6 };

    public static Drawable getImageHolderDrawable() {
        return new ColorDrawable(
            CMUtils.THUMB_COLOR[new Random().nextInt(CMUtils.THUMB_COLOR.length)]);
    }

    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = timeMs / 1000;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        int hours = (int) (totalSeconds / 3600);
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static  String getVideoFormatDurationBy(long timeMs) {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder, Locale.getDefault());
        long totalSeconds = timeMs / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        builder.setLength(0);
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String getNumStyle(int number) {
        String prev = number >= 0 ? "" : "-";
        String followedNum = Math.abs(number) + "";
        int absNumber = Math.abs(number);
        if (absNumber >= 10000) {
            float count = (float) absNumber / 10000;
            BigDecimal bd = new BigDecimal(count);
            int decimalPlaces = 1;
            bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
            followedNum = bd.doubleValue() +"万";
        }
        return prev + followedNum;
    }
}
