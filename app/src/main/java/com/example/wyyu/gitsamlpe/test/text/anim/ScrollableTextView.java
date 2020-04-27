package com.example.wyyu.gitsamlpe.test.text.anim;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ScrollableTextView extends android.support.v7.widget.AppCompatTextView {

    public ScrollableTextView(Context context) {
        super(context);
    }

    public ScrollableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean isFocused() {
        return true;
    }
}
