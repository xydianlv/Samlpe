package com.example.wyyu.gitsamlpe.framework.viewpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class UltraScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.94f;

    @Override
    public void transformPage(View page, float position) {
        final int pageWidth = page.getWidth();
        final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

        if (position < 0) { // [-1,0]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else if (position == 0) {
            page.setScaleX(1);
            page.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        }
    }
}