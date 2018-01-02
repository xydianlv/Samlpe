package com.example.wyyu.gitsamlpe.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;

import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.util.http.HttpGet;
import com.example.wyyu.gitsamlpe.util.http.IHttpCallBack;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    // 判读某一组权限是否都有注册
    public static boolean lackPermission(Context context, String... permissionArray) {

        for (String permission : permissionArray) {
            if (lackPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    // 判读是否缺少某权限
    private static boolean lackPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
