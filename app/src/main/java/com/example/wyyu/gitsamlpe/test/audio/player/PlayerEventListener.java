package com.example.wyyu.gitsamlpe.test.audio.player;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

/**
 * Created by wyyu on 2018/10/12.
 **/

public abstract class PlayerEventListener implements Player.EventListener {
    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        Log.e("PlayerEventListenerT","onTimelineChanged");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Log.e("PlayerEventListenerT","onTracksChanged");
    }

    @Override public void onLoadingChanged(boolean isLoading) {
        Log.e("PlayerEventListenerT","onLoadingChanged");
    }

    @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.e("PlayerEventListenerT","onPlayerStateChanged");
    }

    @Override public void onRepeatModeChanged(int repeatMode) {
        Log.e("PlayerEventListenerT","onRepeatModeChanged");
    }

    @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        Log.e("PlayerEventListenerT","onShuffleModeEnabledChanged");
    }

    @Override public void onPlayerError(ExoPlaybackException error) {
        Log.e("PlayerEventListenerT","onPlayerError");
    }

    @Override public void onPositionDiscontinuity(int reason) {
        Log.e("PlayerEventListenerT","onPositionDiscontinuity");
    }

    @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.e("PlayerEventListenerT","onPlaybackParametersChanged");
    }

    @Override public void onSeekProcessed() {
        Log.e("PlayerEventListenerT","onSeekProcessed");
    }
}
