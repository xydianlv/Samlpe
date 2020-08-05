package com.example.wyyu.gitsamlpe.test.list;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class CustomLoadingView extends FrameLayout {

    public CustomLoadingView(@NonNull Context context) {
        super(context);
        initLoading();
    }

    public CustomLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLoading();
    }

    public CustomLoadingView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoading();
    }

    private static final int SCREEN_HEIGHT = UIUtils.getScreenHeight();

    private SimpleDraweeView animView;
    private Animatable webpAnim;

    private boolean autoCenter;
    private boolean onShow;

    private void initLoading() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_loading_view, this);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(Uri.parse("asset:///anim_loading_blue.webp"))
            .setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
                    @Nullable Animatable animatable) {
                    if (animatable != null && onShow) {
                        animatable.start();
                    }
                    webpAnim = animatable;
                }
            })
            .build();
        animView = findViewById(R.id.custom_loading_anim);
        animView.setController(controller);

        autoCenter = false;
        onShow = false;
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (animView == null || !autoCenter) {
            return;
        }
        int top = ((int) getPivotY() - SCREEN_HEIGHT) / 2;
        if (top <= 0) {
            return;
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) animView.getLayoutParams();
        params.setMargins(0, top, 0, 0);
        requestLayout();
    }

    // 是否展示动画
    public void show(boolean autoAnim) {
        setVisibility(VISIBLE);

        if (autoAnim) {
            if (webpAnim != null && !webpAnim.isRunning()) {
                webpAnim.start();
            }
            if (animView != null) {
                animView.setVisibility(VISIBLE);
            }
            onShow = true;
        } else {
            if (webpAnim != null && webpAnim.isRunning()) {
                webpAnim.stop();
            }
            if (animView != null) {
                animView.setVisibility(GONE);
            }
            onShow = false;
        }
    }

    // 展示加载布局
    public void show() {
        show(true);
    }

    // 隐藏加载布局
    public void hide() {
        setVisibility(GONE);
        onShow = false;

        if (webpAnim != null && webpAnim.isRunning()) {
            webpAnim.stop();
        }
    }
}
