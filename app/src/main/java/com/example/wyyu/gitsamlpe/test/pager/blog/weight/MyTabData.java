package com.example.wyyu.gitsamlpe.test.pager.blog.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-09-06.
 **/

public class MyTabData extends LinearLayout {

    public MyTabData(Context context) {
        this(context, null);
    }

    public MyTabData(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabData(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyTab();
    }

    private void initMyTab() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_my_tab_data, this);
    }
}
