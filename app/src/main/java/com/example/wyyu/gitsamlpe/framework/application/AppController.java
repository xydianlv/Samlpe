package com.example.wyyu.gitsamlpe.framework.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class AppController extends Application {

    @SuppressLint("StaticFieldLeak") private static Application application;

    @Override public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }
}
