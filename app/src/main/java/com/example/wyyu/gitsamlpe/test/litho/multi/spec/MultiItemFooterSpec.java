package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemFooterSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        return Row.create(componentContext)
            .paddingDip(YogaEdge.TOP, 14)
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .widthDip(18)
                .heightDip(18)
                .build())
            .child(Text.create(componentContext)
                .text(String.valueOf(itemBean.countLike))
                .paddingDip(YogaEdge.TOP, 1)
                .marginDip(YogaEdge.LEFT, 12)
                .textColor(0xff333333)
                .textSizeDip(13)
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .marginDip(YogaEdge.LEFT, 32)
                .widthDip(18)
                .heightDip(18)
                .build())
            .child(Text.create(componentContext)
                .text(String.valueOf(itemBean.countReview))
                .paddingDip(YogaEdge.TOP, 1)
                .marginDip(YogaEdge.LEFT, 12)
                .textColor(0xff333333)
                .textSizeDip(13)
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 0)
                .positionDip(YogaEdge.TOP, 14)
                .widthDip(18)
                .heightDip(18)
                .build())
            .build();
    }
}
