package com.example.wyyu.gitsamlpe.test.svga;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

/**
 * Created by wyyu on 2019-12-19.
 **/

public class SvgAFileSurface extends SurfaceView implements SurfaceHolder.Callback {

    public SvgAFileSurface(Context context) {
        super(context);
        initSurface();
    }

    public SvgAFileSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurface();
    }

    public SvgAFileSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurface();
    }

    private ValueAnimator animator;
    private Canvas canvas;

    private void initSurface() {
        setZOrderOnTop(true);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
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
        String filePath = filePath();

        File file = TextUtils.isEmpty(filePath) ? null : new File(filePath);
        if (filePath == null || file == null || !file.exists()) {
            UToast.showShort(getContext(), "缓存文件获取失败");
            return;
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            SVGAParser parser = new SVGAParser(getContext());
            parser.decodeFromInputStream(inputStream, filePath, new SVGAParser.ParseCompletion() {
                @Override public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    startAnimPlay(svgaVideoEntity);
                }

                @Override public void onError() {

                }
            }, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String filePath() {
        String cachePath = getContext() == null || getContext().getCacheDir() == null ? null
            : getContext().getCacheDir().getAbsolutePath();
        return TextUtils.isEmpty(cachePath) ? null : cachePath + "/anim_svg_a.svga";
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
