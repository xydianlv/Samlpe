package com.example.wyyu.gitsamlpe.test.litho.common.component;

import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Card;

/**
 * Created by wyyu on 2019-10-17.
 **/

public class ComponentCard extends AbsComponent<Object> {

    public ComponentCard() {
        super("ComponentCard");
    }

    @Override public Component createLayout(ComponentContext context, Object data) {
        return Card.create(context)
            .content(Row.create(context)
                .widthPercent(100)
                .heightDip(32.0f)
                .backgroundColor(0xffa39480)
                .build())
            .shadowStartColor(0xfff16d7a)
            .shadowEndColor(0x00f16d7a)
            .shadowElevationDip(12.0f)
            .cornerRadiusDip(16.0f)
            .build();
    }
}
