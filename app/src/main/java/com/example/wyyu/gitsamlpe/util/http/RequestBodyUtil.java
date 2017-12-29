package com.example.wyyu.gitsamlpe.util.http;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class RequestBodyUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FILE = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * @ param json  将一个 JSon 串转为 RequestBody
     * @ return  返回一个正常的 RequestBody 对象
     */
    public static RequestBody getRequestBody(String json) {
        return RequestBody.create(JSON, json);
    }

    /**
     * @ param file  将一个 File 对象转为 RequestBody 对象
     * @ return  返回一个正常的 RequestBody 对象
     */
    public static RequestBody getRequestBody(File file) {
        return RequestBody.create(FILE, file);
    }

    /**
     * @ param dataMap  将一个 Map 对象转为 RequestBody 对象，亦可用来传输 表单
     * @ return  返回一个正常的 RequestBody 对象
     */
    public static RequestBody getRequestBody(Map<String, String> dataMap) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : dataMap.keySet()) {
            builder.add(key, dataMap.get(key));
        }
        return builder.build();
    }

}
