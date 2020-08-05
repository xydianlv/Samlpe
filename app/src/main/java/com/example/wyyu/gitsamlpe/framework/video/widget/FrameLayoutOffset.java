package com.example.wyyu.gitsamlpe.framework.video.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class FrameLayoutOffset extends FrameLayout {

    public FrameLayoutOffset(@NonNull Context context) {
        super(context);
    }

    public FrameLayoutOffset(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayoutOffset(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void offsetTopAndBottom(int offset) {
        super.offsetTopAndBottom(offset);

        for (int index = 0; index < getChildCount(); index++) {
            getChildAt(index).offsetTopAndBottom(0);
        }
    }

    @Override public void offsetLeftAndRight(int offset) {
        super.offsetLeftAndRight(offset);

        for (int index = 0; index < getChildCount(); index++) {
            getChildAt(index).offsetLeftAndRight(0);
        }
    }
}
