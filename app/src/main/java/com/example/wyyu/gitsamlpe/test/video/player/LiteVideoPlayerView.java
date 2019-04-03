package com.example.wyyu.gitsamlpe.test.video.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.touch.OnPressListenerAdapter;
import com.example.wyyu.gitsamlpe.framework.touch.TouchListenerLayout;
import com.example.wyyu.gitsamlpe.test.image.matisse.AspectRatioFrameLayout;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class LiteVideoPlayerView extends FrameLayout
    implements View.OnClickListener, TextureView.SurfaceTextureListener,
    LiteVideoPlayer.VideoPlayerListener {

    public LiteVideoPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public LiteVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiteVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPlayerView();
    }

    private AspectRatioFrameLayout playerRoot;
    private LiteResizeTextureView textureView;
    private SurfaceTexture surfaceTexture;
    private Surface surface;

    private SimpleDraweeView videoCover;
    private SimpleDraweeView videoBack;
    private ImageView startButton;
    private ImageView pauseButton;

    private Drawable placeHolder;
    private VideoInfo videoInfo;

    private AnimatorSet startAnim;
    private AnimatorSet pauseAnim;

    private void initPlayerView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_lite_video_player, this);

        initValue();
        initView();
        initAnim();
    }

    private void initValue() {
        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        placeHolder = ta.getDrawable(0);
    }

    private void initView() {
        textureView = findViewById(R.id.video_player_texture);
        playerRoot = findViewById(R.id.video_Player_root);
        videoCover = findViewById(R.id.video_player_cover);
        videoBack = findViewById(R.id.video_player_back);
        startButton = findViewById(R.id.video_player_start);
        pauseButton = findViewById(R.id.video_player_pause);

        playerRoot.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        textureView.setSurfaceTextureListener(this);

        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        TouchListenerLayout touchLayout = findViewById(R.id.video_player_touch);
        touchLayout.setOnPressListener(new OnPressListenerAdapter() {
            @Override public void onPressDown() {
                super.onPressDown();
                if (videoInfo != null
                    && videoInfo.id == LiteVideoPlayer.getPlayer().currentId()
                    && LiteVideoPlayer.getPlayer().currentStatus()
                    == LiteVideoPlayer.PlayStatus.PLAYING) {
                    autoShowPause();
                }
            }
        });
    }

    private void autoShowPause() {
        if (pauseButton == null || pauseButton.getVisibility() == VISIBLE) {
            return;
        }
        pauseButton.setVisibility(VISIBLE);
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                if (pauseButton != null) {
                    pauseButton.setVisibility(GONE);
                }
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }

    private void initAnim() {
        ObjectAnimator startRotate = ObjectAnimator.ofFloat(startButton, "rotation", 0.0f, -90.0f);
        ObjectAnimator startAlpha = ObjectAnimator.ofFloat(startButton, "alpha", 1.0f, 0.0f);
        ObjectAnimator pauseRotate = ObjectAnimator.ofFloat(pauseButton, "rotation", 90.0f, 0.0f);
        ObjectAnimator pauseAlpha = ObjectAnimator.ofFloat(pauseButton, "alpha", 0.0f, 1.0f);
        startAnim = new AnimatorSet();
        startAnim.playTogether(startRotate, startAlpha, pauseRotate, pauseAlpha);
        startAnim.setInterpolator(new LinearInterpolator());
        startAnim.setDuration(200);

        startAnim.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (startButton != null) {
                    startButton.setVisibility(GONE);
                    startButton.setAlpha(1.0f);
                    startButton.setRotation(0.0f);
                }
                if (pauseButton != null) {
                    pauseButton.setVisibility(VISIBLE);
                }
            }
        });

        ObjectAnimator startRotateE = ObjectAnimator.ofFloat(startButton, "rotation", -90.0f, 0.0f);
        ObjectAnimator startAlphaE = ObjectAnimator.ofFloat(startButton, "alpha", 0.0f, 1.0f);
        ObjectAnimator pauseRotateE = ObjectAnimator.ofFloat(pauseButton, "rotation", 0.0f, 90.0f);
        ObjectAnimator pauseAlphaE = ObjectAnimator.ofFloat(pauseButton, "alpha", 1.0f, 0.0f);
        pauseAnim = new AnimatorSet();
        pauseAnim.playTogether(startRotateE, startAlphaE, pauseRotateE, pauseAlphaE);
        pauseAnim.setInterpolator(new LinearInterpolator());
        pauseAnim.setDuration(200);

        pauseAnim.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (startButton != null) {
                    startButton.setVisibility(VISIBLE);
                }
                if (pauseButton != null) {
                    pauseButton.setVisibility(GONE);
                    pauseButton.setAlpha(1.0f);
                    pauseButton.setRotation(0.0f);
                }
            }
        });
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        // SurfaceTexture 准备就绪
        this.surfaceTexture = surfaceTexture;
        this.surface = new Surface(surfaceTexture);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // SurfaceTexture 缓冲大小变化
    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // SurfaceTexture 即将被销毁
        //if (videoInfo != null && videoInfo.id == LiteVideoPlayer.getPlayer().currentId()) {
        //    LiteVideoPlayer.getPlayer().stop();
        //}
        return surface == null;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // SurfaceTexture 通过 updateImage 更新
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_player_start:
                LiteVideoPlayer.getPlayer().start(surface, videoInfo, this);
                startAnim();
                break;
            case R.id.video_player_pause:
                LiteVideoPlayer.getPlayer().stop();
                pauseAnim();
                break;
        }
    }

    @Override public void onStateChange(long videoId, int status) {
        if (videoInfo == null || videoInfo.id != videoId) {
            return;
        }
        switch (status) {
            case LiteVideoPlayer.PlayStatus.PREPARE:
                startButton.setVisibility(VISIBLE);
                pauseButton.setVisibility(GONE);
                videoCover.setVisibility(VISIBLE);
                break;
            case LiteVideoPlayer.PlayStatus.PAUSE:
                videoCover.setVisibility(GONE);
                break;
            case LiteVideoPlayer.PlayStatus.PLAYING:
                videoCover.setVisibility(GONE);
                break;
            case LiteVideoPlayer.PlayStatus.COMPLETE:
                startButton.setVisibility(VISIBLE);
                pauseButton.setVisibility(GONE);
                videoCover.setVisibility(VISIBLE);
                break;
            case LiteVideoPlayer.PlayStatus.LOADING:
                break;
        }
    }

    private void startAnim() {
        startButton.setVisibility(VISIBLE);
        pauseButton.setVisibility(VISIBLE);
        startAnim.start();

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                if (pauseButton != null) {
                    pauseButton.setVisibility(GONE);
                }
            }
        }, 2600, TimeUnit.MILLISECONDS);
    }

    private void pauseAnim() {
        startButton.setVisibility(VISIBLE);
        pauseButton.setVisibility(VISIBLE);
        pauseAnim.start();
    }

    public void setVideoInfo(@NonNull VideoInfo videoInfo) {
        if (videoInfo.width > videoInfo.height) {
            playerRoot.setAspectRatio((float) videoInfo.width / videoInfo.height);
        } else {
            playerRoot.setAspectRatio(1.0f);
        }
        textureView.setVideoSize(videoInfo.width, videoInfo.height);

        FrescoLoader.newFrescoLoader()
            .placeHolder(placeHolder)
            .resize(120, 120)
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .uri(videoInfo.uri)
            .into(videoCover);

        ImageRequest backRequest = ImageRequestBuilder.newBuilderWithSource(videoInfo.uri)
            .setPostprocessor(new IterativeBoxBlurPostProcessor(60))
            .setProgressiveRenderingEnabled(false)
            .build();

        videoBack.setController(
            Fresco.newDraweeControllerBuilder().setImageRequest(backRequest).build());

        this.videoInfo = videoInfo;
        onStateChange(videoInfo.id, LiteVideoPlayer.PlayStatus.PREPARE);

        //if (videoInfo.id == LiteVideoPlayer.getPlayer().currentId()) {
        //    onStateChange(videoInfo.id, LiteVideoPlayer.PlayStatus.PREPARE);
        //    LiteVideoPlayer.getPlayer().start(new Surface(surfaceTexture), videoInfo, this);
        //} else {
        //    onStateChange(videoInfo.id, LiteVideoPlayer.PlayStatus.PREPARE);
        //}
    }
}
