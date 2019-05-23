package com.example.wyyu.gitsamlpe.test.bubbling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wyyu on 2019/5/20.
 **/

public class BubblingView extends View {

    public BubblingView(Context context) {
        super(context);
    }

    public BubblingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BubblingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
