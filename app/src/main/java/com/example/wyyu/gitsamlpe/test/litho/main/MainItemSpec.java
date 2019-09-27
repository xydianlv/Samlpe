package com.example.wyyu.gitsamlpe.test.litho.main;

import android.content.Intent;
import android.graphics.Color;
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
 * Created by wyyu on 2019-09-26.
 **/

@LayoutSpec class MainItemSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop String title,
        @Prop Class<?> classActivity, @Prop int bgColor) {

        return Column.create(componentContext)
            .backgroundColor(Color.WHITE)
            .child(Text.create(componentContext)
                .text(title)
                .paddingDip(YogaEdge.ALL, 16)
                .backgroundColor(bgColor)
                .clickHandler(MainItem.onFaceClicked(componentContext, classActivity))
                .textSizeDip(16))
            .child(Row.create(componentContext).heightPx(1).backgroundColor(0xffc0c0c0))
            .build();
    }

    // 点击事件的回调
    @OnEvent(ClickEvent.class) static void onFaceClicked(ComponentContext componentContext,
        @Param Class<?> classActivity) {
        //@Param String face上面传递过来的数据  @Param注解很重要哦
        if (classActivity != null) {
            componentContext.getAndroidContext()
                .startActivity(new Intent(componentContext.getAndroidContext(), classActivity));
        }
    }
}
