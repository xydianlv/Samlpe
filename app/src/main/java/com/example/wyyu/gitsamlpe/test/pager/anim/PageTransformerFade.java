package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerFade extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        final float scale = 1.0f + Math.abs(position);
        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setPivotX(page.getWidth() * 0.5f);
        page.setPivotY(page.getHeight() * 0.5f);
        page.setAlpha(position < -1.0f || position > 1.0f ? 0.0f : 1.0f - (scale - 1.0f));
        if (position == -1.0f) {
            page.setTranslationX(page.getWidth() * -1.0f);
        }
    }
}
