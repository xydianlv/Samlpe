package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.window.ContentView;
import com.example.wyyu.gitsamlpe.framework.window.PopupShowTest;
import com.example.wyyu.gitsamlpe.framework.window.ShowType;

/**
 * Created by wyyu on 2019/3/1.
 **/

public class HolderFootView extends FrameLayout {

    public HolderFootView(@NonNull Context context) {
        this(context, null);
    }

    public HolderFootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HolderFootView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFootView();
    }

    private TextView textMiddle;
    private TextView textRight;
    private TextView textLeft;

    private void initFootView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_holder_foot_view, this);

        textMiddle = findViewById(R.id.foot_text_middle);
        textRight = findViewById(R.id.foot_text_right);
        textLeft = findViewById(R.id.foot_text_left);

        textMiddle.setOnLongClickListener(new OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                new PopupShowTest(getContext()).show(textMiddle, new ContentView(getContext()),
                    ShowType.左下角);
                return false;
            }
        });
        textRight.setOnLongClickListener(new OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                new PopupShowTest(getContext()).show(textRight, new ContentView(getContext()),
                    ShowType.左上角);
                return false;
            }
        });
        textLeft.setOnLongClickListener(new OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                new PopupShowTest(getContext()).show(textLeft, new ContentView(getContext()),
                    ShowType.右上角);
                return false;
            }
        });
    }

    public void setFootValue(MultiData multiData) {
        if (textMiddle == null || textRight == null || textLeft == null) {
            setVisibility(GONE);
            return;
        }
        if (multiData == null) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);

            textMiddle.setVisibility(GONE);
            textRight.setVisibility(GONE);
            textLeft.setVisibility(GONE);

            switch (multiData.type) {
                case 0:
                    textLeft.setVisibility(VISIBLE);
                    textLeft.setText(String.valueOf(multiData.index));
                    break;
                case 1:
                    textMiddle.setVisibility(VISIBLE);
                    textMiddle.setText(String.valueOf(multiData.index));
                    break;
                case 2:
                    textRight.setVisibility(VISIBLE);
                    textRight.setText(String.valueOf(multiData.index));
                    break;
            }
        }
    }
}
