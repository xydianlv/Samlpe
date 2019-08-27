package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CellLoadLayout extends FrameLayout {

    public CellLoadLayout(@NonNull Context context) {
        this(context, null);
    }

    public CellLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_cell_load, this);
    }
}
