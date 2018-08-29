package com.example.wyyu.gitsamlpe.framework.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class AppController extends Application {

    @SuppressLint("StaticFieldLeak") private static Application application;

    @Override public void onCreate() {
        super.onCreate();
        application = this;

        Fresco.initialize(getAppContext());
        UIUtils.init(getAppContext());
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }
}
