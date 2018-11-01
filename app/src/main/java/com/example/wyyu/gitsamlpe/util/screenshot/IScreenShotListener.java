package com.example.wyyu.gitsamlpe.util.screenshot;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by wyyu on 2018/10/26.
 **/

public interface IScreenShotListener {

    /**
     * 为一个 Activity 注册截屏监听
     *
     * @param activity 待注册 Activity
     */
    void unregisterListener(@NonNull Activity activity);

    /**
     * 取消一个 Activity 的截屏监听
     *
     * @param activity Activity
     */
    void registerListener(@NonNull Activity activity);
}
