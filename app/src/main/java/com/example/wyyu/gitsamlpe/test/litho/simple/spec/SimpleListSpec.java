package com.example.wyyu.gitsamlpe.test.litho.simple.spec;

import com.example.wyyu.gitsamlpe.test.litho.Datum;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.common.DataDiffSection;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.sections.widget.RecyclerCollectionComponent;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.yoga.YogaEdge;
import java.util.List;

/**
 * Created by wyyu on 2019-09-27.
 **/

@LayoutSpec class SimpleListSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext c, @Prop List<Datum> dataModels) {

        return RecyclerCollectionComponent.create(c)
            .disablePTR(true)
            .section(DataDiffSection.<Datum>create(new SectionContext(c)).data(dataModels)
                .renderEventHandler(SimpleList.onRender(c))
                .build())
            .paddingDip(YogaEdge.TOP, 8)
            .testKey("main_screen")
            .build();
    }

    @OnEvent(RenderEvent.class)
    static RenderInfo onRender(ComponentContext c, @FromEvent Datum model) {
        return model.createComponent(c);
    }
}
