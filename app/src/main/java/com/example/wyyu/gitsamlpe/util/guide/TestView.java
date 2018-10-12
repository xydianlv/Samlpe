package com.example.wyyu.gitsamlpe.util.guide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/10/12.
 **/

public class TestView extends FrameLayout {

    public TestView(@NonNull Context context) {
        this(context, null);
    }

    public TestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTestView();
    }

    private void initTestView() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_test_view, this);
    }
}
