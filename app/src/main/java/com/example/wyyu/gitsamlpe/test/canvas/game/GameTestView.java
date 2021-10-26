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
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.wyyu.gitsamlpe.R;

import java.util.Random;

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
    private static final int DIVIDE = 12;
    private static final int LIST = 3;
    private static final int ROW = 3;
    private static final int COUNT = LIST * ROW;

    private Bitmap bitmap;
    private Paint paint;

    private SparseArray<RectF> viewArray;
    private SparseArray<Rect> imageArray;
    private int[] indexArray;
    private boolean hasSort;

    private void initView() {
        initObject();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paint = new Paint();
    }

    private void initData() {
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
        indexArray = new int[COUNT];

        for (int index = 0; index < COUNT; index++) {
            viewArray.put(index, new RectF());
            imageArray.put(index, new Rect());
            indexArray[index] = index;
        }

        Random random = new Random();
        int index = COUNT - 2;

        while (index > 0) {
            int data = random.nextInt(index);

            int value = indexArray[data];
            indexArray[data] = indexArray[index + 1];
            indexArray[index + 1] = value;
            index--;
        }
        sumNumber();
        hasSort = false;
    }

    private void sumNumber() {
        int inNum = 0;
        int zeroPosition = 0;
        for (int index = 0; index < COUNT; index++) {
            for (int i = index + 1; i < COUNT; i++) {
                if (indexArray[index] == 0) {
                    zeroPosition = index;
                }
                if (indexArray[index] < indexArray[i]) {
                    inNum++;
                }
            }
        }
        if (inNum % 2 != 0) {
            if (zeroPosition == 0) {
                switchPosition(zeroPosition, zeroPosition + 1);
            } else if (zeroPosition == COUNT - 1) {
                switchPosition(zeroPosition, zeroPosition - 1);
            } else {
                switchPosition(zeroPosition, zeroPosition + 1);
            }
        }
    }

    private void switchPosition(int indexA, int indexB) {
        int value = indexArray[indexA];
        indexArray[indexA] = indexArray[indexB];
        indexArray[indexB] = value;
    }

    @Override
    public void onMeasure(int valueMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(valueMeasureSpec, valueMeasureSpec);

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        int imgHeight = bitmap.getHeight();
        int imgWidth = bitmap.getWidth();

        float aspectView = viewWidth * 1.0f / viewHeight;
        float aspectImg = imgWidth * 1.0f / imgHeight;

        viewWidth = (viewWidth - DIVIDE * (LIST - 1)) / LIST;
        viewHeight = (viewHeight - DIVIDE * (ROW - 1)) / ROW;

        if (aspectView > aspectImg) {
            imgHeight = ((int) (imgWidth * aspectView) - (DIVIDE * (ROW - 1))) / ROW;
            imgWidth = (imgWidth - (DIVIDE * (LIST - 1))) / LIST;
        } else {
            imgWidth = ((int) (imgHeight * aspectView) - (DIVIDE * (LIST - 1))) / LIST;
            imgHeight = (imgHeight - (DIVIDE * (ROW - 1))) / ROW;
        }

        for (int index = 0; index < COUNT; index++) {

            RectF rectF = viewArray.get(index);
            rectF.left = (viewWidth + DIVIDE) * (index % LIST);
            rectF.right = rectF.left + viewWidth;
            rectF.top = (viewHeight + DIVIDE) * ((int) (index / LIST));
            rectF.bottom = rectF.top + viewHeight;

            Rect rect = imageArray.get(index);
            rect.left = imgWidth * (index % LIST);
            rect.right = rect.left + imgWidth;
            rect.top = imgHeight * ((int) (index / LIST));
            rect.bottom = rect.top + imgHeight;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null) {
            return;
        }

        for (int index = 0; index < COUNT; index++) {
            int drawIndex = indexArray[index];
            if (drawIndex == 0 && !hasSort) {
                continue;
            }
            canvas.drawBitmap(bitmap, imageArray.get(drawIndex), viewArray.get(index), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (hasSort) {
            return super.onTouchEvent(event);
        }
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        judgeClickCard(event);
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void judgeClickCard(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        for (int index = 0; index < COUNT; index++) {
            RectF rectF = viewArray.get(index);
            if (rectF.left < x && rectF.right > x && rectF.top < y && rectF.bottom > y) {
                if (indexArray[index] == 0) {
                    return;
                }
                judgeAndSwitch(index);
                break;
            }
        }
    }

    private void judgeAndSwitch(int clickIndex) {
        int preIndex = clickIndex % LIST == 0 ? -1 : clickIndex - 1;
        if (preIndex != -1 && indexArray[preIndex] == 0) {
            switchIndex(clickIndex, preIndex);
            return;
        }
        int nextIndex = clickIndex % LIST == (LIST - 1) ? -1 : clickIndex + 1;
        if (nextIndex != -1 && indexArray[nextIndex] == 0) {
            switchIndex(clickIndex, nextIndex);
            return;
        }
        int upIndex = clickIndex < LIST ? -1 : clickIndex - LIST;
        if (upIndex != -1 && indexArray[upIndex] == 0) {
            switchIndex(clickIndex, upIndex);
            return;
        }
        int downIndex = clickIndex >= LIST * (ROW - 1) ? -1 : clickIndex + LIST;
        if (downIndex != -1 && indexArray[downIndex] == 0) {
            switchIndex(clickIndex, downIndex);
        }
    }

    private void switchIndex(int indexA, int indexB) {
        int value = indexArray[indexA];
        indexArray[indexA] = indexArray[indexB];
        indexArray[indexB] = value;
        judgeHasSort();
        invalidate();
    }

    private void judgeHasSort() {
        hasSort = true;
        for (int index = 1; index < COUNT; index++) {
            if (indexArray[index] < indexArray[index - 1]) {
                hasSort = false;
                break;
            }
        }
    }
}
