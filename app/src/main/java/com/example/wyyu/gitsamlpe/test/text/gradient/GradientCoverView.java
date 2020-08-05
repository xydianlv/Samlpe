package com.example.wyyu.gitsamlpe.test.text.gradient;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class GradientCoverView extends View {

    public GradientCoverView(Context context) {
        this(context, null);
    }

    public GradientCoverView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientCoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.GradientCoverView);

        try {
            bottomSize =
                typedArray.getDimensionPixelSize(R.styleable.GradientCoverView_bottomShadowY, 0);
            topSize = typedArray.getDimensionPixelSize(R.styleable.GradientCoverView_topShadowY, 0);
            radius = typedArray.getFloat(R.styleable.GradientCoverView_imgRadius, 0);
            resize = typedArray.getInteger(R.styleable.GradientCoverView_resize, 0);
            resId = typedArray.getResourceId(R.styleable.GradientCoverView_resId, 0);
        } finally {
            typedArray.recycle();
        }

        paint = new Paint();
        rectF = new Rect();
        rectS = new Rect();

        radius = radius < 0 ? 0 : (radius > 25 ? 25 : 25);
        height = 0;
        width = 0;
    }

    private static final int DEFAULT_RES_ID = R.mipmap.image_test_1;

    // 位图，负责绘制高斯模糊背景
    private Bitmap bitmap;
    // 画笔，负责透明度控制
    private Paint paint;
    // 绘制区域控制
    private Rect rectF;
    // 绘制区域控制
    private Rect rectS;

    // 控件高度
    private int height;
    // 控件宽度
    private int width;

    // 底部渐变高度
    private int bottomSize;
    // 高斯模糊模糊度，取值 [0.0f，25.0f]
    private float radius;
    // 顶部渐变高度
    private int topSize;
    // 图片缩放尺寸
    private int resize;
    // 高斯模糊图片资源
    private int resId;

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null) {
            blurBitmapTest();
            return;
        }

        if (rectF == null) {
            rectF = new Rect();
        }
        if (rectS == null) {
            rectS = new Rect();
        }
        for (int index = 0; index <= topSize; index++) {
            paint.setAlpha((int) (((topSize - index) * 1.0f / topSize) * 255));
            rectF.left = 0;
            rectS.left = 0;
            rectF.top = index;
            rectS.top = index;
            rectF.bottom = index + 1;
            rectS.bottom = index + 1;
            rectF.right = width;
            rectS.right = width;
            canvas.drawBitmap(bitmap, rectF, rectS, paint);
        }
        paint.setAlpha(0);
        rectF.left = 0;
        rectS.left = 0;
        rectF.top = topSize;
        rectS.top = topSize;
        rectF.bottom = height - bottomSize;
        rectS.bottom = height - bottomSize;
        rectF.right = width;
        rectS.right = width;
        canvas.drawBitmap(bitmap, rectF, rectS, paint);

        for (int index = height - bottomSize; index <= height; index++) {
            paint.setAlpha((int) (((bottomSize + index - height) * 1.0f / bottomSize) * 255));
            rectF.left = 0;
            rectS.left = 0;
            rectF.top = index;
            rectS.top = index;
            rectF.bottom = index + 1;
            rectS.bottom = index + 1;
            rectF.right = width;
            rectS.right = width;
            canvas.drawBitmap(bitmap, rectF, rectS, paint);
        }
    }

    private void blurBitmap() {

        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, resize, resize, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript script = RenderScript.create(getContext());

        Allocation tmpIn = Allocation.createFromBitmap(script, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(script, outputBitmap);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script));

            blur.setRadius(radius);
            blur.setInput(tmpIn);
            blur.forEach(tmpOut);

            tmpOut.copyTo(outputBitmap);
        }

        script.destroy();

        bitmap = Bitmap.createScaledBitmap(outputBitmap, width, height, false);
    }

    private void blurBitmapTest() {
        ImageRequest imageRequest =
            ImageRequestBuilder.newBuilderWithResourceId(resId == 0 ? DEFAULT_RES_ID : resId)
                .setResizeOptions(new ResizeOptions(30, 30))
                .setPostprocessor(new IterativeBoxBlurPostProcessor(1, 30))
                .build();
        Fresco.getImagePipeline()
            .fetchDecodedImage(imageRequest, getContext())
            .subscribe(new BaseBitmapDataSubscriber() {
                @Override protected void onNewResultImpl(@Nullable Bitmap bitmap) {
                    if (bitmap == null) {
                        GradientCoverView.this.bitmap = BitmapFactory.decodeResource(getResources(),
                            resId == 0 ? DEFAULT_RES_ID : resId);
                        blurBitmap();
                    } else {
                        GradientCoverView.this.bitmap =
                            Bitmap.createScaledBitmap(bitmap, width, height, false);
                    }
                    invalidate();
                }

                @Override protected void onFailureImpl(
                    DataSource<CloseableReference<CloseableImage>> dataSource) {

                }
            }, CallerThreadExecutor.getInstance());
    }
}
