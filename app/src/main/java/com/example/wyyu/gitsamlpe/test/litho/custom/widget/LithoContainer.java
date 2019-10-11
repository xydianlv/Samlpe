package com.example.wyyu.gitsamlpe.test.litho.custom.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.facebook.litho.LithoView;

/**
 * Created by wyyu on 2019-10-11.
 **/
public class LithoContainer extends FrameLayout {

    public LithoContainer(@NonNull Context context) {
        super(context);
    }

    public LithoContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LithoContainer(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void offsetTopAndBottom(int offset) {
        super.offsetTopAndBottom(offset);

        for (int index = 0; index < getChildCount(); index++) {
            View childView = getChildAt(index);
            if (!(childView instanceof LithoView)) {
                continue;
            }
            LithoView lithoView = (LithoView) childView;
            lithoView.offsetTopAndBottom(0);
        }
    }
}
