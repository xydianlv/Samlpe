package com.example.wyyu.gitsamlpe.test.litho.spec;

import android.graphics.Color;
import android.util.Log;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
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
        @Prop String info) {

        return Column.create(componentContext)
            .paddingDip(YogaEdge.ALL, 16)
            .backgroundColor(Color.WHITE)
            .child(Text.create(componentContext)
                .text(title)
                .clickHandler(MainItem.onFaceClicked(componentContext, title))
                .textSizeDip(16))
            .child(Text.create(componentContext)
                .text(info)
                .clickHandler(MainItem.onInfoClicked(componentContext, info))
                .textSizeDip(14))
            .build();
    }

    // 点击事件的回调
    @OnEvent(ClickEvent.class) static void onFaceClicked(ComponentContext componentContext,
        @Param String title) {
        //@Param String face上面传递过来的数据  @Param注解很重要哦
        Log.e("onFaceClicked", "title");
    }

    // 点击事件的回调
    @OnEvent(ClickEvent.class) static void onInfoClicked(ComponentContext componentContext,
        @Param String info) {
        //@Param String face上面传递过来的数据  @Param注解很重要哦
        Log.e("onFaceClicked", "info : " + info);
    }
}
