package com.example.wyyu.gitsamlpe.test.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-01-09.
 **/

public class CanvasTextView extends View {

    public CanvasTextView(Context context) {
        super(context);
        initValue();
    }

    public CanvasTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public CanvasTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private static final int SIZE_LINE_HEIGHT = UIUtils.dpToPx(50.0f);
    private static final int SIZE_LINE_LEFT = UIUtils.dpToPx(24.0f);
    private static final int SIZE_LINE_TOP = UIUtils.dpToPx(36.0f);

    private static final float SIZE_STROKE = UIUtils.dpToPx(1.0f);
    private static final float SIZE_TEXT = UIUtils.dpToPx(22.0f);
    private static final int COLOR_TEXT = 0xffffa500;

    private Paint paintStroke;
    private Paint paintFill;
    private Paint paintAll;

    private void initValue() {

        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(SIZE_STROKE);
        paintStroke.setTextAlign(Paint.Align.LEFT);
        paintStroke.setTextSize(SIZE_TEXT);
        paintStroke.setColor(COLOR_TEXT);

        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setTextAlign(Paint.Align.LEFT);
        paintFill.setTextSize(SIZE_TEXT);
        paintFill.setColor(COLOR_TEXT);

        paintAll = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAll.setStyle(Paint.Style.FILL_AND_STROKE);
        paintAll.setStrokeWidth(SIZE_STROKE);
        paintAll.setTextAlign(Paint.Align.LEFT);
        paintAll.setTextSize(SIZE_TEXT);
        paintAll.setColor(COLOR_TEXT);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画一个只有线框的文字
        canvas.drawText("PaintStroke", SIZE_LINE_LEFT, SIZE_LINE_TOP, paintStroke);

        // 画一个只有实线的文字
        canvas.drawText("PaintFill", SIZE_LINE_LEFT, SIZE_LINE_TOP + SIZE_LINE_HEIGHT, paintFill);

        // 画一个实线带有线框的文字
        canvas.drawText("PaintAll", SIZE_LINE_LEFT, SIZE_LINE_TOP + SIZE_LINE_HEIGHT * 2, paintAll);

        // 画一个粗体的实线文字
        paintFill.setFakeBoldText(true);
        canvas.drawText("PaintFillBold", SIZE_LINE_LEFT, SIZE_LINE_TOP + SIZE_LINE_HEIGHT * 3,
            paintFill);

        // 画一个带下划线实线文字
        paintFill.setUnderlineText(true);
        paintFill.setFakeBoldText(false);
        canvas.drawText("PaintFillUnderLine", SIZE_LINE_LEFT, SIZE_LINE_TOP + SIZE_LINE_HEIGHT * 4,
            paintFill);

        // 画一个倾斜的实线文字
        paintFill.setUnderlineText(false);
        paintFill.setTextSkewX(-0.25f);
        canvas.drawText("PaintFillWidthSkew", SIZE_LINE_LEFT, SIZE_LINE_TOP + SIZE_LINE_HEIGHT * 5,
            paintFill);

        // 画一个带删除线的实线文字
        paintFill.setTextSkewX(0.0f);
        paintFill.setStrikeThruText(true);
        canvas.drawText("PaintFillWidthDeleteLine", SIZE_LINE_LEFT,
            SIZE_LINE_TOP + SIZE_LINE_HEIGHT * 6, paintFill);
    }
}
