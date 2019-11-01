package com.example.wyyu.gitsamlpe.test.litho.common.component;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
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

        SpannableString spanString = new SpannableString(TEXT);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override public void onClick(@NonNull View widget) {
                UToast.showShort(context.getAndroidContext(), "ClickSpan");
            }

            @Override public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xffff0000);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };

        spanString.setSpan(new ForegroundColorSpan(0xff00ff00), 0, 5,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spanString.setSpan(clickableSpan, 6, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return Text.create(context)
            .text(spanString)
            .textSizeDip(15.0f)
            .textColor(0xff333333)
            .maxLines(2)
            .ellipsize(TextUtils.TruncateAt.END)
            .spacingMultiplier(1.3f)
            .build();
    }
}
