package com.example.wyyu.gitsamlpe.test.pager.vertical;

import android.view.View;
import com.example.wyyu.gitsamlpe.test.pager.transform.PageTransformerBase;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class VerticalTransformer extends PageTransformerBase {

    @Override protected void onTransform(View page, float position) {
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            alpha = position + 1;
        }
        page.setAlpha(alpha);
        float transX = page.getWidth() * -position;
        page.setTranslationX(transX);
        float transY = position * page.getHeight();
        page.setTranslationY(transY);
    }
}