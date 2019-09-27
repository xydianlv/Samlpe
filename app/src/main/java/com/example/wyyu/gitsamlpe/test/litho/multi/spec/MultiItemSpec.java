package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        return Column.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.VERTICAL, 12)
            .child(MultiItemHeader.create(componentContext).itemBean(itemBean))
            .child(MultiItemContent.create(componentContext).itemBean(itemBean))
            .child(MultiItemFooter.create(componentContext).itemBean(itemBean))
            .build();
    }
}
