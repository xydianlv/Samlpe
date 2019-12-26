package com.example.wyyu.gitsamlpe.framework.video.player;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.TextureView;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class TVideoPlayer implements ITVideoPlayer {

    private static final class PlayerHolder {
        private static final ITVideoPlayer player = new TVideoPlayer();
    }

    public static ITVideoPlayer getPlayer() {
        return PlayerHolder.player;
    }

    // 视频缓存
    private CacheDataSourceFactory cacheDataSourceFactory;
    // 用来生成供 ExoPlayer 解析的 MediaSource
    private DataSource.Factory dataSourceFactory;
    // 视频播放器
    private SimpleExoPlayer exoPlayer;

    // 多个视频源列表，供换源使用
    private List<String> sourceList;
    // 当前的视频源
    private String currentSource;
    // 当前视频源的 index
    private int sourceIndex;

    // 视频播放监听
    private List<MediaPlayListener> playListenerList;
    // 当前播放视频的唯一标识
    private long sourceId;
    // 播放器当前状态
    private @MediaStatus int mediaStatus;
    // 新的播放视频
    private boolean newPlay;

    // 播放计时工具，一个 Runable 和一个 Handler
    private Runnable timeRunable;
    // 主线程 Handler
    private Handler mainHandler;

    // 播放器当前音量
    private float volume;

    private TVideoPlayer() {
        initValue();
        initSourceFactory();
        initCacheSourceFactory();
        initHandler();
        initPlayer();
    }

    private void initValue() {
        playListenerList = new ArrayList<>();
        currentSource = null;
        sourceList = null;
        sourceIndex = 0;

        mediaStatus = MediaStatus.PREPARE;
    }

    private void initSourceFactory() {
        dataSourceFactory =
            new DefaultDataSourceFactory(AppController.getAppContext(), "exo-player");
    }

    private void initCacheSourceFactory() {
        if (cacheDataSourceFactory != null) {
            return;
        }
        DefaultDataSourceFactory dataSourceFactory =
            new DefaultDataSourceFactory(AppController.getAppContext(), null,
                new DefaultHttpDataSourceFactory(null));
        File cacheDir = AppController.getAppContext().getExternalCacheDir();
        if (cacheDir == null) {
            return;
        }
        File cacheFile = new File(cacheDir.getAbsolutePath(), "pipi-video");

        // 本地最多保存512M, 按照LRU原则删除老数据
        SimpleCache simpleCache =
            new SimpleCache(cacheFile, new LeastRecentlyUsedCacheEvictor(200 * 1024 * 1024));

        cacheDataSourceFactory = new CacheDataSourceFactory(simpleCache, dataSourceFactory);
    }

    private void initHandler() {
        mainHandler = new Handler(Looper.getMainLooper());
        timeRunable = () -> {
            long position = getCurrentPosition();
            long duration = getDuration();
            notifyPlayerProgress(position, (int) (100 * ((float) position / duration)));

            mainHandler.postDelayed(timeRunable, 500);
        };
    }

    private void initPlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(AppController.getAppContext(),
            new DefaultTrackSelector());

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
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        if (sourceList == null || sourceList.isEmpty()) {
                            mediaStatus = MediaStatus.ERROR;
                            notifyMediaStatus();
                            return;
                        }
                        sourceIndex = sourceIndex + 1;
                        if (sourceIndex >= sourceList.size()) {
                            mediaStatus = MediaStatus.ERROR;
                            notifyMediaStatus();
                            return;
                        }
                        currentSource = sourceList.get(sourceIndex);
                        preparePlay();
                        return;
                    case Player.STATE_BUFFERING:
                        mediaStatus = MediaStatus.LOADING;
                        break;
                    case Player.STATE_READY:
                        if (playWhenReady) {
                            mediaStatus = MediaStatus.PLAYING;
                            newPlay = false;
                            if (mainHandler != null) {
                                mainHandler.postDelayed(timeRunable, 0);
                            }
                        } else if (newPlay) {
                            start();
                            newPlay = false;
                        } else {
                            mediaStatus = MediaStatus.PAUSE;
                        }
                        break;
                    case Player.STATE_ENDED:
                        mediaStatus = MediaStatus.COMPLETE;
                        break;
                }
                if (!(playbackState == Player.STATE_READY && playWhenReady)
                    && mainHandler != null) {
                    mainHandler.removeCallbacks(timeRunable);
                }
                notifyMediaStatus();
            }

            @Override public void onRepeatModeChanged(int repeatMode) {

            }

            @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override public void onPlayerError(ExoPlaybackException error) {
                mediaStatus = MediaStatus.ERROR;
                notifyMediaStatus();
            }

            @Override public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override public void onSeekProcessed() {

            }
        });

        volume = exoPlayer.getVolume();
    }

    private void notifyMediaStatus() {
        if (playListenerList == null || playListenerList.isEmpty()) {
            return;
        }
        for (MediaPlayListener playListener : playListenerList) {
            if (playListener == null) {
                continue;
            }
            playListener.onStateChange(mediaStatus);
        }
    }

    private void notifyPlayerProgress(long position, int progress) {
        if (playListenerList == null || playListenerList.isEmpty()) {
            return;
        }
        for (MediaPlayListener playListener : playListenerList) {
            if (playListener == null) {
                continue;
            }
            playListener.onProgress(position, progress);
        }
    }

    @Override
    public void setDataAndPlay(@NonNull LinkedHashMap<String, String> sourceMap, long sourceId,
        @NonNull MediaPlayListener playListener, @NonNull TextureView textureView) {
        // 更新播放器监听对象
        attachPlayListener(sourceId, playListener);

        if (this.sourceList == null) {
            this.sourceList = new ArrayList<>();
        }
        this.sourceList.clear();

        this.sourceList.add(sourceMap.get("default"));
        this.sourceList.add(sourceMap.get("origin"));
        this.sourceList.add(sourceMap.get("ext"));
        this.sourceList.add(sourceMap.get("src"));

        this.currentSource = this.sourceList.get(0);
        this.sourceIndex = 0;
        this.newPlay = true;

        if (exoPlayer == null) {
            initPlayer();
        }
        this.exoPlayer.setVideoTextureView(textureView);
        this.setRepeat(false);
        this.setMute(false);
        this.preparePlay();
    }

    @Override public void setAudioAndPlay(@NonNull String audioSource, long sourceId,
        @NonNull MediaPlayListener playListener) {
        // 更新播放器监听对象
        attachPlayListener(sourceId, playListener);

        if (this.sourceList != null) {
            this.sourceList.clear();
        }
        this.currentSource = audioSource;
        this.newPlay = true;

        if (exoPlayer == null) {
            initPlayer();
        }
        this.setRepeat(false);
        this.setMute(false);
        this.preparePlay();
    }

    @Override
    public void attachPlayListener(long sourceId, @NonNull MediaPlayListener playListener) {
        if (this.playListenerList == null) {
            this.playListenerList = new ArrayList<>();
        }
        if (this.sourceId == sourceId) {
            this.playListenerList.add(playListener);
        } else if (this.playListenerList.isEmpty()) {
            this.playListenerList.add(playListener);
            this.sourceId = sourceId;
        } else {
            mediaStatus = MediaStatus.PREPARE;
            notifyMediaStatus();

            this.playListenerList.clear();
            this.playListenerList.add(playListener);
            this.sourceId = sourceId;
        }
    }

    @Override public void detachPlayListener(@NonNull MediaPlayListener playListener) {
        if (this.playListenerList == null || this.playListenerList.isEmpty()) {
            return;
        }
        this.playListenerList.remove(playListener);
    }

    @Override public void start() {
        if (exoPlayer == null) {
            initPlayer();
        }
        if (mediaStatus == MediaStatus.LOADING || mediaStatus == MediaStatus.PAUSE) {
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override public void pause() {
        if (exoPlayer == null) {
            return;
        }
        if (mediaStatus == MediaStatus.PLAYING || mediaStatus == MediaStatus.LOADING) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    @Override public void reStart() {
        if (exoPlayer == null) {
            initPlayer();
        }
        if (mediaStatus == MediaStatus.COMPLETE) {
            preparePlay();
        }
    }

    @Override public void complete() {
        pause();

        mediaStatus = MediaStatus.PREPARE;
        notifyMediaStatus();

        clearValue();
    }

    @Override public void release() {
        complete();

        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override public void seekTo(int progress) {
        if (mediaStatus != MediaStatus.PLAYING && mediaStatus != MediaStatus.PAUSE) {
            return;
        }
        if (exoPlayer != null) {
            exoPlayer.seekTo((long) (exoPlayer.getDuration() * ((float) progress / 100)));
        }
    }

    @Override public void setRepeat(boolean repeat) {
        if (exoPlayer != null) {
            exoPlayer.setRepeatMode(repeat ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF);
        }
    }

    @Override public void setMute(boolean setMute) {
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.setVolume(setMute ? 0.0f : volume);
    }

    @Override public long getCurrentPosition() {
        if (exoPlayer == null) {
            return 0;
        }
        return exoPlayer.getCurrentPosition();
    }

    @Override public long getDuration() {
        if (exoPlayer == null) {
            return 0;
        }
        return exoPlayer.getDuration();
    }

    @Override public long getSourceId() {
        if (mediaStatus == MediaStatus.PREPARE) {
            return 0;
        }
        return sourceId;
    }

    private void preparePlay() {
        if (dataSourceFactory == null) {
            initSourceFactory();
        }
        if (exoPlayer == null) {
            initPlayer();
        }
        try {
            initCacheSourceFactory();
            MediaSource mediaSource =
                new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(
                    Uri.parse(currentSource));
            exoPlayer.prepare(mediaSource, true, true);
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
    }

    private void clearValue() {
        if (sourceList != null) {
            sourceList.clear();
            sourceList = null;
        }
        if (playListenerList != null) {
            playListenerList.clear();
            playListenerList = null;
        }
        currentSource = null;
        sourceIndex = 0;
        sourceId = 0;
    }
}
