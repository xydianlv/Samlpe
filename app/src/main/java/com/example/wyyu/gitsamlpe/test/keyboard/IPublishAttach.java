package com.example.wyyu.gitsamlpe.test.keyboard;

import android.app.Activity;
import androidx.annotation.NonNull;

/**
 * Created by wyyu on 2018/11/5.
 **/

public interface IPublishAttach {

    /**
     * 内容发送控件绑定到 Activity
     *
     * @param activity Activity
     */
    void detachFromActivity(@NonNull Activity activity);

    /**
     * 解除控件绑定
     *
     * @param activity Activity
     */
    void attachToActivity(@NonNull Activity activity);
}
