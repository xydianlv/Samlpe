package com.example.wyyu.gitsamlpe.test.litho.custom.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventDispatcher;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.LithoView;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-09-30.
 **/

public class LayoutHead extends FrameLayout implements HasEventDispatcher {

    public LayoutHead(@NonNull Context context) {
        super(context);
        initLayout();
    }

    public LayoutHead(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LayoutHead(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private ComponentContext componentContext;

    private void initLayout() {
        initContext();
        setHeadValue(null, null);
    }

    private void initContext() {
        componentContext = new ComponentContext(getContext());
    }

    public void setHeadValue(String title, String info) {
        removeAllViews();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(info)) {
            addView(LithoView.create(componentContext,
                Row.create(componentContext).heightDip(0.0f).widthDip(0.0f).build()));
        } else {
            addView(loadHeadLayout(title, info));
        }
    }

    private View loadHeadLayout(String title, String info) {

        Component component = Row.create(componentContext)
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.multi_image_1)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .widthDip(32.0f)
                .heightDip(32.0f)
                .build())
            .child(createTitleAndInfo(title, info))
            .child(Image.create(componentContext)
                .widthDip(12.0f)
                .heightDip(12.0f)
                .drawableRes(R.mipmap.icon_test)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 12)
                .build())
            .build();

        return LithoView.create(componentContext, component);
    }

    private Component createTitleAndInfo(String title, String info) {

        return Column.create(componentContext)
            .child(Text.create(componentContext)
                .text(title)
                .textSizeDip(15.0f)
                .textColor(0xff333333)
                .clickHandler(new EventHandler<>(this, 0, new Object[] { title }))
                .build())
            .child(Text.create(componentContext)
                .text(info)
                .textSizeDip(12.0f)
                .textColor(0xff666666)
                .clickHandler(new EventHandler<>(this, 1, new Object[] { info }))
                .build())
            .build();
    }

    @Override public EventDispatcher getEventDispatcher() {
        return (eventHandler, eventState) -> {
            switch (eventHandler.id) {
                case 0:
                    UToast.showShort(getContext(), "Title");
                    break;
                case 1:
                    UToast.showShort(getContext(), "info");
                    break;
            }
            return null;
        };
    }
}
