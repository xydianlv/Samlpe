package com.example.wyyu.gitsamlpe.test.video.player;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.TextureView;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.audio.player.PlayerEventListener;
import com.example.wyyu.gitsamlpe.test.video.data.SourceType;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-07-27.
 *
 * 支持 缓存 等功能
 *
 * 缓存 ：已实现
 *
 * ExoPlayer 播放流程
 *
 * * 传入数据时，首先执行 exoPlayer.prepare(mediaSource, true, true) 方法
 * * 在 Player.EventListener 中最先回调 Player.STATE_BUFFERING 状态
 * * 若数据源不可用，在 Player.EventListener 中回调 Player.STATE_IDLE 状态，在这里执行换源逻辑
 * * 若数据源可用，在 Player.EventListener 中回调 Player.STATE_READY 状态，同时监听到 playWhenReady 变量
 * ** 若 playWhenReady 为 true，视频会直接播放
 * ** 若 playWhenReady 为 false，需要再调用 start 方法才可以执行播放行为
 **/

public class CMVideoPlayer implements ICMVideoPlayer {

    private static final class PlayerHolder {

        private static final CMVideoPlayer player = new CMVideoPlayer();
    }

    public static ICMVideoPlayer getPlayer() {
        return PlayerHolder.player;
    }

    // 用来生成供 ExoPlayer 解析的 MediaSource
    private DataSource.Factory dataSourceFactory;
    // 视频播放器
    private SimpleExoPlayer exoPlayer;

    // 播放过的视频进度缓存
    private SourceCacheSeek seekCache;
    // 当前的视频源
    private String currentSource;
    // 视频源类型，默认为服务器URL
    private @SourceType int sourceType;

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
    // 播放器统计的播放时长
    private long playDuration;
    // 是否可直接暂停
    private boolean canComplete;

    private CMVideoPlayer() {
        initValue();
        initSourceFactory();
        initHandler();
        initPlayer();
    }

    private void initValue() {
        playListenerList = new ArrayList<>();
        seekCache = new SourceCacheSeek();
        currentSource = null;
        canComplete = true;

        mediaStatus = MediaStatus.PREPARE;
        sourceType = SourceType.URL;
    }

    private void initSourceFactory() {
        DefaultHttpDataSourceFactory httpDataSourceFactory =
            new DefaultHttpDataSourceFactory("cm-player");

        DefaultDataSourceFactory sourceFactory =
            new DefaultDataSourceFactory(AppController.getAppContext(), httpDataSourceFactory);

        DatabaseProvider databaseProvider = new ExoDatabaseProvider(AppController.getAppContext());
        File downloadDirectory =
            new File(AppController.getAppContext().getExternalCacheDir(), "cm-video");

        Cache cache = new SimpleCache(downloadDirectory, new NoOpCacheEvictor(), databaseProvider);

        dataSourceFactory =
            new CacheDataSourceFactory(cache, sourceFactory, new FileDataSource.Factory(), null,
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }

    private void initHandler() {
        mainHandler = new Handler(Looper.getMainLooper());
        playDuration = 0;
        timeRunable = () -> {
            if (mediaStatus != MediaStatus.PLAYING) {
                return;
            }
            long position = getPosition();
            long duration = getDuration();
            notifyPlayerProgress(position, (int) (100 * ((float) position / duration)));

            mainHandler.postDelayed(timeRunable, 500);
            playDuration = playDuration + 500;
        };
    }

    private void initPlayer() {

        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector =
            new DefaultTrackSelector(AppController.getAppContext(), trackSelectionFactory);

        DefaultRenderersFactory renderersFactory =
            new DefaultRenderersFactory(AppController.getAppContext()).setExtensionRendererMode(
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);

        exoPlayer = new SimpleExoPlayer.Builder(AppController.getAppContext(),
            renderersFactory).setTrackSelector(trackSelector).build();

        exoPlayer.addListener(new PlayerEventListener() {

            @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        mediaStatus = MediaStatus.ERROR;
                        notifyMediaStatus();
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
                        defaultSpeed();
                        if (seekCache != null) {
                            seekCache.cacheSeek(sourceId, 0);
                        }
                        break;
                }
                if (!(playbackState == Player.STATE_READY && playWhenReady)
                    && mainHandler != null) {
                    mainHandler.removeCallbacks(timeRunable);
                }
                notifyMediaStatus();
            }

            @Override public void onPlayerError(ExoPlaybackException error) {
                mediaStatus = MediaStatus.ERROR;
                notifyMediaStatus();
            }
        });

        volume = exoPlayer.getVolume();
    }

    private void preparePlay() {
        if (dataSourceFactory == null) {
            initSourceFactory();
        }
        if (exoPlayer == null) {
            initPlayer();
        }
        try {
            if (sourceType == SourceType.URL) {
                MediaSource mediaSource = SourceCacheData.getInstance()
                    .createMediaSource(currentSource, String.valueOf(sourceId));
                if (mediaSource == null) {
                    mediaSource =
                        new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                            Uri.parse(currentSource));
                }
                exoPlayer.prepare(mediaSource, true, true);
            } else {
                MediaSource mediaSource =
                    new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                        Uri.fromFile(new File(currentSource)));
                exoPlayer.prepare(mediaSource, true, true);
            }
        } catch (Exception e) {
            ULog.show(e.getMessage());
        }
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

    private void clearValue() {
        if (playListenerList != null) {
            playListenerList.clear();
            playListenerList = null;
        }
        currentSource = null;
        sourceId = 0;
    }

    @Override public void setDataAndPlay(int sourceType, String sourceValue, long sourceId,
        @NonNull TextureView textureView, @NonNull MediaPlayListener playListener) {

        // 更新播放器监听对象
        attachPlayListener(sourceId, playListener);

        this.currentSource = sourceValue;
        this.sourceType = sourceType;
        this.playDuration = 0;
        this.newPlay = true;

        if (exoPlayer == null) {
            initPlayer();
        }
        this.exoPlayer.setVideoTextureView(textureView);
        this.setRepeat(false);
        this.setMute(false);
        this.preparePlay();
    }

    @Override
    public void preloadPlay(@SourceType int sourceType, String sourceValue, long sourceId) {
        if (TextUtils.isEmpty(sourceValue) || sourceType != SourceType.URL) {
            return;
        }
        SourceCacheData.getInstance().preload(sourceValue, String.valueOf(sourceId));
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

    @Override public void setSpeed(float speed) {
        if (exoPlayer != null) {
            exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));
        }
    }

    @Override public void defaultSpeed() {
        setSpeed(1.0f);
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
            this.mediaStatus = MediaStatus.RESET;
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
        if (seekCache != null) {
            long sourceSeek = seekCache.lastSeek(sourceId);
            if (sourceSeek > 0) {
                exoPlayer.seekTo(sourceSeek);
            }
        }
    }

    @Override public void pause() {
        if (exoPlayer == null) {
            return;
        }
        if (seekCache != null && mediaStatus == MediaStatus.PLAYING) {
            seekCache.cacheSeek(sourceId, getPosition());
        }
        if (mediaStatus == MediaStatus.PLAYING || mediaStatus == MediaStatus.LOADING) {
            exoPlayer.setPlayWhenReady(false);
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

    @Override public void reStart() {
        if (exoPlayer == null) {
            initPlayer();
        }
        if (mediaStatus == MediaStatus.COMPLETE) {
            exoPlayer.setPlayWhenReady(true);
            preparePlay();
        }
    }

    @Override public void complete() {
        if (!canComplete) {
            return;
        }
        pause();

        mediaStatus = MediaStatus.RESET;
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

    @Override public long getSourceId() {
        if (mediaStatus == MediaStatus.PREPARE) {
            return 0;
        }
        return sourceId;
    }

    @Override public long getPosition() {
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
}
