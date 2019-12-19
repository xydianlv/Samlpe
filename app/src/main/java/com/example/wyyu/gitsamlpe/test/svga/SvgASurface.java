package com.example.wyyu.gitsamlpe.test.svga;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.drawer.SVGACanvasDrawer;

/**
 * Created by wyyu on 2019-12-19.
 **/

public class SvgASurface extends SurfaceView implements SurfaceHolder.Callback {

    public SvgASurface(Context context) {
        super(context);
        initSurface();
    }

    public SvgASurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurface();
    }

    public SvgASurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurface();
    }

    private ValueAnimator animator;
    private Canvas canvas;

    private void initSurface() {
        setZOrderOnTop(true);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        animator = null;
    }

    @Override public void surfaceCreated(SurfaceHolder holder) {
        if (animator == null) {
            initAnimAndPlay();
        } else {
            animator.start();
        }
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        if (animator != null) {
            animator.cancel();
        }
    }

    private void initAnimAndPlay() {
        SVGAParser parser = new SVGAParser(getContext());
        parser.decodeFromAssets("anim_svg_test.svga", new SVGAParser.ParseCompletion() {
            @Override public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {
                startAnimPlay(svgaVideoEntity);
            }

            @Override public void onError() {
                UToast.showShort(getContext(), "SVGA加载失败");
            }
        });
    }

    private void startAnimPlay(@NonNull SVGAVideoEntity svgaVideoEntity) {
        SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
        SVGACanvasDrawer drawer = new SVGACanvasDrawer(svgaVideoEntity, drawable.getDynamicItem());

        animator = ValueAnimator.ofInt(0, svgaVideoEntity.getFrames() - 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration((svgaVideoEntity.getFrames()) * (1000 / svgaVideoEntity.getFPS()));
        animator.addUpdateListener(animation -> {
            if (animator == null || getHolder() == null) {
                return;
            }
            canvas = getHolder().lockCanvas();
            if (canvas == null) {
                return;
            }
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            drawer.drawFrame(canvas, drawable.getCurrentFrame(), ImageView.ScaleType.FIT_CENTER);
            getHolder().unlockCanvasAndPost(canvas);
            drawable.setCurrentFrame$library_release((int) animator.getAnimatedValue());
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (canvas != null) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                }
            }
        });

        animator.start();
    }
}
