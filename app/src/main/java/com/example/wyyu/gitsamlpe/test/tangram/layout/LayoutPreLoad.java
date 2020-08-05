package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-09-25.
 **/

public class LayoutPreLoad extends FrameLayout {

    public LayoutPreLoad(@NonNull Context context) {
        this(context, null);
    }

    public LayoutPreLoad(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayoutPreLoad(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_pre_load, this);
    }
}