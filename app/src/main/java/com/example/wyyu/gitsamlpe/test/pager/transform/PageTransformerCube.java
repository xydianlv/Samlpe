package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerCube extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        //page.setPivotX(position > 0 ? 0 : page.getWidth());
        //page.setPivotY(0);
        //page.setRotationY(-90f * position);

        page.setPivotX(position < 0.0f ? page.getWidth() : 0.0f);
        page.setPivotY(page.getHeight() * 0.5f);
        // 转角的角度，值越小，角度越大
        page.setRotationY(20.0f * position);
    }

    @Override public boolean isPagingEnabled() {
        return true;
    }
}
