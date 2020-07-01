package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerScaleCenter extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        final float scale = position < 0.0f ? position + 1.0f : Math.abs(1.0f - position);
        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setPivotX(page.getWidth() * 0.5f);
        page.setPivotY(page.getHeight() * 0.5f);
        page.setAlpha(position < -1.0f || position > 1.0f ? 0.0f : 1.0f - (scale - 1.0f));
    }
}
