package com.example.wyyu.gitsamlpe.framework.volume;

import android.app.Activity;

/**
 * Created by wyyu on 2018/8/11.
 **/

public interface IVolumeSeekView {

    void refreshProgress(int progress);

    void show(Activity activity, int max, int progress);

    void hide();
}
