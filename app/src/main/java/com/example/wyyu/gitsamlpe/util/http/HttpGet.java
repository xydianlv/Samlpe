package com.example.wyyu.gitsamlpe.util.http;

import android.support.annotation.NonNull;

import com.example.wyyu.gitsamlpe.framework.ULog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class HttpGet {

    /**
     * @param url  待访问服务器的 URL 地址
     * @param cookie  cookie 值，若此次连接无 cookie ，传入 "" 即可
     *
     * @ return  同步 get 中，获取到的 数据值
     */
    public static String syncGet(String url, String cookie) {

        OkHttpClient client = HttpClientHolder.getClientHolder().getClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie", cookie)
                .build();
        try {
            return client.newCall(request).execute().toString();
        } catch (IOException exception) {
            ULog.show(exception.getMessage());
        }

        return "";
    }

    /**
     * @param url  待访问服务器的 URL 地址
     * @param cookie  cookie 值，若此次连接无 cookie ，传入 "" 即可
     * @param callBack  异步 get 中，将获取到的 数据值 回调给 调用者
     */
    public static void asyncGet(String url, String cookie, final IHttpCallBack callBack) {

        OkHttpClient client = HttpClientHolder.getClientHolder().getClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie", cookie)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                callBack.onResponse(response);
            }
        });
    }

}
