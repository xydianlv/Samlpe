package com.example.wyyu.gitsamlpe.test.litho.custom.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.custom.component.ComponentFoot;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.LithoView;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-09-30.
 **/

public class LayoutFoot extends FrameLayout {

    public LayoutFoot(@NonNull Context context) {
        super(context);
        initFoot();
    }

    public LayoutFoot(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFoot();
    }

    public LayoutFoot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFoot();
    }

    private ComponentFoot componentFoot;

    private void initFoot() {

        ComponentContext componentContext = new ComponentContext(getContext());

        componentFoot = ComponentFoot.create(componentContext).build();

        removeAllViews();
        addView(LithoView.create(componentContext, componentFoot));
    }

    public void setValue(int likeCount, int reviewCount) {
        componentFoot.setFootValue(likeCount);
    }
}
