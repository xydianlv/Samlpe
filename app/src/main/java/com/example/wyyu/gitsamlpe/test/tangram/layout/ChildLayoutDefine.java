package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class ChildLayoutDefine extends FrameLayout {

    public ChildLayoutDefine(@NonNull Context context) {
        this(context, null);
    }

    public ChildLayoutDefine(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildLayoutDefine(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChileLayout();
    }

    private void initChileLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_child_layout_define, this);
    }
}
