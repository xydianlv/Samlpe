package com.example.wyyu.gitsamlpe.test.text;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2020-01-02.
 **/

public class MarqueeListView extends View implements LifecycleObserver {

    public MarqueeListView(Context context) {
        super(context);
        initValue();
    }

    public MarqueeListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public MarqueeListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private static final int STRUCT_DIVIDE = UIUtils.dpToPx(32.0f);
    private static final int SPACE_DIVIDE = UIUtils.dpToPx(2.0f);
    private static final int MSG_DEFAULT = 0;

    private static class TextStruct {

        private String name;
        private String text;

        private float nameIndex;
        private float textIndex;

        private TextStruct(String nameValue, String textValue, float nameIndex, float textIndex) {
            this.name = nameValue;
            this.text = textValue;
            this.nameIndex = nameIndex;
            this.textIndex = textIndex;
        }
    }

    private WeakHandler handler;
    private Paint paintName;
    private Paint paintText;
    private Rect rect;

    private List<TextStruct> structList;
    private float allLength;

    private boolean onAnim;
    private float x;
    private float y;

    private void initValue() {
        initBasicValue();
        initPaintName();
        initPaintText();
        initHandler();
    }

    private void initBasicValue() {
        structList = new LinkedList<>();
        allLength = 0.0f;

        onAnim = false;
        x = 40.0f;
        y = 40.0f;

        rect = new Rect();
    }

    private void initPaintName() {
        paintName = new Paint();
        paintName.setTextSize(UIUtils.dpToPx(12.0f));
        paintName.setColor(0xffB27100);
        paintName.setAntiAlias(true);
        paintName.setStyle(Paint.Style.FILL_AND_STROKE);
        paintName.setStrokeWidth(0.4f);
        paintName.setTextAlign(Paint.Align.LEFT);
    }

    private void initPaintText() {
        paintText = new Paint();
        paintText.setTextSize(UIUtils.dpToPx(12.0f));
        paintText.setColor(0xffB27100);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextAlign(Paint.Align.LEFT);
    }

    private void initHandler() {
        handler = new WeakHandler(msg -> {
            if (msg == null || handler == null) {
                return;
            }
            x = x - 2.0f;
            invalidate();

            if (x < -allLength) {
                handler.removeMessages(MSG_DEFAULT);
                setVisibility(INVISIBLE);
                onAnim = false;
            } else {
                handler.sendEmptyMessageDelayed(MSG_DEFAULT, 10);
            }
        });
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (structList == null || structList.isEmpty()) {
            return;
        }
        for (TextStruct struct : structList) {
            canvas.drawText(struct.name, x + struct.nameIndex, y, paintName);
            canvas.drawText(struct.text, x + struct.textIndex, y, paintText);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) public void destroyAnim() {
        if (handler != null) {
            handler.removeMessages(MSG_DEFAULT);
        }
        handler = null;
        onAnim = false;
    }

    public void addNextValue(String nameValue, String textValue) {
        if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(textValue)) {
            return;
        }
        if (structList == null) {
            structList = new LinkedList<>();
        }
        structList.add(loadStructValue(nameValue, textValue));
        if (handler == null) {
            initHandler();
        }
        if (!onAnim) {
            handler.sendEmptyMessageDelayed(MSG_DEFAULT, 10);
            onAnim = true;
        }
    }

    private TextStruct loadStructValue(String nameValue, String textValue) {
        if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(textValue)) {
            return null;
        }
        float nameIndex = allLength == 0.0f ? 0.0f : allLength + STRUCT_DIVIDE;
        float textIndex = nameIndex + getContentWidth(nameValue, true) + SPACE_DIVIDE;

        allLength = textIndex + getContentWidth(textValue, false);

        return new TextStruct(nameValue, textValue, nameIndex, textIndex);
    }

    private float getContentWidth(String textValue, boolean forName) {
        if (TextUtils.isEmpty(textValue)) {
            return 0.0f;
        }
        if (rect == null) {
            rect = new Rect();
        }
        if (forName) {
            if (paintName == null) {
                initPaintName();
            }
            paintName.getTextBounds(textValue, 0, textValue.length(), rect);
        } else {
            if (paintText == null) {
                initPaintName();
            }
            paintText.getTextBounds(textValue, 0, textValue.length(), rect);
        }
        return rect.width();
    }
}
