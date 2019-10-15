package com.example.wyyu.gitsamlpe.test.litho.common.component;

import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.Text;

/**
 * Created by wyyu on 2019-10-14.
 **/

public class ComponentText extends AbsComponent<Object> {

    private static final String TEXT =
        "Litho是Facebook推出的一套高效构建Android UI的声明式框架，主要目的是提升RecyclerView复杂列表的滑动性能和降低内存占用。";

    public ComponentText() {
        super("ComponentText");
    }

    @Override public Component createLayout(ComponentContext context, Object data) {
        return Text.create(context)
            .text(TEXT)
            .textSizeDip(15.0f)
            .textColor(0xff333333)
            .maxLines(2)
            .ellipsize(TextUtils.TruncateAt.END)
            .spacingMultiplier(1.3f)
            .build();
    }
}
