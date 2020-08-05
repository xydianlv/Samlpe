package com.example.wyyu.gitsamlpe.util.http;

import androidx.annotation.NonNull;

import com.example.wyyu.gitsamlpe.framework.ULog;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class HttpPost {

    /**
     * @param url  待访问服务器的 URL 地址
     * @param cookie  cookie 值，若此次连接无 cookie ，传入 "" 即可
     * @param requestBody  post 操作传输的主要数据，可通过 RequestBodyUtil 将 待传输数据 转为 RequestBody
     *
     * @ return 同步 post 中，需要在 post 完成后返回从服务器 获取到的数据
     */
    public static String syncPost(String url, String cookie, RequestBody requestBody) {

        OkHttpClient client = HttpClientHolder.getClientHolder().getClient();

        Request request
                = new Request.Builder()
                .addHeader("cookie", cookie)
                .url(url)
                .post(requestBody)
                .build();
        try {
            return client.newCall(request).execute().body().toString();
        } catch (IOException exception) {
            ULog.show(exception.getMessage());
        }

        return "";
    }

    /**
     * @param url  待访问服务器的 URL 地址
     * @param cookie  cookie 值，若此次连接无 cookie ，传入 "" 即可
     * @param requestBody  post 操作传输的主要数据，可通过 RequestBodyUtil 将 待传输数据 转为 RequestBody
     * @param callBack  异步 post 中，将获取到的 数据 通过该回调返回给 调用者
     */
    public static void asyncPost(String url, String cookie, RequestBody requestBody, final IHttpCallBack callBack) {

        OkHttpClient client = HttpClientHolder.getClientHolder().getClient();

        Request request
                = new Request.Builder()
                .addHeader("cookie", cookie)
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    callBack.onResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
