package com.example.wyyu.gitsamlpe.util.location;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.util.CommonUtil;
import com.example.wyyu.gitsamlpe.util.http.HttpGet;
import com.example.wyyu.gitsamlpe.util.http.IHttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class LocationUtil {

    // 注册一个位置信息观察者到被观察者，当位置信息发生变动时进行更新
    public static void registerToObservable(Observer observer) {
        Observable.getObservable().attach(observer);
    }

    // 注销位置监听
    public static void detachFromObservable(Observer observer) {
        Observable.getObservable().detach(observer);
    }

    // 只负责处理权限问题，调用该方法的 Activity 需重写 onRequestPermissionsResult 方法
    public static void checkLocationPermission(Activity activity) {

        if (lackPermission(activity)) {
            requestLocationPermissions(activity);
        } else {
            LocationProvider.getLocationData(activity);
        }
    }

    // 判断是否拥有位置获取权限
    static boolean lackPermission(Context context) {
        return CommonUtil.lackPermission(context, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void requestLocationPermissions(Activity activity) {

        // 如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true
        // 如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false
        // activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);

        // activity 中自带的方法，用户调用 requestPermission 后在系统弹出的 dialog 上确定点击选项后立马执行的回调方法
        // 根据用户的点击选择需要做的后续操作需要在 Activity 中重写该方法进行操作
        // activity.onRequestPermissionsResult();

        if (!lackPermission(activity)) return;

        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 47);
    }

    private static final String URL_PRE = "http://gc.ditu.aliyun.com/regeocoding?l=";

    public static void getCityFromLocation(double latitude, double longitude, final Handler handler) {

        final String url = URL_PRE + latitude + "," + longitude + "&type=100";

        HttpGet.asyncGet(url, "", new IHttpCallBack() {
            @Override
            public void onFailure(IOException exception) {
                ULog.show("Location -> exception ：" + exception);
            }

            @Override
            public void onResponse(Response response) throws IOException, JSONException {
                Message message = Message.obtain(handler);
                message.obj = getCityFromJson(response.body().string());
                handler.sendMessage(message);
            }
        });
    }

    private static String getCityFromJson(String json) {

        String city = "";

        try {
            JSONObject jsonObject = new JSONObject(new JSONObject(json).optJSONArray("addrList").optString(0));
            city = jsonObject.optString("admName") + "," + jsonObject.optString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return city;
    }

}
