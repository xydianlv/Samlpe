package com.example.wyyu.gitsamlpe.test.canvas.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class CanvasXFerModeShadow extends View {

    public CanvasXFerModeShadow(Context context) {
        this(context, null);
    }

    public CanvasXFerModeShadow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasXFerModeShadow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private static final int PADDING = UIUtils.dpToPx(20.0f);
    private static final int END = UIUtils.dpToPx(160.0f);

    private LinearGradient gradient;
    private Xfermode xfermode;
    private Paint paint;

    private void initValue() {
        gradient = new LinearGradient(PADDING, END, PADDING, END + PADDING, new int[] {
            Color.BLACK, Color.TRANSPARENT
        }, null, Shader.TileMode.CLAMP);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint = new Paint();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xffb7d28d);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        paint.setColor(0xffa39480);
        canvas.drawRect(PADDING, PADDING, END + PADDING, END + PADDING, paint);
        if (xfermode != null) {
            paint.setXfermode(xfermode);
        }
        if (gradient != null) {
            paint.setShader(gradient);
        }
        canvas.drawRect(PADDING, END, PADDING + END, PADDING + END, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
