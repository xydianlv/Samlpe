package com.example.wyyu.gitsamlpe.test.video.player;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.image.matisse.AspectRatioFrameLayout;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class LiteVideoPlayerView extends FrameLayout
    implements View.OnClickListener, TextureView.SurfaceTextureListener {

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

    @IntDef({ PlayStatus.PREPARE, PlayStatus.PLAYING, PlayStatus.PAUSE, PlayStatus.COMPLETE })
    @Retention(RetentionPolicy.SOURCE) public @interface PlayStatus {
        int PREPARE = 0;
        int PLAYING = 1;
        int PAUSE = 2;
        int COMPLETE = 3;
    }

    private static DataSource.Factory mediaDataSourceFactory;
    private AspectRatioFrameLayout playerRoot;
    private SurfaceTexture surfaceTexture;

    private SimpleDraweeView videoCover;
    private SimpleDraweeView videoBack;
    private ImageView startButton;
    private ImageView pauseButton;

    private SimpleExoPlayer exoPlayer;
    private Drawable placeHolder;
    private VideoInfo videoInfo;

    private @PlayStatus int presentStatus;

    private void initPlayerView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_lite_video_player, this);

        initValue();
        initView();
        initPlayer();
    }

    private void initValue() {
        mediaDataSourceFactory =
            new DefaultDataSourceFactory(AppController.getAppContext(), "exo-player");

        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        placeHolder = ta.getDrawable(0);

        presentStatus = PlayStatus.PREPARE;
    }

    private void initView() {
        LiteResizeTextureView textureView = findViewById(R.id.video_player_texture);
        textureView.setSurfaceTextureListener(this);

        playerRoot = findViewById(R.id.video_Player_root);
        videoCover = findViewById(R.id.video_player_cover);
        videoBack = findViewById(R.id.video_player_back);
        startButton = findViewById(R.id.video_player_start);
        pauseButton = findViewById(R.id.video_player_pause);

        playerRoot.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

        startButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
    }

    private void initPlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());

        exoPlayer.addListener(new Player.EventListener() {
            @Override public void onTimelineChanged(Timeline timeline, @Nullable Object manifest,
                int reason) {

            }

            @Override public void onTracksChanged(TrackGroupArray trackGroups,
                TrackSelectionArray trackSelections) {

            }

            @Override public void onLoadingChanged(boolean isLoading) {

            }

            @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            }

            @Override public void onRepeatModeChanged(int repeatMode) {

            }

            @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override public void onPlayerError(ExoPlaybackException error) {

            }

            @Override public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override public void onSeekProcessed() {

            }
        });

        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        try {
            exoPlayer.setVideoSurface(new Surface(surfaceTexture));
            this.surfaceTexture = surfaceTexture;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return surfaceTexture == null;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_player_start:
                onClickStart();
                break;
            case R.id.video_player_pause:
                onClickPause();
                break;
        }
    }

    private void onClickStart() {
        if (presentStatus == PlayStatus.PREPARE) {
            prepare();
        }
        exoPlayer.setPlayWhenReady(true);
    }

    private void prepare() {
        try {
            MediaSource mediaSource =
                new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
                    videoInfo.uri);
            exoPlayer.prepare(mediaSource, true, true);
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
    }

    private void onClickPause() {
        exoPlayer.setPlayWhenReady(false);
    }

    private void refreshStatus() {
        switch (presentStatus) {
            case PlayStatus.PREPARE:
                startButton.setVisibility(VISIBLE);
                pauseButton.setVisibility(GONE);
                videoCover.setVisibility(VISIBLE);
                break;
            case PlayStatus.PAUSE:
                pauseButton.setVisibility(VISIBLE);
                startButton.setVisibility(GONE);
                videoCover.setVisibility(GONE);
                break;
            case PlayStatus.PLAYING:
                pauseButton.setVisibility(VISIBLE);
                startButton.setVisibility(GONE);
                videoCover.setVisibility(GONE);
                break;
            case PlayStatus.COMPLETE:
                startButton.setVisibility(VISIBLE);
                pauseButton.setVisibility(GONE);
                videoCover.setVisibility(VISIBLE);
                break;
        }
    }

    public void setVideoInfo(@NonNull VideoInfo videoInfo) {
        if (videoInfo.width > videoInfo.height) {
            playerRoot.setAspectRatio((float) videoInfo.width / videoInfo.height);
        } else {
            playerRoot.setAspectRatio(1.0f);
        }

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

        this.presentStatus = PlayStatus.PREPARE;
        this.videoInfo = videoInfo;

        refreshStatus();
    }
}
