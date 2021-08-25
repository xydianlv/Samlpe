package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.wyyu.gitsamlpe.R;

public class GameTestView extends View {

    public GameTestView(Context context) {
        super(context);
        initView();
    }

    public GameTestView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameTestView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private static final int RES_ID = R.mipmap.image_test_1;
    private static final int WIDTH_DIVIDE = 12;
    private static final int LIST = 4;
    private static final int ROW = 4;

    private Bitmap bitmap;
    private Paint paint;

    private RectF rectImage;
    private Rect rectBitmap;

    private int bitmapSize;
    private int imageSize;

    private SparseArray<RectF> viewArray;
    private SparseArray<Rect> imageArray;

    private int[] indexArray;

    private void initView() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        bitmapSize = bitmap.getWidth() / LIST;

        rectImage = new RectF();
        rectBitmap = new Rect();
        paint = new Paint();

        indexArray = new int[ROW * LIST];
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
    }

    @Override
    public void onMeasure(int valueMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(valueMeasureSpec, valueMeasureSpec);

        int viewWidth = getMeasuredWidth();

        imageArray.clear();
        viewArray.clear();

        for (int index = 0; index < indexArray.length; index++) {
            indexArray[index] = index;



        }
        imageSize = (viewWidth - WIDTH_DIVIDE * (LIST - 1)) / LIST;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null) {
            return;
        }
        for (int index = 0; index < ROW; index++) {
            for (int position = 0; position < LIST; position++) {
                rectBitmap.left = bitmapSize * position;
                rectBitmap.top = bitmapSize * index;
                rectBitmap.right = rectBitmap.left + bitmapSize;
                rectBitmap.bottom = rectBitmap.top + bitmapSize;

                rectImage.left = (imageSize + WIDTH_DIVIDE) * position;
                rectImage.top = (imageSize + WIDTH_DIVIDE) * index;
                rectImage.right = rectImage.left + imageSize;
                rectImage.bottom = rectImage.top + imageSize;

                canvas.drawBitmap(bitmap, rectBitmap, rectImage, paint);
            }
        }

        for (int index = 0; index < indexArray.length; index++) {
            canvas.drawBitmap(bitmap, imageArray.get(index), viewArray.get(index), paint);
        }
    }
}
