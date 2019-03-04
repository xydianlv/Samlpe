package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/3/1.
 **/

public class BaseHolderView extends LinearLayout {

    public BaseHolderView(@NonNull Context context) {
        this(context, null);
    }

    public BaseHolderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseHolderView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBaseHolderView();
    }

    private HolderContentView holderContentView;
    private HolderHeadView holderHeadView;
    private HolderFootView holderFootView;

    private void initBaseHolderView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_base_holder_view, this);

        holderContentView = findViewById(R.id.base_holder_content);
        holderHeadView = findViewById(R.id.base_holder_head);
        holderFootView = findViewById(R.id.base_holder_foot);
    }

    public void setMultiData(MultiData multiData) {
        if (multiData == null) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);

            if (holderHeadView != null) {
                holderHeadView.setHeadValue(multiData);
            }
            if (holderContentView != null) {
                holderContentView.setContentValue(multiData);
            }
            if (holderFootView != null) {
                holderFootView.setFootValue(multiData);
            }
        }
    }
}
