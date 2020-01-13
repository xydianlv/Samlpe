package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerSimple extends PageTransformerBase {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override protected void onTransform(View page, float position) {
        if (position >= -1 || position <= 1) {
            // Modify the default slide transition to shrink the page as well
            final float height = page.getHeight();
            final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            final float vertMargin = height * (1 - scaleFactor) / 2;
            final float horzMargin = page.getWidth() * (1 - scaleFactor) / 2;

            // Center vertically
            page.setPivotY(0.5f * height);

            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            page.setAlpha(
                MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }
}
