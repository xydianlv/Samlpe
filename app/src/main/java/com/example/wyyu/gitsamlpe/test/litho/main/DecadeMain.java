package com.example.wyyu.gitsamlpe.test.litho.main;

import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;

/**
 * Created by wyyu on 2019-09-26.
 **/

public class DecadeMain implements Datum {

    private Class<?> classActivity;
    private String title;
    private int bgColor;

    DecadeMain(String title, Class<?> classActivity, int bgColor) {
        this.title = title;
        this.classActivity = classActivity;
        this.bgColor = bgColor;
    }

    @Override public RenderInfo createComponent(ComponentContext componentContext) {
        return ComponentRenderInfo.create()
            .component(MainItem.create(componentContext)
                .title(title)
                .bgColor(bgColor)
                .classActivity(classActivity)
                .build())
            .isSticky(true)
            .build();
    }
}
