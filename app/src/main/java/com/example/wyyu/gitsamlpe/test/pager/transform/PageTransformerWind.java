package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerWind extends PageTransformerBase {

    private static final float ROT_MOD = -15.0f;

    @Override protected void onTransform(View page, float position) {
        //final float width = page.getWidth();
        //final float rotation = ROT_MOD * position;
        //
        //page.setPivotX(width * 0.5f);
        //page.setPivotY(0.0f);
        //page.setTranslationX(0.0f);
        //page.setRotation(rotation);

        final float width = page.getWidth();
        final float height = page.getHeight();
        final float rotation = ROT_MOD * position * -1.25f;

        page.setPivotX(width * 0.5f);
        page.setPivotY(height);
        page.setRotation(rotation);
    }

    @Override protected boolean isPagingEnabled() {
        return true;
    }
}
