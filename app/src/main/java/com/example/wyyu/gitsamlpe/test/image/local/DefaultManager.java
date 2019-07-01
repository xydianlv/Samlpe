package com.example.wyyu.gitsamlpe.test.image.local;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import com.example.wyyu.gitsamlpe.framework.application.AppController;

/**
 * Created by wyyu on 2019-07-01.
 **/

class DefaultManager {

    static Drawable getDefaultPlaceHolder() {
        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        return ta.getDrawable(0);
    }
}
