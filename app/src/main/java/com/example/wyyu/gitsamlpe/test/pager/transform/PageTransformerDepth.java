package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerDepth extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        final float height = page.getHeight();
        final float width = page.getWidth();
        final float scale = min(position > 0.0f ? 1.0f : Math.abs(1.0f + position), 0.5f);

        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setPivotX(width * 0.5f);
        page.setPivotY(height * 0.5f);
        page.setTranslationX(position > 0.0f ? width * position : -width * position * 0.25f);
    }
}
