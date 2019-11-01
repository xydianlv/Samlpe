package com.example.wyyu.gitsamlpe.test.litho.text;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponent;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-11-01.
 *
 * 扩展字段
 * params[0] - float - 字符大小，单位 dp
 * params[0] - boolean - 是否有行数限制
 * params[1] - int - 最大行数
 * params[2] - HeightLightText[] - 高亮字段列表
 **/

public class ComponentSpanText extends AbsComponent<String> {

    private static final int VIEW_WIDTH = UIUtils.getScreenWidth() - UIUtils.dpToPx(14.0f * 2);
    private static final String ELLIPSE = "…全文";
    private static final String SPACE = " ";

    ComponentSpanText() {
        super("ComponentSpanText");
    }

    @Override
    public Component createLayout(ComponentContext context, String data, Object... params) {
        if (TextUtils.isEmpty(data)) {
            return Row.create(context).heightDip(0.0f).widthDip(0.0f).build();
        }
        return loadTextShow(context,
            loadSpanText(data, textSize(params), hasLimit(params), maxLines(params),
                lightTextList(data, params)), textSize(params));
    }

    private float textSize(Object... params) {
        if (params == null || params.length < 1 || !(params[0] instanceof Float)) {
            return 14.0f;
        }
        return (float) params[0];
    }

    // 从参数列表中获取 行数限制信息
    private boolean hasLimit(Object... params) {
        if (params == null || params.length < 2 || !(params[1] instanceof Boolean)) {
            return false;
        }
        return (boolean) params[1];
    }

    // 从参数列表中获取 最大行数信息
    private int maxLines(Object... params) {
        if (params == null || params.length < 3 || !(params[2] instanceof Integer)) {
            return 8;
        }
        return (int) params[2];
    }

    // 从参数列表中获取 高亮字段列表，并移除不合理高亮字段数据
    private List<HeightLightText> lightTextList(String data, Object... params) {
        if (TextUtils.isEmpty(data) || params == null || params.length < 4) {
            return null;
        }
        if (!(params[3] instanceof Object[])) {
            return null;
        }
        Object[] objectArray = (Object[]) params[3];
        if (objectArray.length == 0) {
            return null;
        }
        List<HeightLightText> lightTextList = new ArrayList<>();
        for (Object object : objectArray) {
            if (!(object instanceof HeightLightText)) {
                continue;
            }
            HeightLightText lightText = (HeightLightText) object;
            if (lightText.insertIndex < 0 || lightText.insertIndex > data.length()) {
                continue;
            }
            switch (lightText.lightType) {
                case HeightLightText.LightType.DRAWABLE:
                    if (lightText.drawable == null) {
                        continue;
                    }
                    break;
                case HeightLightText.LightType.INSERT:
                    if (TextUtils.isEmpty(lightText.insertText)) {
                        continue;
                    }
                    break;
                case HeightLightText.LightType.LIGHT:
                    if (lightText.insertIndex + lightText.lightLength > data.length()) {
                        lightText.lightLength = data.length() - lightText.insertIndex;
                    }
                    break;
            }
            lightTextList.add((HeightLightText) object);
        }
        return lightTextList;
    }

    // 加载处理了所有特殊条件的 SpanText
    private CharSequence loadSpanText(String mainText, float textSize, boolean hasLimit,
        int maxLines, List<HeightLightText> lightTextList) {
        if ((lightTextList == null || lightTextList.isEmpty()) && !hasLimit) {
            return mainText;
        }
        if (lightTextList != null && !lightTextList.isEmpty()) {
            mainText = dispatchHeightLight(mainText, lightTextList);
        }
        if (hasLimit && maxLines > 0) {
            mainText = dispatchMaxLines(mainText, textSize, maxLines);
        }
        if (lightTextList == null || lightTextList.isEmpty()) {
            return mainText;
        }
        SpannableString spanString = new SpannableString(mainText);
        for (HeightLightText lightText : lightTextList) {
            switch (lightText.lightType) {
                case HeightLightText.LightType.DRAWABLE:
                    lightText.drawable.setBounds(0, 0, lightText.drawable.getIntrinsicWidth(),
                        lightText.drawable.getIntrinsicHeight());
                    ImageSpan img = new ImageSpan(lightText.drawable);
                    spanString.setSpan(img, lightText.insertIndex, lightText.insertIndex + 1,
                        ImageSpan.ALIGN_BASELINE);
                    break;
                case HeightLightText.LightType.INSERT:
                    break;
                case HeightLightText.LightType.LIGHT:
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override public void onClick(@NonNull View widget) {
                            if (lightText.clickListener != null) {
                                lightText.clickListener.onClick(widget);
                            }
                        }

                        @Override public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(lightText.textColor);
                            ds.setUnderlineText(false);
                            ds.clearShadowLayer();
                        }
                    };
                    spanString.setSpan(clickableSpan, lightText.insertIndex,
                        lightText.insertIndex + lightText.lightLength,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
            }
        }
        return spanString;
    }

    // 将 HeightLightText 中 类型为 INSERT、DRAWABLE 需要插入到原文本的高亮字段添加进去，并将类型转为 Light
    private String dispatchHeightLight(String mainText, List<HeightLightText> lightTextList) {
        if (lightTextList == null || lightTextList.isEmpty()) {
            return mainText;
        }
        StringBuilder sBuilder = new StringBuilder(mainText);
        for (HeightLightText lightText : lightTextList) {
            if (lightText == null) {
                continue;
            }
            switch (lightText.lightType) {
                case HeightLightText.LightType.LIGHT:
                    continue;
                case HeightLightText.LightType.INSERT:
                    if (TextUtils.isEmpty(lightText.insertText)) {
                        break;
                    }
                    if (lightText.insertIndex < 0 || lightText.insertIndex > sBuilder.length()) {
                        break;
                    }
                    sBuilder.insert(lightText.insertIndex, lightText.insertText);
                    lightText.lightLength = lightText.insertText.length();
                    lightText.lightType = HeightLightText.LightType.LIGHT;
                    break;
                case HeightLightText.LightType.DRAWABLE:
                    if (lightText.drawable == null) {
                        break;
                    }
                    if (lightText.insertIndex < 0 || lightText.insertIndex > sBuilder.length()) {
                        break;
                    }
                    sBuilder.insert(lightText.insertIndex, SPACE);
                    break;
            }
        }
        return sBuilder.toString();
    }

    // 将文本行数处理为限制行数内
    private String dispatchMaxLines(String mainText, float textSize, int maxLines) {

        String[] textArray = mainText.split("\n");
        int lines = 0;

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);

        for (int index = 0; index < textArray.length; index++) {
            float measure = textPaint.measureText(textArray[index]);
            int currentLines = UIUtils.dpToPx(measure) / VIEW_WIDTH + 1;
            lines = lines + currentLines;
            if (lines < maxLines) {
                continue;
            }
            if (lines == maxLines) {
                mainText = "";
                for (int position = 0; position <= index; position++) {
                    mainText = String.format("%s%s", mainText, textArray[position]);
                }
                return mainText;
            }
            int divide = maxLines - (lines - currentLines);
            String funText = TextUtils.ellipsize(textArray[index], textPaint,
                (int) (VIEW_WIDTH / 3.5) * divide - ELLIPSE.length() * textPaint.getTextSize(),
                TextUtils.TruncateAt.END).toString() + ELLIPSE;
            mainText = "";
            for (int position = 0; position < index; position++) {
                mainText = String.format("%s%s", mainText, textArray[position] + "\n");
            }
            return mainText + funText;
        }
        return mainText;
    }

    private Component loadTextShow(ComponentContext context, CharSequence text, float textSize) {
        return Text.create(context)
            .textSizeDip(textSize)
            .spacingMultiplier(1.3f)
            .text(text)
            .textColor(0xff333333)
            .build();
    }
}
