package com.example.wyyu.gitsamlpe.test.tangram.layout;

import android.content.Context;
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
 * Created by wyyu on 2019-08-19.
 **/

public class ChildLayoutA extends FrameLayout implements ITangramViewLifeCycle {

    public ChildLayoutA(Context context) {
        this(context, null);
    }

    public ChildLayoutA(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildLayoutA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChildLayout();
    }

    private TextView indexText;
    private TextView textStyle;

    private void initChildLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_child_layout_a, this);

        indexText = findViewById(R.id.item_text);
    }

    @Override public void cellInited(BaseCell cell) {
        ULog.show("cellInited : " + cell.toString());
    }

    @Override public void postBindView(BaseCell cell) {
        String jsonString = cell.optStringParam("items");
        //cacheItemValue(JSON.parseObject(jsonString, TestBean.class));
    }

    @Override public void postUnBindView(BaseCell cell) {
        ULog.show("postUnBindView : " + cell.toString());
    }

    private void cacheItemValue(TestBean bean) {
        if (bean == null || indexText == null) {
            return;
        }
        indexText.setText(String.valueOf(bean.index));
    }
}
