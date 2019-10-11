package com.example.wyyu.gitsamlpe.test.litho.custom.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wyyu on 2019-10-11.
 **/

public class LithoContainerL extends LinearLayout {

    public LithoContainerL(Context context) {
        super(context);
    }

    public LithoContainerL(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LithoContainerL(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void offsetTopAndBottom(int offset) {
        super.offsetTopAndBottom(offset);

        for (int index = 0; index < getChildCount(); index++) {
            getChildAt(index).offsetTopAndBottom(0);
        }
    }
}
