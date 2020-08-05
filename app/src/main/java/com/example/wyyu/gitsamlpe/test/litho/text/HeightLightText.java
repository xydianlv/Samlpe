package com.example.wyyu.gitsamlpe.test.litho.text;

import android.graphics.drawable.Drawable;
import androidx.annotation.IntDef;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019-11-01.
 **/

public class HeightLightText {

    @IntDef({ LightType.LIGHT, LightType.INSERT, LightType.DRAWABLE })
    @Retention(RetentionPolicy.SOURCE) public @interface LightType {

        // 指定文本中的一段字符串高亮，不对原文本做改动
        int LIGHT = 0;

        // 向原文本中插入一段高亮问题
        int INSERT = 1;

        // 向原文本中插入一个高亮图标
        int DRAWABLE = 2;
    }

    // 高亮类型
    public @LightType int lightType;

    // 插入位置
    public int insertIndex;

    // 指定高亮字符串长度
    public int lightLength;

    // 插入文本的文字颜色
    public int textColor;

    // 插入的字符串
    public String insertText;

    // 插入的图标
    public Drawable drawable;

    // 高亮部分点击事件
    public View.OnClickListener clickListener;

    private HeightLightText() {

    }

    public static final class Builder {

        private HeightLightText heightLightText;

        public Builder(@LightType int lightType) {
            heightLightText = new HeightLightText();
            heightLightText.lightType = lightType;
        }

        public Builder insertIndex(int insertIndex) {
            heightLightText.insertIndex = insertIndex;
            return this;
        }

        public Builder lightLength(int lightLength) {
            heightLightText.lightLength = lightLength;
            return this;
        }

        public Builder textColor(int textColor) {
            heightLightText.textColor = textColor;
            return this;
        }

        public Builder insertText(String insertText) {
            heightLightText.insertText = insertText;
            return this;
        }

        public Builder insertDrawable(Drawable drawable) {
            heightLightText.drawable = drawable;
            return this;
        }

        public Builder clickListener(View.OnClickListener clickListener) {
            heightLightText.clickListener = clickListener;
            return this;
        }

        public HeightLightText build() {
            return heightLightText;
        }
    }
}
