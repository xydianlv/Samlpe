package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.ULog;

/**
 * Created by wyyu on 2018/8/21.
 * 具有 行数限制、插入高亮字段、尾部续接更多 功能的 TextView
 **/

public class PostContentView extends android.support.v7.widget.AppCompatTextView {

    public PostContentView(Context context) {
        super(context);
        initContentView();
    }

    public PostContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContentView();
    }

    public PostContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContentView();
    }

    private static final String ELLIPSE = " ... 全文";
    private static final String SPACE = " ";

    // 点击事件监听
    private GestureDetector gestureDetector;
    // 添加可点击字段
    private SpannableString spanString;
    // 话题片段点击控制
    private boolean isClickedTopic;
    // 是否已经绘制
    private boolean needDraw;

    // 点击事件回调
    private OnContentTextClickListener clickListener;
    // 文字数据
    private String mainText;
    // 最大行数
    private int maxLines;

    private void initContentView() {
        gestureDetector =
            new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onDoubleTap(MotionEvent e) {
                    return true;
                }

                @Override public void onLongPress(MotionEvent e) {
                    if (clickListener != null) {
                        clickListener.onLongClick();
                    }
                }

                @Override public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (isClickedTopic) {
                        isClickedTopic = false;
                        return true;
                    }
                    // 得做一下判断，如果点到了 话题名称，则跳过事件回调
                    if (clickListener != null) {
                        clickListener.onClick();
                        return true;
                    }
                    return false;
                }
            });
        setHint("");
    }

    /**
     * 为 ContentTextView 传入数据
     *
     * @param mainText 该 TextView 需要展示的数据主体
     * @param hasLimit 是否需要限制行数
     * @param maxLines 最大行数，超过行数尾部显示 "双击展开"
     * @param clickListener ContentView 点击事件监听
     * @param lightTextArray 高亮字段列表，无高亮字段时传入 null
     */
    public void setContentData(@NonNull String mainText, boolean hasLimit, int maxLines,
        OnContentTextClickListener clickListener, HighLightText... lightTextArray) {

        if (TextUtils.isEmpty(mainText)) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);

        this.clickListener = clickListener;
        this.maxLines = maxLines;
        this.mainText = mainText;

        initSpanText(lightTextArray);
        isClickedTopic = false;

        if (TextUtils.isEmpty(spanString)) {
            return;
        }
        if (hasLimit) {
            needDraw = true;
            invalidate();
        } else {
            needDraw = false;
            setText(spanString);
        }
    }

    // 将高亮字段添加进 TextValue
    private void initSpanText(HighLightText... lightTextArray) {

        if (lightTextArray == null || lightTextArray.length == 0) {
            spanString = new SpannableString(mainText);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(mainText);
        int funIndex = 0;
        for (HighLightText lightText : lightTextArray) {
            if (lightText != null) {
                stringBuilder.insert(lightText.index + funIndex, lightText.text);
                lightText.index = lightText.index + funIndex;
                funIndex = funIndex + lightText.text.length();
            }
        }
        stringBuilder.append(SPACE);
        spanString = new SpannableString(stringBuilder.toString());
        for (final HighLightText lightText : lightTextArray) {
            if (lightText != null) {
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override public void onClick(View widget) {
                        isClickedTopic = true;
                        if (lightText.clickListener != null) {
                            lightText.clickListener.onClick(widget);
                        }
                    }

                    @Override public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(lightText.lightColor);
                        ds.setUnderlineText(false);
                        ds.clearShadowLayer();
                    }
                };
                spanString.setSpan(clickableSpan, lightText.index,
                    lightText.index + lightText.text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        spanString.setSpan(new ForegroundColorSpan(0x00000000), stringBuilder.length() - 1,
            stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (needDraw) {
            needDraw = false;
            setAdapterShow();
        }
        super.onDraw(canvas);
    }

    // 判断是否显示 "全文"
    private void setAdapterShow() {

        // TextView 当前显示宽度
        int showWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        if (showWidth == 0) {
            setText(spanString);
            return;
        }

        String[] funArray = mainText.split("\n");
        int[] lineArray = new int[funArray.length];

        TextPaint textPaint = getPaint();
        int lines = 0;

        for (int index = 0; index < funArray.length; index++) {
            float textWidth = textPaint.measureText(funArray[index]);
            lineArray[index] = (int) textWidth / showWidth + 1;
            lines = lines + lineArray[index];
        }

        int percent = (int) textPaint.measureText(mainText) / showWidth;

        if (lines > maxLines && percent != 0) {
            int expect = mainText.length() / percent;
            String funText = "";
            int funLines = 0;

            for (int index = 0; index < funArray.length; index++) {
                if (funLines + lineArray[index] < maxLines) {
                    funLines = funLines + lineArray[index];
                    funText = funText + funArray[index] + "\n";
                } else {
                    funLines = maxLines - funLines;
                    int textNum = expect * funLines;
                    textNum =
                        textNum > funArray[index].length() ? funArray[index].length() : textNum;
                    funText = funText + funArray[index].subSequence(0, textNum);
                    break;
                }
            }
            SpannableString spannableString = new SpannableString(funText + ELLIPSE);
            try {
                if (spannableString.length() > 2) {
                    spannableString.setSpan(new ForegroundColorSpan(0xfffe698d),
                        spannableString.length() - 2, spannableString.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setText(spannableString);
                }
            } catch (Exception ex) {
                ULog.show(ex.getMessage());
            }
        } else {
            setText(spanString);
        }
    }

    public interface OnContentTextClickListener {

        void onLongClick();

        void onClick();
    }

    public static class HighLightText {

        // 高亮字段所在位置
        public int index;
        // 高亮字段数据
        public String text;
        // 高亮字段颜色
        public int lightColor;
        // 高亮字段点击事件
        public OnClickListener clickListener;

        public HighLightText(int index, String text, int color, OnClickListener clickListener) {
            this.index = index;
            this.text = text;
            this.lightColor = color;
            this.clickListener = clickListener;
        }
    }
}
