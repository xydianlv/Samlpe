package com.example.wyyu.gitsamlpe.test.text.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class GradientScrollView extends ScrollView {

    public GradientScrollView(Context context) {
        this(context, null);
    }

    public GradientScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScrollView();
    }

    private static final int HEIGHT_SHADOW = UIUtils.dpToPx(64.0f);

    private LinearGradient gradient;
    private Xfermode xfermode;
    private Paint paint;

    private void initScrollView() {
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint = new Paint();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        if (xfermode != null) {
            paint.setXfermode(xfermode);
        }
        if (gradient == null) {
            gradient =
                new LinearGradient(0, getHeight() - HEIGHT_SHADOW, 0, getHeight(), new int[] {
                    Color.BLACK, Color.TRANSPARENT
                }, null, Shader.TileMode.CLAMP);
        }
        canvas.drawRect(0, getHeight() - HEIGHT_SHADOW, getWidth(), getHeight(), paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
