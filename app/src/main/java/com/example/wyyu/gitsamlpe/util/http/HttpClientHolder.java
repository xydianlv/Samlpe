package com.example.wyyu.gitsamlpe.util.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wyyu on 2017/12/28.
 **/

class HttpClientHolder {

    private OkHttpClient httpClient;

    /**
     * connectTimeout ： 连接时限，超过 3000 毫秒 返回连接错误
     * writeTimeout ： 写时限，超过 10000 毫秒 返回写错误
     * readTimeout ： 读时限，超过 10000 毫秒 返回读错误
     * retryOnConnectionFailure ： 连接失败后，是否需要继续尝试连接
     **/
    private HttpClientHolder() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }

    static HttpClientHolder getClientHolder() {
        return HttpClientHolderCache.getClientHolder();
    }

    OkHttpClient getClient() {
        return httpClient;
    }

    private static class HttpClientHolderCache {
        private static HttpClientHolder clientHolder = new HttpClientHolder();

        static HttpClientHolder getClientHolder() {
            return clientHolder == null ? new HttpClientHolder() : clientHolder;
        }
    }

}
