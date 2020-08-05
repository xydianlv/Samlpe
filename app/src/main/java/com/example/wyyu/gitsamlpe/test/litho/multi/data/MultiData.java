package com.example.wyyu.gitsamlpe.test.litho.multi.data;

import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class MultiData implements Datum {

    private ItemBean itemBean;

    public MultiData(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    @Override public RenderInfo createComponent(ComponentContext componentContext) {
        Component component = Column.create(componentContext)
            .child(Row.create(componentContext)
                .heightDip(6)
                .backgroundColor(0xffededf2)
                .marginDip(YogaEdge.TOP, 4)
                .build())
            .build();
        return ComponentRenderInfo.create().component(component).build();
    }
}
