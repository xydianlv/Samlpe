package com.example.matisse.internal.utils;

import android.os.Build;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class Platform {

    public static final String MIUI = "miui";
    public static final String EMUI = "EMUI";
    public static final String FLYME = "Flyme";
    public static final String COLOROS = "coloros";
    public static final String VIVO = "vivo";
    public static final String UNKNOW = "unknow";
    public static final String RUNTIME_MIUI = "ro.miui.ui.version.name";
    public static final String RUNTIME_DISPLAY = "ro.build.display.id";
    public static final String RUNTIME_OPPO = "ro.build.version.opporom";
    public static final String RUNTIME_VIVO = "ro.product.manufacturer";

    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isMIUI() {
        return MIUI.equalsIgnoreCase(getRomProperty(RUNTIME_MIUI));
    }

    public static String getRomInfo() {
        String romInfo = null;
        if (!TextUtils.isEmpty(getRomProperty(RUNTIME_MIUI))) {
            romInfo = MIUI;
        } else if (!TextUtils.isEmpty(getRomProperty(RUNTIME_OPPO))) {
            romInfo = COLOROS;
        } else if (getRomProperty(RUNTIME_DISPLAY).contains(EMUI)) {
            romInfo = EMUI;
        } else if (getRomProperty(RUNTIME_DISPLAY).contains(FLYME)) {
            romInfo = FLYME;
        } else if(getRomProperty(RUNTIME_VIVO).contains(VIVO)) {
            romInfo = VIVO;
        } else {
            romInfo = UNKNOW;
        }
        return romInfo;
    }

    private static String getRomProperty(String prop) {
        String line = "";
        BufferedReader reader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("getprop " + prop);
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (p != null) {
                p.destroy();
            }
        }
        return line;
    }
}
