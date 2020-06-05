package com.example.wyyu.gitsamlpe.test.canvas.xfermode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-06-05.
 **/

public class CanvasXFerModeView extends View {

    public CanvasXFerModeView(Context context) {
        this(context, null);
    }

    public CanvasXFerModeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasXFerModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CanvasXFerModeView);
        try {
            int type = typedArray.getInteger(R.styleable.CanvasXFerModeView_modeType, 0);
            if (type >= 0 && type < MODE_ARRAY.length) {
                xFerMode = new PorterDuffXfermode(MODE_ARRAY[type]);
            } else {
                xFerMode = null;
            }
        } finally {
            typedArray.recycle();
        }

        rectLayer = new RectF(SCREEN_HALF + PADDING, PADDING, SCREEN_HALF + END, END);
        rect = new RectF(PADDING, PADDING, END, END);

        paint = new Paint();
        paint.setXfermode(null);
        paint.setAntiAlias(true);
    }

    private static final PorterDuff.Mode[] MODE_ARRAY = new PorterDuff.Mode[] {
        PorterDuff.Mode.CLEAR, PorterDuff.Mode.ADD, PorterDuff.Mode.DARKEN, PorterDuff.Mode.LIGHTEN,
        PorterDuff.Mode.MULTIPLY, PorterDuff.Mode.OVERLAY, PorterDuff.Mode.SCREEN,
        PorterDuff.Mode.XOR, PorterDuff.Mode.SRC, PorterDuff.Mode.SRC_OVER, PorterDuff.Mode.SRC_IN,
        PorterDuff.Mode.SRC_ATOP, PorterDuff.Mode.SRC_OUT, PorterDuff.Mode.DST,
        PorterDuff.Mode.DST_IN, PorterDuff.Mode.DST_OUT, PorterDuff.Mode.DST_OVER,
        PorterDuff.Mode.DST_ATOP,
    };

    private static final int SCREEN_HALF = UIUtils.getScreenWidth() / 2;
    private static final int PADDING = UIUtils.dpToPx(70.0f);
    private static final int CIRCLE = UIUtils.dpToPx(50.0f);
    private static final int END = UIUtils.dpToPx(170.0f);

    private PorterDuffXfermode xFerMode;
    private Paint paint;

    private RectF rectLayer;
    private RectF rect;

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xffb7d28d);

        // 在同一张画纸上绘制
        paint.setColor(0xffa39480);
        canvas.drawCircle(PADDING, PADDING, CIRCLE, paint);
        if (xFerMode != null) {
            paint.setXfermode(xFerMode);
        }
        paint.setColor(0xff09a4ff);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);

        // 在一张新的画纸上绘制
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        paint.setColor(0xffa39480);
        canvas.drawCircle(SCREEN_HALF + PADDING, PADDING, CIRCLE, paint);
        if (xFerMode != null) {
            paint.setXfermode(xFerMode);
        }
        paint.setColor(0xff09a4ff);
        canvas.drawRect(rectLayer, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
