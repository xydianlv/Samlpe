package com.example.wyyu.gitsamlpe.framework.volume;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.util.LinkedList;

/**
 * Created by wyyu on 2018/8/11.
 **/

public class VolumeObservable implements IVolumeObservable {

    private static class ObservableHolder {
        private static VolumeObservable observable = new VolumeObservable();
    }

    public static VolumeObservable getObservable() {
        return ObservableHolder.observable;
    }

    private IVolumeSeekView volumeSeekView;
    private VolumeReceiver volumeReceiver;
    private AudioManager audioManager;

    private LinkedList<Activity> activityList;
    private boolean showSeekView;

    private VolumeObservable() {
        volumeReceiver = new VolumeReceiver();
        volumeSeekView = new VolumeSeekView();
        activityList = new LinkedList<>();

        initAudioManager();
    }

    private void initAudioManager() {
        audioManager =
            (AudioManager) AppController.getAppContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override public void onClickVolumeDown() {
        if (audioManager == null) {
            initAudioManager();
        }
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
    }

    @Override public void onClickVolumeUp() {
        if (audioManager == null) {
            initAudioManager();
        }
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
    }

    @Override public void onVolumeValueChange() {
        if (audioManager == null) {
            initAudioManager();
        }
        if (volumeSeekView == null) {
            volumeSeekView = new VolumeSeekView();
        }
        showVolumeSeekView();
        volumeSeekView.refreshProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @Override public void refreshProgress(int progress) {
        if (audioManager == null) {
            initAudioManager();
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override public void attachActivity(@NonNull Activity activity) {
        if (activityList == null) {
            activityList = new LinkedList<>();
        }
        if (volumeReceiver == null) {
            volumeReceiver = new VolumeReceiver();
        }
        activity.registerReceiver(volumeReceiver, getFilter());
        activityList.add(activity);
    }

    @Override public void detachActivity(@NonNull Activity activity) {
        if (activityList == null || activityList.isEmpty()) {
            return;
        }
        if (volumeReceiver != null) {
            activity.unregisterReceiver(volumeReceiver);
        }
        activityList.remove(activity);
    }

    @Override public void release() {
        if (activityList == null || activityList.isEmpty()) {
            return;
        }
        activityList.clear();
        activityList = null;
    }

    private void showVolumeSeekView() {
        if (audioManager == null) {
            initAudioManager();
        }
        if (volumeSeekView == null) {
            volumeSeekView = new VolumeSeekView();
        }
        volumeSeekView.show(activityList.getFirst(),
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void checkShowTime() {

    }

    private IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        return filter;
    }
}
