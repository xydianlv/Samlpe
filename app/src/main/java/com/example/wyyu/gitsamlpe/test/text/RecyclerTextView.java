package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-01-02.
 **/
public class RecyclerTextView extends FrameLayout {

    public RecyclerTextView(@NonNull Context context) {
        super(context);
        initView();
    }

    public RecyclerTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RecyclerTextView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private RecyclerView textList;

    private void initView() {
    }
}
