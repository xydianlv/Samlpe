package com.example.wyyu.gitsamlpe.test.litho.simple.data;

import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.example.wyyu.gitsamlpe.test.litho.simple.spec.SimpleItem;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class DecadeSimple implements Datum {

    public String title;
    public String info;

    public DecadeSimple(String title, String info) {
        this.title = title;
        this.info = info;
    }

    @Override public RenderInfo createComponent(ComponentContext componentContext) {
        return ComponentRenderInfo.create()
            .component(SimpleItem.create(componentContext).title(title).info(info).build())
            .isSticky(true)
            .build();
    }
}
