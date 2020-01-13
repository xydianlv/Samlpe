package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/
public class PageTransformerCrimp extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        page.setPivotX(position < 0 ? 0.0f : page.getWidth());
        page.setScaleX(position < 0 ? 1.0f + position : 1.0f - position);
    }
}
