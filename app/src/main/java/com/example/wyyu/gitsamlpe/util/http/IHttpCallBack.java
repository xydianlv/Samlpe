package com.example.wyyu.gitsamlpe.util.http;

import java.io.IOException;
import okhttp3.Response;

/**
 * Created by wyyu on 2017/12/28.
 **/

public interface IHttpCallBack {

    void onFailure(IOException exception);

    void onResponse(Response response);
}
