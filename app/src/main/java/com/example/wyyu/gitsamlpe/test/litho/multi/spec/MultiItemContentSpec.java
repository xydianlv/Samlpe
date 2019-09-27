package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemContentSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        return Column.create(componentContext)
            .paddingDip(YogaEdge.TOP, 12)
            .child(Text.create(componentContext)
                .text(itemBean.content)
                .textColor(0xff333333)
                .textSizeDip(16)
                .spacingMultiplier(1.3f)
                .maxLines(4)
                .ellipsize(TextUtils.TruncateAt.END)
                .build())
            .child(MultiItemImage.create(componentContext)
                .paddingDip(YogaEdge.TOP, 12)
                .itemBean(itemBean)
                .build())
            .build();
    }
}
