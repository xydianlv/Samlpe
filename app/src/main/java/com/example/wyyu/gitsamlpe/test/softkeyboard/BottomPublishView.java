package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.CommonUtil;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class BottomPublishView extends LinearLayout {

    public BottomPublishView(Context context) {
        this(context, null);
    }

    public BottomPublishView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomPublishView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPublishView();
    }

    private EditText editText;
    private View info;

    private void initPublishView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_publish_view, this);

        editText = findViewById(R.id.publish_edit);
        info = findViewById(R.id.publish_info);
    }

    void showInfoView() {
        CommonUtil.showSoftInput(getContext(), editText);
        info.setVisibility(VISIBLE);
    }

    void hideInfoView() {
        CommonUtil.hideSoftInput(getContext(), editText);
        info.setVisibility(GONE);
    }
}
