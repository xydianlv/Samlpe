package com.example.wyyu.gitsamlpe.test.litho.custom.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;

/**
 * Created by wyyu on 2019-09-30.
 **/

public class LayoutContent extends FrameLayout {

    public LayoutContent(@NonNull Context context) {
        super(context);
        initLayout();
    }

    public LayoutContent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LayoutContent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private ComponentContext componentContext;

    private void initLayout() {
        initContext();
        setContentValue(null, 0);
    }

    private void initContext() {
        componentContext = new ComponentContext(getContext());
    }

    public void setContentValue(String content, int resId) {
        removeAllViews();
        if (TextUtils.isEmpty(content) && resId == 0) {
            addView(LithoView.create(componentContext,
                Row.create(componentContext).heightDip(0.0f).widthDip(0.0f).build()));
        } else {
            addView(loadContentView(content, resId));
        }
    }

    private View loadContentView(String content, int resId) {

        Component component = Column.create(componentContext)
            .child(Text.create(componentContext)
                .text(content)
                .maxLines(4)
                .ellipsize(TextUtils.TruncateAt.END)
                .textColor(0xff333333)
                .textSizeDip(16.0f)
                .build())
            .child(Image.create(componentContext)
                .drawableRes(resId)
                .widthDip(WindowManager.LayoutParams.MATCH_PARENT)
                .heightDip(202.0f)
                .scaleType(ImageView.ScaleType.FIT_START)
                .build())
            .build();

        return LithoView.create(componentContext, component);
    }
}
