package com.example.wyyu.gitsamlpe.test.svga;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.framework.WeakHandler;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.drawer.SVGACanvasDrawer;

/**
 * Created by wyyu on 2019-12-19.
 *
 * 经测试，SVGA 动画用 60帧 效果最佳，帧数低会在视觉上又掉帧现象，帧数高 ValueAnimator 在绘制时会卡顿
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

        Log.e("SvgASurfaceTest", "surfaceCreated -> currentId : " + Thread.currentThread().getId());

        SVGAParser parser = new SVGAParser(getContext());
        parser.decodeFromAssets("anim_svg_test_5.svga", new SVGAParser.ParseCompletion() {
            @Override public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {

                startAnimPlay(svgaVideoEntity);
            }

            @Override public void onError() {
                UToast.showShort(getContext(), "SVGA加载失败");
            }
        });
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        if (animator != null) {
            animator.cancel();
        }
    }

    private void startAnimPlay(@NonNull SVGAVideoEntity svgaVideoEntity) {

        Log.e("SvgASurfaceTest", "surfaceCreated -> currentId : " + Thread.currentThread().getId());

        HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        Handler handler = new Handler(thread.getLooper()) {

            SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
            SVGACanvasDrawer drawer =
                new SVGACanvasDrawer(svgaVideoEntity, drawable.getDynamicItem());

            @Override public void handleMessage(Message msg) {

                Log.e("SvgASurfaceTest",
                    "handleMessage -> currentId : " + Thread.currentThread().getId());

                canvas = getHolder() == null ? null : getHolder().lockCanvas();
                if (canvas == null) {
                    return;
                }

                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                drawable.setCurrentFrame$library_release(msg.what);
                drawer.drawFrame(canvas, drawable.getCurrentFrame(),
                    ImageView.ScaleType.FIT_CENTER);

                getHolder().unlockCanvasAndPost(canvas);
            }
        };

        animator = ValueAnimator.ofInt(0, svgaVideoEntity.getFrames() - 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(svgaVideoEntity.getFrames() / svgaVideoEntity.getFPS() * 1000);
        animator.addUpdateListener(animation -> {
            if (animation == null) {
                return;
            }
            handler.sendEmptyMessage((int) animation.getAnimatedValue());
        });

        animator.start();
    }
}
