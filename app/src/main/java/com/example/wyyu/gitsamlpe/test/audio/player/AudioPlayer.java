package com.example.wyyu.gitsamlpe.test.audio.player;

import android.net.Uri;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by wyyu on 2018/10/10.
 **/

public class AudioPlayer implements IAudioPlayer {

    private static class PlayerHolder {
        private static AudioPlayer audioPlayer = new AudioPlayer();
    }

    public static AudioPlayer getPlayer() {
        return PlayerHolder.audioPlayer;
    }

    private DefaultDataSourceFactory dataSourceFactory;
    private ExoPlayer player;

    private boolean isPlaying;
    private long presentId;

    private AudioPlayer() {
        initPlayer();
    }

    private void initPlayer() {
        player = new SimpleExoPlayer.Builder(AppController.getAppContext())
                .build();
        player.setRepeatMode(Player.REPEAT_MODE_OFF);
        player.setPlayWhenReady(true);
        dataSourceFactory = new DefaultDataSourceFactory(AppController.getAppContext(),
            Util.getUserAgent(AppController.getAppContext(), "GitSample"));

        player.addListener(new PlayerEventListener() {

            // 播放器状态回调，playWhenReady - 是否可进行播放，playbackState - 播放器状态
            // playbackState ：2 - 准备中、3 - 开始播放、4 - 播放结束
            @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);
                isPlaying = playbackState == 3;
            }
        });

        isPlaying = false;
        presentId = 0;
    }

    @Override public void play(@NonNull AudioPlayInfo playInfo) {
        if (TextUtils.isEmpty(playInfo.path)) {
            UToast.showShort(AppController.getAppContext(), "音频播放失败");
            return;
        }
        if (player == null) {
            initPlayer();
        }
        if (!isPlaying) {
            prepareAndPlay(playInfo);
        } else if (playInfo.audioId == presentId) {
            stop();
        } else {
            stop();
            prepareAndPlay(playInfo);
        }
    }

    private void prepareAndPlay(AudioPlayInfo playInfo) {
        Uri uri = Uri.parse(playInfo.path);
        presentId = playInfo.audioId;
        MediaSource mediaSource= new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri));
        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override public void release() {
        if (player == null) {
            return;
        }
        if (isPlaying) {
            player.stop(true);
        }
        player.release();
        presentId = 0;
    }

    @Override public void stop() {
        if (player == null || !isPlaying) {
            return;
        }
        player.stop(true);
        presentId = 0;
    }
}
