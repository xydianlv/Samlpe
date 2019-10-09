package com.example.wyyu.gitsamlpe.test.litho.simple.spec;

import android.text.TextUtils;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-08.
 **/

@LayoutSpec class SimpleInfoSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop String preInfo,
        @State String info) {
        return Text.create(componentContext)
            .text(TextUtils.isEmpty(info) ? preInfo : info)
            .textSizeDip(12)
            .textColor(0x99ff0000)
            .paddingDip(YogaEdge.HORIZONTAL, 16)
            .paddingDip(YogaEdge.TOP, 4)
            .paddingDip(YogaEdge.BOTTOM, 8)
            .clickHandler(SimpleInfo.onClickInfo(componentContext))
            .build();
    }

    @OnUpdateState static void refreshInfo(StateValue<String> info) {
        info.set("47");
    }

    @OnEvent(ClickEvent.class) static void onClickInfo(ComponentContext c) {
        SimpleInfo.refreshInfoSync(c);
    }
}
