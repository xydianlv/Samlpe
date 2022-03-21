package com.example.wyyu.gitsamlpe.test.video.player;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.NonNull;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.audio.player.PlayerEventListener;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class SimpleVideoPlayer {

    private static final class PlayerHolder {
        private static final SimpleVideoPlayer player = new SimpleVideoPlayer();
    }

    public static SimpleVideoPlayer getPlayer() {
        return PlayerHolder.player;
    }

    // 用来生成供 ExoPlayer 解析的 MediaSource
    private DataSource.Factory dataSourceFactory;
    // 视频播放器
    private SimpleExoPlayer exoPlayer;
    // 第一次播放
    private boolean newPlay;

    private SimpleVideoPlayer() {
        initPlayer();
        initDataSource();
    }

    private void initDataSource() {
        DatabaseProvider databaseProvider = new ExoDatabaseProvider(AppController.getAppContext());
        File downloadDirectory =
                new File(AppController.getAppContext().getExternalCacheDir(), "cm-video-s");

        Cache cache = new SimpleCache(downloadDirectory, new NoOpCacheEvictor(), databaseProvider);

        dataSourceFactory = new CacheDataSource.Factory()
                .setCache(cache);
    }

    private void initPlayer() {
        Context context = AppController.getAppContext();

        ExoTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(context, trackSelectionFactory);

        DefaultRenderersFactory renderersFactory =
                new DefaultRenderersFactory(AppController.getAppContext()).setExtensionRendererMode(
                        DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);

        exoPlayer = new SimpleExoPlayer.Builder(AppController.getAppContext(),
                renderersFactory).setTrackSelector(trackSelector).build();

        exoPlayer.addListener(new PlayerEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.e("SimpleVideoPlayerLog","playbackState -> " + playbackState);
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        return;
                    case Player.STATE_BUFFERING:
                        break;
                    case Player.STATE_READY:
                        if (newPlay) {
                            exoPlayer.setPlayWhenReady(true);
                        }
                        newPlay = false;
                        break;
                    case Player.STATE_ENDED:
                        break;
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Log.e("SimpleVideoPlayerLog","onPlayerError -> "+error.getMessage());
                error.getMessage();
            }
        });
    }

    public void setDataAndPlay(Uri uri, TextureView textureView) {
        exoPlayer.setVideoTextureView(textureView);
        newPlay = true;

        try {
            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.video_test));
            RawResourceDataSource dataSource = new RawResourceDataSource(AppController.getAppContext());
            try {
                dataSource.open(dataSpec);
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
            DataSource.Factory factory = () -> dataSource;
            Uri rawUri = dataSource.getUri();
            Uri testUri = rawUri == null ? uri : rawUri;
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                    .createMediaSource(MediaItem.fromUri(testUri));

            exoPlayer.setMediaSource(mediaSource);
            exoPlayer.prepare();
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    public void release() {
        exoPlayer.release();
    }

    public void pause() {
        exoPlayer.setPlayWhenReady(false);
    }

    public void stop() {
        exoPlayer.stop();
    }
}