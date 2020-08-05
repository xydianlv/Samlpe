package com.example.wyyu.gitsamlpe.framework.viewpager.transformer;

import androidx.viewpager.widget.ViewPager;
import android.view.View;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class UltraDepthScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_ROTATION = 30;

    @Override
    public void transformPage(View view, float position) {
        final float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
        final float rotation = MAX_ROTATION * Math.abs(position);

        if (position <= 0f) {
            view.setTranslationX(view.getWidth() * -position * 0.19f);
            view.setPivotY(0.5f * view.getHeight());
            view.setPivotX(0.5f * view.getWidth());
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setRotationY(rotation);
        } else if (position <= 1f) {
            view.setTranslationX(view.getWidth() * -position * 0.19f);
            view.setPivotY(0.5f * view.getHeight());
            view.setPivotX(0.5f * view.getWidth());
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setRotationY(-rotation);
        }
    }
}