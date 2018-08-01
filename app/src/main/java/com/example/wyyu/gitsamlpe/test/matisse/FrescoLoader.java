package com.example.wyyu.gitsamlpe.test.matisse;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by wyyu on 2018/7/23.
 **/

public class FrescoLoader implements View.OnAttachStateChangeListener {

    private DraweeHolder<GenericDraweeHierarchy> drawHolder;

    // 占位图缩放类型
    private ScalingUtils.ScaleType placeholderScaleType;
    // 进度条图片缩放类型
    private ScalingUtils.ScaleType progressBarScaleType;
    // 加载成功的原图的缩放类型
    private ScalingUtils.ScaleType actualImageScaleType;
    // 加载失败图片的缩放类型
    private ScalingUtils.ScaleType failureScaleType;

    // 占位图
    private Drawable placeHolderDrawable;
    // 背景图
    private Drawable backgroundDrawable;
    // 进度条图片
    private Drawable progressDrawable;
    // 记载失败图片
    private Drawable failureDrawable;

    //
    private RotationOptions rotationOptions;
    //
    private RoundingParams roundingParams;
    // 图片压缩数据
    private ResizeOptions resizeOptions;
    //
    private PointF actualImageFocusPoint;

    //
    private boolean thumbnailPreviewsEnabled;
    // 加载完原图后是否自动播放，一般用于 GIF
    private boolean autoPlayAnimation;
    //
    private float desiredAspectRatio;
    // 占位图／进度条 转为原图的过渡时间
    private int fadeDuration;
    //
    private Uri lowImageUri;
    // 带加载图片地址
    private Uri uri;

    private FrescoLoader() {
        actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
    }

    public static FrescoLoader newFrescoLoader() {
        return new FrescoLoader();
    }

    public FrescoLoader uri(Uri uri) {
        this.uri = uri;
        return this;
    }

    // 占位图
    public FrescoLoader placeHolder(Drawable drawable) {
        return this;
    }

    // 旋转进度条使用的图片
    public FrescoLoader progressbar(Drawable drawable) {
        return this;
    }

    // 对图片进行压缩处理，减少内存消耗
    public FrescoLoader resize(ResizeOptions resizeOptions) {
        this.resizeOptions = resizeOptions;
        return this;
    }

    // 对图片进行压缩处理，减少内存消耗
    public FrescoLoader resize(int targetWidth, int targetHeight) {
        this.resizeOptions = new ResizeOptions(targetWidth, targetHeight);
        return this;
    }

    //
    public FrescoLoader cornersRadius(int radius) {
        return this;
    }

    //
    public FrescoLoader roundAsCircle(int borderColor, int borderWidth) {
        return this;
    }

    // 由 占位图／进度条 转为完成加载的图片时所需时长
    public FrescoLoader fadeDuration(int duration) {
        return this;
    }

    //
    public FrescoLoader autoPlayAnimation(boolean auto) {
        return this;
    }

    //
    public void into(ImageView targetView) {

        if (targetView == null || uri == null) {
            return;
        }

        if (drawHolder == null) {
            drawHolder = DraweeHolder.create(null, targetView.getContext());
        }

        GenericDraweeHierarchy hierarchy =
            new GenericDraweeHierarchyBuilder(targetView.getResources()).setPlaceholderImage(
                placeHolderDrawable, placeholderScaleType)
                .setProgressBarImage(progressDrawable, progressBarScaleType)
                .setFailureImage(failureDrawable, failureScaleType)
                .setActualImageFocusPoint(actualImageFocusPoint)
                .setActualImageScaleType(actualImageScaleType)
                .setDesiredAspectRatio(desiredAspectRatio)
                .setRoundingParams(roundingParams)
                .setBackground(backgroundDrawable)
                .setFadeDuration(fadeDuration)
                .build();
        drawHolder.setHierarchy(hierarchy);

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
            .setLocalThumbnailPreviewsEnabled(thumbnailPreviewsEnabled)
            .setRotationOptions(rotationOptions)
            .setRequestPriority(Priority.HIGH)
            .setResizeOptions(resizeOptions)
            .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setLowResImageRequest(ImageRequest.fromUri(lowImageUri))
            .setOldController(drawHolder.getController())
            .setAutoPlayAnimations(autoPlayAnimation)
            .setImageRequest(imageRequest)
            .setTapToRetryEnabled(false)
            .build();

        drawHolder.setController(controller);

        if (isAttachedToWindow(targetView)) {
            drawHolder.onAttach();
        }

        targetView.addOnAttachStateChangeListener(this);
        targetView.setImageDrawable(drawHolder.getTopLevelDrawable());
    }

    private static boolean isAttachedToWindow(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return view.isAttachedToWindow();
        } else {
            return view.getWindowToken() != null;
        }
    }

    @Override public void onViewAttachedToWindow(View v) {
        drawHolder.onAttach();
    }

    @Override public void onViewDetachedFromWindow(View v) {
        drawHolder.onDetach();
    }
}
