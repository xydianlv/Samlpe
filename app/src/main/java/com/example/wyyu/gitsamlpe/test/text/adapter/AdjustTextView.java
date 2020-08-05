package com.example.wyyu.gitsamlpe.test.text.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;

/**
 * Created by wyyu on 2020-05-09.
 **/

public class AdjustTextView extends AppCompatTextView {

    public AdjustTextView(@NonNull Context context) {
        super(context);
    }

    public AdjustTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustTextView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String ELLIPSE = " …全文";

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getLineCount() > getMaxLines()) {
            adaptTextShow();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void adaptTextShow() {
        int textCount = 0;
        for (int index = 0; index < getMaxLines(); index++) {
            textCount = textCount + getLayout().getLineEnd(index) - getLayout().getLineStart(index);
        }
        String content = getText().toString().substring(0, textCount);
        if (content.charAt(content.length() - 1) == '\n') {
            content = content.substring(0, content.length() - 1) + ELLIPSE;
        } else {
            content = content.substring(0, content.length() - ELLIPSE.length()) + ELLIPSE;
        }
        SpannableString spanString = new SpannableString(content);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override public void onClick(@NonNull View widget) {
                UToast.showShort(getContext(), "OnClickEllipse");
            }

            @Override public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xffff0000);
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        spanString.setSpan(clickableSpan, spanString.length() - 3, spanString.length(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());

        setText(spanString);
    }
}
