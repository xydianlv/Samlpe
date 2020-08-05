package com.example.wyyu.gitsamlpe.framework.viewpager.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class CircleView extends View {

    private float radius;
    private Paint paint;

    public CircleView(Context context) {
        super(context);
        initCircleView();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCircleView();
    }
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCircleView();
    }

    private void initCircleView() {

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(radius, radius, radius, paint);

        super.onDraw(canvas);
    }

    void setCircleRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    void setCircleColor(int color) {
        paint.setColor(color);
        invalidate();
    }
}
