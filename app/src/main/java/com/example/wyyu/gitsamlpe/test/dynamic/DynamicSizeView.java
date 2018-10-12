package com.example.wyyu.gitsamlpe.test.dynamic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/10/12.
 **/

public class DynamicSizeView extends FrameLayout {

    public DynamicSizeView(@NonNull Context context) {
        this(context, null);
    }

    public DynamicSizeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicSizeView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDynamicSizeView();
    }

    private View rootView;

    private void initDynamicSizeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_dynamic_size_view, this);

        rootView = findViewById(R.id.dynamic_root_view);

        post(new Runnable() {
            @Override public void run() {

                int height = getMeasuredHeight();
                int width = getMeasuredWidth();

                Log.e("DynamicSizeView", "width : " + width);
                Log.e("DynamicSizeView", "height : " + height);

                FrameLayout.LayoutParams linearParams =
                    (FrameLayout.LayoutParams) rootView.getLayoutParams();

                linearParams.height = height;
                linearParams.width = width;

                rootView.setLayoutParams(linearParams);
            }
        });
    }

    void setRootViewSize(int width, int height) {
        FrameLayout.LayoutParams linearParams =
            (FrameLayout.LayoutParams) rootView.getLayoutParams();

        linearParams.height = height * 3;
        linearParams.width = width * 3;

        rootView.setLayoutParams(linearParams);
    }
}
