package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemTitleSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        return Column.create(componentContext)
            .positionType(YogaPositionType.ABSOLUTE)
            .positionDip(YogaEdge.LEFT, 40)
            .child(Text.create(componentContext)
                .text(itemBean.title)
                .textSizeDip(14)
                .textColor(0xff333333))
            .child(Text.create(componentContext)
                .text(itemBean.info)
                .textSizeDip(11)
                .textColor(0xff666666)
                .build())
            .build();
    }
}
