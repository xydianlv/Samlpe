package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityMultiText extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityMultiText.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_text);

        initActivity();
    }

    private void initActivity(){
        initToolbar("MultiText", 0xffffffff, 0xff84919b);

        String name = "LCC ：";
        String content =
            "love love love love love love love love love love love love love love love love love love love love";
        Drawable drawable = getResources().getDrawable(R.drawable.img_volume);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        String info = "forever";

        SpannableString spannableString = new SpannableString(name + content + " " + info);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override public void onClick(@NonNull View widget) {
            }

            @Override public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xffff0000);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        spannableString.setSpan(clickableSpan, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, name.length() + content.length(),
            name.length() + content.length() + 1, ImageSpan.ALIGN_BASELINE);

        ClickableSpan clickableSpanInfo = new ClickableSpan() {
            @Override public void onClick(@NonNull View widget) {
            }

            @Override public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xff00ff00);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        spannableString.setSpan(clickableSpanInfo, name.length() + content.length(),
            name.length() + content.length() + info.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView text = findViewById(R.id.img_span_text_view);
        UToast.showShort(this,
            String.valueOf(getLineMaxNumber(content, text.getPaint(), text.getMaxWidth())));

        text.setText(spannableString);
    }

    private int getLineMaxNumber(String text, TextPaint paint, int maxWidth) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        StaticLayout staticLayout =
            new StaticLayout(text, paint, maxWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
        //获取第一行最后显示的字符下标
        return staticLayout.getLineEnd(0);
    }
}
