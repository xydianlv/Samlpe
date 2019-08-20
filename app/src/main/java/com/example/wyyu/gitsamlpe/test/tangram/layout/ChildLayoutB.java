package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.tangram.bean.TestBean;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

/**
 * Created by wyyu on 2019-08-20.
 **/

public class ChildLayoutB extends FrameLayout implements ITangramViewLifeCycle {

    public ChildLayoutB(@NonNull Context context) {
        this(context, null);
    }

    public ChildLayoutB(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildLayoutB(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChildLayout();
    }

    private TextView textIndex;
    private TextView textStyle;

    private void initChildLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_child_layout_b, this);

        textIndex = findViewById(R.id.item_text);
        textStyle = findViewById(R.id.item_style);
    }

    @Override public void cellInited(BaseCell cell) {
        ULog.show("cellInited : " + cell.toString());
    }

    @Override public void postBindView(BaseCell cell) {
        String jsonString = cell.optStringParam("item");
        cacheItemValue(JSON.parseObject(jsonString, TestBean.class));
    }

    @Override public void postUnBindView(BaseCell cell) {

    }

    private void cacheItemValue(TestBean bean) {
        if (bean == null || textIndex == null) {
            return;
        }
        textIndex.setText(String.valueOf(bean.index));
    }
}
