package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.tangram.bean.DefineBean;

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

    private TextView index;
    private TextView count;

    private void initChileLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_child_layout_define, this);

        index = findViewById(R.id.define_item_index);
        count = findViewById(R.id.define_item_count);
    }

    public void cacheValue(DefineBean bean) {
        if (index != null) {
            index.setText(String.valueOf(bean.index));
        }
        if (count != null) {
            count.setText(String.valueOf(bean.count));
        }
    }

    public void setCount(int data) {
        if (count != null) {
            count.setText(String.valueOf(data));
        }
    }
}
