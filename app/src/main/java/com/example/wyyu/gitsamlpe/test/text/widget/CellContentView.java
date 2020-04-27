package com.example.wyyu.gitsamlpe.test.text.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wyyu on 2020-04-21.
 **/

public class CellContentView extends AppCompatTextView {

    public CellContentView(Context context) {
        super(context);
    }

    public CellContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CellContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String ELLIPSE = "…全文";
    private static final String SPACE = " ";

    // 待添加的高亮字段列表
    private List<HeightLightText> lightTextList;
    // 待展示的原文本
    private String mainText;

    // 控件在界面中的宽度
    private int showWidth;

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (showWidth == 0) {
            showWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            loadTextShow();
        }
    }

    /**
     * 为文本展示控件传入数据
     * 一些最大行数、文字大小等在 XML 中直接设置即可
     *
     * @param mainText 原文本
     * @param lightTextArray 高亮字段列表
     */
    public void setTextValue(String mainText, HeightLightText... lightTextArray) {
        // 这个地方不能用 Arrays.asList ，因为在计算 "...全文" 时会对列表做变动，用 Arrays.asList 生成的 List 会报错
        if (lightTextArray == null || lightTextArray.length == 0) {
            this.lightTextList = null;
        } else {
            if (this.lightTextList == null) {
                this.lightTextList = new ArrayList<>();
            }
            this.lightTextList.addAll(Arrays.asList(lightTextArray));
        }
        this.mainText = mainText;
        loadTextShow();
    }

    private void loadTextShow() {
        dispatchLightText();
        dispatchTextLines();
        showMarkText();
    }

    // 将需要插入的高亮字段先添加到正确的位置
    private void dispatchLightText() {
        if (TextUtils.isEmpty(mainText) || lightTextList == null || lightTextList.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder(mainText);
        for (HeightLightText lightText : lightTextList) {
            if (lightText == null
                || lightText.insertIndex < 0
                || lightText.insertIndex > mainText.length()) {
                continue;
            }
            switch (lightText.lightType) {
                case HeightLightText.LightType.DRAWABLE:
                    if (lightText.drawable != null) {
                        builder.insert(lightText.insertIndex, SPACE);
                    }
                    break;
                case HeightLightText.LightType.INSERT:
                    if (!TextUtils.isEmpty(lightText.insertText)) {
                        builder.insert(lightText.insertIndex, lightText.insertText);
                        lightText.lightType = HeightLightText.LightType.LIGHT;
                        lightText.lightLength = lightText.insertText.length();
                    }
                    break;
                case HeightLightText.LightType.LIGHT:
                    break;
            }
        }
        mainText = builder.toString();
    }

    // 计算文本的展示高度
    private void dispatchTextLines() {
        if (TextUtils.isEmpty(mainText) || showWidth == 0) {
            return;
        }
        int maxLines = getMaxLines();
        if (maxLines == -1) {
            return;
        }

        // 按行截取后拼接起来的文本内容
        StringBuilder stringBuilder = new StringBuilder();
        // 按行裁剪后的文本列表
        String[] textArray = mainText.split("\n");
        // 每行的文字个数
        int textCount = (int) (showWidth / getTextSize());
        // 行数
        int lineCount = 0;

        for (int index = 0; index < textArray.length; index++) {
            String currentText = textArray[index];

            int textLine = TextUtils.isEmpty(currentText) ? 1 : currentText.length() / textCount;
            textLine = textLine + (currentText.length() % textCount > 0 ? 1 : 0);

            lineCount = lineCount + textLine;
            if (lineCount < maxLines) {
                continue;
            }

            for (int position = 0; position < index; position++) {
                stringBuilder.append(textArray[position]);
                stringBuilder.append("\n");
            }

            int endIndex = textCount * (maxLines - (lineCount - textLine)) - imageHoldCount(
                stringBuilder.length(), currentText.length()) - ELLIPSE.length();

            stringBuilder.append(currentText.substring(0,
                currentText.length() < endIndex ? currentText.length() : endIndex));

            boolean hasEllipse = false;
            if (lineCount > maxLines) {
                stringBuilder.append(ELLIPSE);
                hasEllipse = true;
            } else if (index < textArray.length - 1) {
                stringBuilder.append(ELLIPSE);
                hasEllipse = true;
            }
            if (hasEllipse) {
                HeightLightText lightText =
                    new HeightLightText.Builder(HeightLightText.LightType.LIGHT).insertIndex(
                        stringBuilder.length() - 3)
                        .lightLength(3)
                        .textColor(getContext().getResources().getColor(R.color.ct_4))
                        .build();
                if (lightTextList == null) {
                    lightTextList = new ArrayList<>();
                }
                lightTextList.add(lightText);
            }

            mainText = stringBuilder.toString();
            return;
        }
    }

    // 在指定区间内，图片占据的文字个数
    private int imageHoldCount(int startIndex, int length) {
        if (TextUtils.isEmpty(mainText) || lightTextList == null || lightTextList.isEmpty()) {
            return 0;
        }
        if (startIndex >= mainText.length() || startIndex <= 0 || length <= 0) {
            return 0;
        }
        int holdCount = 0;

        for (HeightLightText lightText : lightTextList) {
            if (lightText == null || lightText.lightType != HeightLightText.LightType.DRAWABLE) {
                continue;
            }
            if (lightText.insertIndex < startIndex || lightText.insertIndex > startIndex + length) {
                continue;
            }
            holdCount = holdCount + (int) (lightText.drawable.getIntrinsicWidth() / getTextSize());
        }
        return holdCount;
    }

    // 展示富媒体文本
    private void showMarkText() {
        if (TextUtils.isEmpty(mainText)) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);

        if (lightTextList == null || lightTextList.isEmpty()) {
            setText(mainText);
        } else {
            SpannableString spanString = new SpannableString(mainText);
            for (HeightLightText lightText : lightTextList) {
                if (lightText == null) {
                    continue;
                }
                switch (lightText.lightType) {
                    case HeightLightText.LightType.DRAWABLE:
                        loadInsertDrawable(spanString, lightText);
                        break;
                    case HeightLightText.LightType.INSERT:
                        break;
                    case HeightLightText.LightType.LIGHT:
                        loadInsetLightText(spanString, lightText);
                        break;
                }
            }
            setText(spanString);
        }
    }

    // 加载插入的高亮文字
    private void loadInsetLightText(@NonNull SpannableString spanString,
        @NonNull HeightLightText lightText) {
        if (lightText.insertIndex < 0
            || lightText.insertIndex > spanString.length()
            || lightText.lightLength <= 0) {
            return;
        }
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
            lightText.insertIndex + lightText.lightLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    // 加载插入的图标
    private void loadInsertDrawable(@NonNull SpannableString spanString,
        @NonNull HeightLightText lightText) {
        if (lightText.insertIndex < 0
            || lightText.insertIndex > spanString.length()
            || lightText.drawable == null) {
            return;
        }
        lightText.drawable.setBounds(0, 0, lightText.drawable.getIntrinsicWidth(),
            lightText.drawable.getIntrinsicHeight());
        ImageSpan img = new CenterImageSpan(lightText.drawable);
        spanString.setSpan(img, lightText.insertIndex, lightText.insertIndex + 1,
            ImageSpan.ALIGN_BASELINE);
    }
}