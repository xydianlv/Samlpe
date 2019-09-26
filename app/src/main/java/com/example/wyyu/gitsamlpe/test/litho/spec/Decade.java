package com.example.wyyu.gitsamlpe.test.litho.spec;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;

/**
 * Created by wyyu on 2019-09-26.
 **/

public class Decade implements Datum {

    private String title;
    private String info;

    public Decade(String title, String info) {
        this.title = title;
        this.info = info;
    }

    @Override public RenderInfo createComponent(ComponentContext componentContext) {
        return ComponentRenderInfo.create()
            .component(MainItem.create(componentContext).title(title).info(info).build())
            .isSticky(true)
            .build();
    }
}
