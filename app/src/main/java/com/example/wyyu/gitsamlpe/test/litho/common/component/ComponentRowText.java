package com.example.wyyu.gitsamlpe.test.litho.common.component;

import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-31.
 **/

public class ComponentRowText extends AbsComponent<Object> {

    public ComponentRowText() {
        super("ComponentRowText");
    }

    @Override public Component createLayout(ComponentContext context, Object data) {
        return Row.create(context)
            .child(Text.create(context)
                .textColor(0xff333333)
                .textSizeDip(15.0f)
                .text("测试文字前缀")
                .build())
            .child(Image.create(context)
                .marginDip(YogaEdge.LEFT, 10.0f)
                .heightDip(12.0f)
                .widthDip(12.0f)
                .drawableRes(R.mipmap.icon_test)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build())
            .child(Image.create(context)
                .marginDip(YogaEdge.LEFT, 10.0f)
                .heightDip(12.0f)
                .widthDip(12.0f)
                .drawableRes(R.mipmap.icon_test)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build())
            .build();
    }
}
