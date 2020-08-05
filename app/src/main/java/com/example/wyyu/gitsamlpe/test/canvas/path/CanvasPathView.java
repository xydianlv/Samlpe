package com.example.wyyu.gitsamlpe.test.canvas.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-01-09.
 **/

public class CanvasPathView extends View {

    public CanvasPathView(Context context) {
        super(context);
        initValue();
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private static final int SIZE_LINE_LENGTH = UIUtils.dpToPx(240.0f);
    private static final int SIZE_LINE_HEIGHT = UIUtils.dpToPx(50.0f);
    private static final int SIZE_LINE_DIVIDE = UIUtils.dpToPx(10.0f);
    private static final int SIZE_LINE_LEFT = UIUtils.dpToPx(24.0f);

    private static final float SIZE_STROKE = UIUtils.dpToPx(1.0f);
    private static final int COLOR_PATH = 0xffa39480;

    private Paint[] paintArray;
    private RectF[] rectFArray;
    private Path[] pathArray;

    private float[] angleArray;

    private void initValue() {

        paintArray = new Paint[3];
        for (int index = 0; index < 3; index++) {
            paintArray[index] = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintArray[index].setColor(COLOR_PATH);
            if (index == 0) {
                paintArray[index].setStyle(Paint.Style.STROKE);
                paintArray[index].setStrokeWidth(SIZE_STROKE);
            }
            if (index == 1) {
                paintArray[index].setStyle(Paint.Style.FILL);
            }
            if (index == 2) {
                paintArray[index].setStyle(Paint.Style.FILL_AND_STROKE);
                paintArray[index].setStrokeWidth(SIZE_STROKE);
            }
        }

        int drawCount = 7;

        rectFArray = new RectF[drawCount];
        for (int index = 0; index < drawCount; index++) {
            rectFArray[index] =
                new RectF(SIZE_LINE_LEFT, SIZE_LINE_DIVIDE + SIZE_LINE_HEIGHT * (index + 2),
                    SIZE_LINE_LEFT + SIZE_LINE_LENGTH, SIZE_LINE_HEIGHT * (index + 3));
        }

        pathArray = new Path[drawCount + 2];
        for (int index = 0; index < drawCount + 2; index++) {
            pathArray[index] = new Path();
        }

        angleArray = new float[] { 20, 20, 40, 40, 30, 30, 10, 10 };
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画一条直线
        pathArray[0].moveTo(SIZE_LINE_LEFT, SIZE_LINE_DIVIDE * 2);
        pathArray[0].lineTo(SIZE_LINE_LEFT + SIZE_LINE_LENGTH, SIZE_LINE_DIVIDE);
        canvas.drawPath(pathArray[0], paintArray[0]);

        // 画一条直线，事实证明，用 Paint.Style.FILL 画线画不出来
        pathArray[1].moveTo(SIZE_LINE_LEFT, SIZE_LINE_DIVIDE * 2 + SIZE_LINE_HEIGHT);
        pathArray[1].lineTo(SIZE_LINE_LEFT + SIZE_LINE_LENGTH, SIZE_LINE_DIVIDE + SIZE_LINE_HEIGHT);
        canvas.drawPath(pathArray[1], paintArray[1]);

        // 画一个框，用 Paint.Style.FILL_AND_STROKE 和 Paint.Style.FILL 画框会填充整个矩形
        pathArray[2].addRect(rectFArray[0], Path.Direction.CCW);
        canvas.drawPath(pathArray[2], paintArray[2]);

        // 画一个框，采用 Path.Direction.CW 时 Stroke 会绘制在框内部
        pathArray[3].addRect(rectFArray[1], Path.Direction.CW);
        canvas.drawPath(pathArray[3], paintArray[2]);

        // 画一个框，采用 Paint.Style.STROKE 会画出一个不填充的矩形
        pathArray[4].addRect(rectFArray[2], Path.Direction.CCW);
        canvas.drawPath(pathArray[4], paintArray[0]);

        // 画一个圆角矩形
        // 角度数组为 float[]{x1,y1,x2,y2,x3,y3,x4,y4}
        // 1、2、3、4 分别表示左上、右上、右下、左下，x 代表椭圆角的横轴半径，y 代表椭圆角的纵轴半径
        pathArray[5].addRoundRect(rectFArray[3], angleArray, Path.Direction.CCW);
        canvas.drawPath(pathArray[5], paintArray[0]);

        // 画一个圆角矩形，rx、ry ，四个角公用这个横轴半径值和纵轴半径值
        pathArray[6].addRoundRect(rectFArray[4], 40, 40, Path.Direction.CCW);
        canvas.drawPath(pathArray[6], paintArray[0]);

        // 画一个圆，x、y、radius 分别代表 圆心坐标 和 圆半径
        float x = SIZE_LINE_LEFT + SIZE_LINE_LENGTH * 1.0f / 2;
        float y = rectFArray[5].top + SIZE_LINE_HEIGHT * 1.0f / 2;
        float radius = (SIZE_LINE_HEIGHT - SIZE_LINE_DIVIDE) * 1.0f / 2;
        pathArray[7].addCircle(x, y, radius, Path.Direction.CCW);
        canvas.drawPath(pathArray[7], paintArray[2]);

        // 画一个椭圆
        pathArray[8].addOval(rectFArray[6], Path.Direction.CCW);
        canvas.drawPath(pathArray[8], paintArray[2]);
    }
}
