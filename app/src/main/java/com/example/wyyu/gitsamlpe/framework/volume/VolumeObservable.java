package com.example.wyyu.gitsamlpe.framework.volume;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
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

    private static final int MESSAGE_HIDE_VIEW = 0;

    private IVolumeSeekView volumeSeekView;
    private VolumeReceiver volumeReceiver;
    private AudioManager audioManager;

    private LinkedList<Activity> activityList;
    private boolean showSeekView;

    private Handler handler;

    private VolumeObservable() {
        volumeReceiver = new VolumeReceiver();
        volumeSeekView = new VolumeSeekView();
        activityList = new LinkedList<>();

        initAudioManager();
        initBasicValue();
    }

    private void initAudioManager() {
        audioManager =
            (AudioManager) AppController.getAppContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @SuppressLint("HandlerLeak") private void initBasicValue() {

        handler = new Handler() {
            @Override public void handleMessage(Message msg) {
                if (msg == null || msg.what != MESSAGE_HIDE_VIEW) {
                    return;
                }
                volumeSeekView.hide();
                showSeekView = false;
            }
        };
        showSeekView = false;
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
        if (showSeekView) {
            volumeSeekView.refreshProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        } else {
            volumeSeekView.show(activityList.getLast(),
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }

        showSeekView = true;
        handler.removeMessages(MESSAGE_HIDE_VIEW);
        handler.sendEmptyMessageDelayed(MESSAGE_HIDE_VIEW, 2000);
    }

    private IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        return filter;
    }
}
