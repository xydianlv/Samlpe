package com.example.wyyu.gitsamlpe.test.litho.simple.spec;

import android.graphics.Color;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class SimpleItemSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop String title,
        @Prop String info) {

        return Column.create(componentContext)
            .backgroundColor(Color.WHITE)
            .child(Text.create(componentContext)
                .text(title)
                .textSizeDip(16)
                .textColor(0xff333333)
                .paddingDip(YogaEdge.HORIZONTAL, 16)
                .paddingDip(YogaEdge.TOP, 8))
            .child(SimpleInfo.create(componentContext).preInfo(info))
            //.child(Text.create(componentContext)
            //    .text(info)
            //    .textSizeDip(12)
            //    .textColor(0x99ff0000)
            //    .paddingDip(YogaEdge.HORIZONTAL, 16)
            //    .paddingDip(YogaEdge.TOP, 4)
            //    .paddingDip(YogaEdge.BOTTOM, 8))
            .child(Row.create(componentContext).backgroundColor(0xffc0c0c0).heightPx(1))
            .clickHandler(SimpleItem.onClickItem(componentContext, info))
            .build();
    }

    // 点击事件的回调
    @OnEvent(ClickEvent.class) static void onClickItem(ComponentContext componentContext,
        @Param String info) {
        //@Param String face上面传递过来的数据  @Param注解很重要哦
        UToast.showShort(componentContext.getAndroidContext(), info);
    }
}
