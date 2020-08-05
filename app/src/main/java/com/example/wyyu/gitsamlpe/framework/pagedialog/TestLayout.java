package com.example.wyyu.gitsamlpe.framework.pagedialog;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/11/19.
 **/

public class TestLayout extends LinearLayout implements View.OnClickListener {

    public TestLayout(Context context) {
        this(context, null);
    }

    public TestLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTestLayout();
    }

    private int position;

    private void initTestLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_test_layout, this);

        findViewById(R.id.test_layout_head).setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        if (headClickListener == null) {
            return;
        }
        headClickListener.onClick(position == 0);
    }

    void bindPosition(int position) {
        this.position = position;
    }

    private OnHeadClickListener headClickListener;

    void setOnHeadClickListener(OnHeadClickListener headClickListener) {
        this.headClickListener = headClickListener;
    }

    public interface OnHeadClickListener {

        void onClick(boolean isFirst);
    }
}
