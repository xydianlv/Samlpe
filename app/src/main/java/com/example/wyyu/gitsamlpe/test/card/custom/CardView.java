package com.example.wyyu.gitsamlpe.test.card.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/1/10.
 **/

public class CardView extends LinearLayout {

    public CardView(Context context) {
        super(context);
        initCardView(context, null);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCardView(context, attrs);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCardView(context, attrs);
    }

    private float cardShadowRadius;
    private float cardShadowX;
    private float cardShadowY;

    private int cardShadowColor;
    private int cardBackColor;

    // 所有角的 Radius 值
    private float cardRadiusAll;
    // 4个角的 Radius 值
    private float cardRadiusLT;
    private float cardRadiusRT;
    private float cardRadiusRB;
    private float cardRadiusLB;

    private float[] radiusArray;
    private int measureHeight;
    private int measureWidth;

    private Paint paint;
    private Path path;

    private void initCardView(Context context, AttributeSet attrs) {

        initBasicValue(context, attrs);

        initLayoutSetting();

        initRadiusArray();

        initConfigValue();
    }

    private void initBasicValue(Context context, AttributeSet attrs) {

        @SuppressLint("Recycle") TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CardView);

        cardShadowRadius = typedArray.getDimension(R.styleable.CardView_cardShadowRadius, 0);
        cardShadowX = typedArray.getDimension(R.styleable.CardView_cardShadowX, 0);
        cardShadowY = typedArray.getDimension(R.styleable.CardView_cardShadowY, 0);

        cardShadowColor = typedArray.getColor(R.styleable.CardView_cardShadowColor, 0x44242424);
        cardBackColor = typedArray.getColor(R.styleable.CardView_cardBackColor, 0x00000000);

        cardRadiusAll = typedArray.getDimension(R.styleable.CardView_cardRadiusAll, 0);

        cardRadiusLT = typedArray.getDimension(R.styleable.CardView_cardRadiusLeftTop, 0);
        cardRadiusRT = typedArray.getDimension(R.styleable.CardView_cardRadiusRightTop, 0);
        cardRadiusRB = typedArray.getDimension(R.styleable.CardView_cardRadiusRightBottom, 0);
        cardRadiusLB = typedArray.getDimension(R.styleable.CardView_cardRadiusLeftBottom, 0);
    }

    private void initLayoutSetting() {

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        setBackgroundColor(0x00000000);

        int offset = (int) cardShadowRadius;

        setPadding(offset, offset, offset, offset);
    }

    private void initRadiusArray() {

        radiusArray = new float[8];

        radiusArray[0] = cardRadiusLT == 0 ? cardRadiusAll : cardRadiusLT;
        radiusArray[1] = cardRadiusLT == 0 ? cardRadiusAll : cardRadiusLT;
        radiusArray[2] = cardRadiusRT == 0 ? cardRadiusAll : cardRadiusRT;
        radiusArray[3] = cardRadiusRT == 0 ? cardRadiusAll : cardRadiusRT;
        radiusArray[4] = cardRadiusRB == 0 ? cardRadiusAll : cardRadiusRB;
        radiusArray[5] = cardRadiusRB == 0 ? cardRadiusAll : cardRadiusRB;
        radiusArray[6] = cardRadiusLB == 0 ? cardRadiusAll : cardRadiusLB;
        radiusArray[7] = cardRadiusLB == 0 ? cardRadiusAll : cardRadiusLB;
    }

    private void initConfigValue() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(cardBackColor);

        paint.setShadowLayer(cardShadowRadius, cardShadowX, cardShadowY, cardShadowColor);

        path = new Path();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {

        drawShadow(canvas);

        super.onDraw(canvas);
    }

    private void drawShadow(Canvas canvas) {

        RectF rectF = new RectF(cardShadowRadius, cardShadowRadius, measureWidth - cardShadowRadius,
            measureHeight - cardShadowRadius);

        path.addRoundRect(rectF, radiusArray, Path.Direction.CW);

        canvas.drawPath(path, paint);

        // 且一下画布的路径，作为一个 ViewGroup，可以使其子 View 完全位于所限定的范围内
        canvas.clipPath(path);

        canvas.drawPath(path, paint);
    }
}
