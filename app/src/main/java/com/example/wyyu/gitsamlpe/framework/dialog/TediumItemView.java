package com.example.wyyu.gitsamlpe.framework.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class TediumItemView extends LinearLayout {

    public TediumItemView(Context context) {
        this(context, null);
    }

    public TediumItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TediumItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initItemView();
    }

    private TextView itemInfo;
    private View itemLine;

    private void initItemView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_tedium_item_view, this);

        itemInfo = findViewById(R.id.tedium_item_text);
        itemLine = findViewById(R.id.tedium_item_line);
    }

    void setItemValue(String info, boolean isLast) {
        itemInfo.setText(info);
        itemLine.setVisibility(isLast ? GONE : VISIBLE);
    }
}
