package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerOver extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {

        final float rotation = 180.0f * position;

        page.setAlpha(rotation > 90.0f || rotation < -90.0f ? 0.0f : 1.0f);
        page.setPivotX(page.getWidth() * 0.5f);
        page.setPivotY(page.getHeight() * 0.5f);
        page.setScaleX(0.8f);
        page.setScaleY(0.8f);
        page.setRotationY(rotation);
    }
}
