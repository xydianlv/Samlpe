package com.example.wyyu.gitsamlpe.test.pager.anim;

import android.view.View;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class PageTransformerPreview extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        page.setTranslationX(position < 0.0f ? 0.0f : -page.getWidth() * position);
    }
}
