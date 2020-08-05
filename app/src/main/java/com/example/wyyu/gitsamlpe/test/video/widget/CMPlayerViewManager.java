package com.example.wyyu.gitsamlpe.test.video.widget;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.test.video.player.ICMVideoPlayer;
import java.lang.ref.WeakReference;

/**
 * Created by wyyu on 2020-07-28.
 **/

public class CMPlayerViewManager {

    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static void judgeScreenOn(Context context, @ICMVideoPlayer.MediaStatus int status) {
        Activity activity = CMPlayerViewManager.getAppCompActivity(context);
        if (activity != null && activity.getWindow() != null) {
            if (status == ICMVideoPlayer.MediaStatus.PLAYING) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (status == ICMVideoPlayer.MediaStatus.PAUSE
                || status == ICMVideoPlayer.MediaStatus.COMPLETE) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    private static WeakReference<CMFullPlayerView> playerReference = null;

    public static void attachPlayer(CMFullPlayerView fullScreenPlayer) {
        playerReference = new WeakReference<>(fullScreenPlayer);
    }

    public static void detachPlayer() {
        if (playerReference != null) {
            playerReference.clear();
            playerReference = null;
        }
    }

    public static boolean onPressBack() {
        if (playerReference != null && playerReference.get() != null) {
            CMFullPlayerView player = playerReference.get();
            player.detachPlayerShow();
            return true;
        }
        return false;
    }
}
