package com.example.wyyu.gitsamlpe.test.slide.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/1/3.
 **/

public class LayoutReview extends LinearLayout {

    public LayoutReview(Context context) {
        super(context);
        initLayout();
    }

    public LayoutReview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LayoutReview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_review, this);
    }
}
