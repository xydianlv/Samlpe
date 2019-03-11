package com.example.wyyu.gitsamlpe.framework.window;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2019/3/11.
 **/

public class ContentView extends LinearLayout {

    public ContentView(Context context) {
        this(context, null);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContentView();
    }

    private void initContentView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_content_view, this);

        findViewById(R.id.content_test_a).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                UToast.showShort(getContext(),"按名称排序");
            }
        });
        findViewById(R.id.content_test_b).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                UToast.showShort(getContext(),"按时间排序");
            }
        });
    }
}
