package com.example.wyyu.gitsamlpe.test.litho.multi.spec;

import android.view.WindowManager;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Image;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class MultiItemImageSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext componentContext, @Prop ItemBean itemBean) {
        if (itemBean.imageBean == null) {
            return Column.create(componentContext).widthDip(0).heightDip(0).build();
        } else if (itemBean.imageBean.height > itemBean.imageBean.width) {
            return Image.create(componentContext)
                .heightDip(232)
                .widthDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(itemBean.imageBean.resId)
                .build();
        } else {
            return Image.create(componentContext)
                .widthDip(232)
                .heightDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(itemBean.imageBean.resId)
                .build();
        }
    }
}
