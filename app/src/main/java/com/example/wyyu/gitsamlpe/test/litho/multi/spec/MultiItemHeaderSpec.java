package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Image;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemHeaderSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        return Column.create(componentContext)
            .child(Image.create(componentContext)
                .drawableRes(itemBean.iconId)
                .widthDip(32)
                .heightDip(32)
                .scaleType(ImageView.ScaleType.CENTER_CROP))
            .child(MultiItemTitle.create(componentContext).itemBean(itemBean))
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 0)
                .positionDip(YogaEdge.TOP, 4)
                .widthDip(14)
                .heightDip(14))
            .build();
    }
}
