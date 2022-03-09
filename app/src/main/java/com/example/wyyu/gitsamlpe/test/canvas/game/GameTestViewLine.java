package com.example.wyyu.gitsamlpe.test.canvas.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

public class GameTestViewLine extends View {

    public GameTestViewLine(Context context) {
        super(context);
        initView();
    }

    public GameTestViewLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameTestViewLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @IntDef({ShowType.SHOW_NUM, ShowType.SHOW_IMG, ShowType.SELECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
        int SHOW_NUM = 0;
        int SHOW_IMG = 1;
        int SELECTED = 2;
    }

    private static final int RES_ID = R.mipmap.image_test_1;
    private static final int COUNT = 6 * 2;

    private static final int DIVIDE = 12;
    private static final int LIST = 5;
    private static final int ROW = 4;

    private static final float SIZE_STROKE = UIUtils.dpToPx(1.0f);
    private static final int COLOR_PATH_N = 0x0a000000;
    private static final int COLOR_PATH_S = 0xffb7d28d;
    private static final float SIZE_TEXT = UIUtils.dpToPx(14.0f);
    private static final int COLOR_TEXT = 0xff010101;

    private Paint paintImage;
    private Paint paintText;
    private Paint paintPath;
    private Bitmap bitmap;
    private Path path;

    private SparseArray<RectF> viewArray;
    private SparseArray<Rect> imageArray;
    private int[] indexArray;
    // 0-展示序号，1-展示图片，2-被选中
    private int[] showArray;

    private void initView() {
        initObject();
        initData();
    }

    private void initObject() {
        bitmap = BitmapFactory.decodeResource(getResources(), RES_ID);
        paintImage = new Paint();

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextAlign(Paint.Align.LEFT);
        paintText.setTextSize(SIZE_TEXT);
        paintText.setColor(COLOR_TEXT);

        paintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeWidth(SIZE_STROKE);

        path = new Path();
    }

    private void initData() {
        imageArray = new SparseArray<>();
        viewArray = new SparseArray<>();
        indexArray = new int[COUNT];
        showArray = new int[COUNT];

        for (int index = 0; index < COUNT; index++) {
            viewArray.put(index, new RectF());
            imageArray.put(index, new Rect());
            indexArray[index] = index / 2;
            showArray[index] = ShowType.SHOW_NUM;
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

        float halfHeight = viewHeight * 1.0f / 2;
        float halfImage = imgHeight * 1.0f / 2;

        for (int index = 0; index < COUNT; index++) {
            if (index == 0) {
                RectF rectF = viewArray.get(index);
                rectF.left = 0;
                rectF.right = viewWidth;
                rectF.top = viewHeight + halfHeight + DIVIDE;
                rectF.bottom = rectF.top + viewHeight;

                Rect rect = imageArray.get(index);
                rect.left = 0;
                rect.right = imgWidth;
                rect.top = (int) (imgHeight + halfImage + DIVIDE);
                rect.bottom = rect.top + imgHeight;
            } else if (index <= 3) {
                RectF rectF = viewArray.get(index);
                rectF.left = viewWidth + DIVIDE;
                rectF.right = rectF.left + viewWidth;
                rectF.top = (viewHeight + DIVIDE) * (index - 1) + halfHeight;
                rectF.bottom = rectF.top + viewHeight;

                Rect rect = imageArray.get(index);
                rect.left = imgWidth + DIVIDE;
                rect.right = rect.left + imgWidth;
                rect.top = (int) ((imgHeight + DIVIDE) * (index - 1) + halfImage);
                rect.bottom = rect.top + imgHeight;
            } else if (index <= 7) {
                RectF rectF = viewArray.get(index);
                rectF.left = (viewWidth + DIVIDE) * 2;
                rectF.right = rectF.left + viewWidth;
                rectF.top = (viewHeight + DIVIDE) * (index - 4);
                rectF.bottom = rectF.top + viewHeight;

                Rect rect = imageArray.get(index);
                rect.left = (imgWidth + DIVIDE) * 2;
                rect.right = rect.left + imgWidth;
                rect.top = (imgHeight + DIVIDE) * (index - 4);
                rect.bottom = rect.top + imgHeight;
            } else if (index <= 10) {
                RectF rectF = viewArray.get(index);
                rectF.left = (viewWidth + DIVIDE) * 3;
                rectF.right = rectF.left + viewWidth;
                rectF.top = (viewHeight + DIVIDE) * (index - 8) + halfHeight;
                rectF.bottom = rectF.top + viewHeight;

                Rect rect = imageArray.get(index);
                rect.left = (imgWidth + DIVIDE) * 3;
                rect.right = rect.left + imgWidth;
                rect.top = (int) ((imgHeight + DIVIDE) * (index - 8) + halfImage);
                rect.bottom = rect.top + imgHeight;
            } else {
                RectF rectF = viewArray.get(index);
                rectF.left = (viewWidth + DIVIDE) * 4;
                rectF.right = rectF.left + viewWidth;
                rectF.top = viewHeight + halfHeight + DIVIDE;
                rectF.bottom = rectF.top + viewHeight;

                Rect rect = imageArray.get(index);
                rect.left = (imgWidth + DIVIDE) * 4;
                rect.right = rect.left + imgWidth;
                rect.top = (int) (imgHeight + halfImage + DIVIDE);
                rect.bottom = rect.top + imgHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            return;
        }
        for (int index = 0; index < COUNT; index++) {
            switch (showArray[index]) {
                case ShowType.SHOW_NUM:
                    RectF rectN = viewArray.get(index);
                    int xN = (int) (rectN.left + rectN.width() / 2);
                    int yN = (int) (rectN.top + rectN.height() / 2);
                    canvas.drawText(String.valueOf(indexArray[index]), xN, yN, paintText);

                    paintPath.setStyle(Paint.Style.FILL);
                    paintPath.setColor(COLOR_PATH_N);
                    path.reset();
                    path.addRect(rectN, Path.Direction.CCW);
                    canvas.drawPath(path, paintPath);
                    break;
                case ShowType.SELECTED:
                    RectF rectS = viewArray.get(index);
                    int xS = (int) (rectS.left + rectS.width() / 2);
                    int yS = (int) (rectS.top + rectS.height() / 2);
                    canvas.drawText(String.valueOf(indexArray[index]), xS, yS, paintText);

                    paintPath.setStyle(Paint.Style.STROKE);
                    paintPath.setColor(COLOR_PATH_S);
                    path.reset();
                    path.addRect(rectS, Path.Direction.CCW);
                    canvas.drawPath(path, paintPath);
                    break;
                case ShowType.SHOW_IMG:
                    canvas.drawBitmap(bitmap, imageArray.get(index), viewArray.get(index), paintImage);
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                checkClickIndex(index);
                break;
            }
        }
    }

    private void checkClickIndex(int index) {
        if (showArray[index] == 1) {
            return;
        }
        int selectedIndex = checkSelectedIndex();
        if (selectedIndex == index) {
            return;
        }
        if (selectedIndex == -1) {
            showArray[index] = ShowType.SELECTED;
        } else if (indexArray[index] == indexArray[selectedIndex]) {
            showArray[selectedIndex] = ShowType.SHOW_IMG;
            showArray[index] = ShowType.SHOW_IMG;
        } else {
            showArray[selectedIndex] = ShowType.SHOW_NUM;
            showArray[index] = ShowType.SELECTED;
        }
        invalidate();
    }

    private int checkSelectedIndex() {
        for (int index = 0; index < COUNT; index++) {
            if (showArray[index] == ShowType.SELECTED) {
                return index;
            }
        }
        return -1;
    }
}