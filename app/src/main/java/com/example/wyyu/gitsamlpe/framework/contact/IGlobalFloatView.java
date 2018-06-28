package com.example.wyyu.gitsamlpe.framework.contact;

import android.app.Activity;

/**
 * Created by wyyu on 2018/6/28.
 **/

public interface IGlobalFloatView {

    void refreshDownloadValue(float percent);

    void show(Activity activity);

    void hide();
}
