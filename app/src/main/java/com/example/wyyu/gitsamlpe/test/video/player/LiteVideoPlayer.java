package com.example.wyyu.gitsamlpe.test.video.player;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
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
 * Created by wyyu on 2019/4/3.
 **/

public class LiteVideoPlayer {

    private static class PlayerHolder {
        private static final LiteVideoPlayer videoPlayer = new LiteVideoPlayer();
    }

    public static LiteVideoPlayer getPlayer() {
        return PlayerHolder.videoPlayer;
    }

    @IntDef({
        PlayStatus.PREPARE, PlayStatus.LOADING, PlayStatus.PLAYING, PlayStatus.PAUSE,
        PlayStatus.COMPLETE
    }) @Retention(RetentionPolicy.SOURCE) public @interface PlayStatus {
        int PREPARE = 0;
        int LOADING = 1;
        int PLAYING = 2;
        int PAUSE = 3;
        int COMPLETE = 4;
    }

    public interface VideoPlayerListener {

        void onStateChange(long videoId, @PlayStatus int status);
    }

    private DataSource.Factory mediaDataSourceFactory;

    private SimpleExoPlayer exoPlayer;
    private VideoInfo videoInfo;

    private VideoPlayerListener playerListener;
    private @PlayStatus int presentStatus;

    private LiteVideoPlayer() {

        exoPlayer = ExoPlayerFactory.newSimpleInstance(AppController.getAppContext(),
            new DefaultTrackSelector());

        mediaDataSourceFactory =
            new DefaultDataSourceFactory(AppController.getAppContext(), "exo-player");

        exoPlayer.addListener(new Player.EventListener() {
            @Override public void onTimelineChanged(Timeline timeline, @Nullable Object manifest,
                int reason) {

            }

            @Override public void onTracksChanged(TrackGroupArray trackGroups,
                TrackSelectionArray trackSelections) {

            }

            @Override public void onLoadingChanged(boolean isLoading) {
                presentStatus = isLoading ? PlayStatus.LOADING : presentStatus;

                if (playerListener != null && isLoading) {
                    playerListener.onStateChange(videoInfo.id, presentStatus);
                }
            }

            @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        // LiteVideoPlayerView 中的 TextureView 回调 onSurfaceTextureDestroyed 后 Player 会调到这个状态
                        presentStatus = PlayStatus.PREPARE;
                        if (playerListener != null) {
                            playerListener.onStateChange(videoInfo.id, presentStatus);
                        }
                        videoInfo = null;
                        return;
                    case Player.STATE_BUFFERING:
                        presentStatus = PlayStatus.PREPARE;
                        break;
                    case Player.STATE_READY:
                        if (presentStatus == PlayStatus.PLAYING) {
                            presentStatus = PlayStatus.PAUSE;
                        } else {
                            presentStatus = PlayStatus.PLAYING;
                        }
                        break;
                    case Player.STATE_ENDED:
                        presentStatus = PlayStatus.COMPLETE;
                        break;
                }
                if (playerListener != null) {
                    playerListener.onStateChange(videoInfo.id, presentStatus);
                }
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

        exoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);

        presentStatus = PlayStatus.PREPARE;
    }

    /**
     * 开始播放
     *
     * @param surface 视频播放控件
     * @param video 视频数据
     * @param playerListener 播放状态监听
     */
    public void start(@NonNull Surface surface, @NonNull VideoInfo video,
        @NonNull VideoPlayerListener playerListener) {
        if (videoInfo == null) {
            this.playerListener = playerListener;
            exoPlayer.setVideoSurface(surface);
            videoInfo = new VideoInfo(video);
            prepare();
            start();
        } else if (videoInfo.id == video.id) {
            switch (presentStatus) {
                case PlayStatus.PREPARE:
                case PlayStatus.COMPLETE:
                    prepare();
                case PlayStatus.PAUSE:
                    start();
                    break;
                case PlayStatus.PLAYING:
                case PlayStatus.LOADING:
                    break;
            }
            this.playerListener = playerListener;
        } else {
            switch (presentStatus) {
                case PlayStatus.PREPARE:
                case PlayStatus.COMPLETE:
                case PlayStatus.PAUSE:
                    reset();
                    break;
                case PlayStatus.PLAYING:
                case PlayStatus.LOADING:
                    stop();
                    reset();
                    break;
            }
            videoInfo = null;
            start(surface, video, playerListener);
        }
    }

    private void reset() {
        exoPlayer.clearVideoSurface();
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

    private void start() {
        exoPlayer.setPlayWhenReady(true);
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (presentStatus == PlayStatus.LOADING || presentStatus == PlayStatus.PLAYING) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    /**
     * 释放播放器
     */
    public void release() {
        stop();
        videoInfo = null;
    }

    /**
     * 获取当前正在播放视频的 ID
     *
     * @return 视频ID
     */
    long currentId() {
        return videoInfo == null ? 0 : videoInfo.id;
    }

    /**
     * 获取播放器的当前状态
     *
     * @return 播放器当前状态
     */
    @PlayStatus int currentStatus() {
        return presentStatus;
    }
}
