package com.example.wyyu.gitsamlpe.framework.video.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import java.lang.ref.WeakReference;

/**
 * Created by wyyu on 2019-12-26.
 **/
public class PlayerViewManager {

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

    private static WeakReference<TFullScreenPlayerView> playerReference = null;

    public static void attachPlayer(TFullScreenPlayerView fullScreenPlayer) {
        playerReference = new WeakReference<>(fullScreenPlayer);
    }

    public static void detachPlayer() {
        playerReference.clear();
        playerReference = null;
    }

    public static boolean onPressBack() {
        if (playerReference != null && playerReference.get() != null) {
            TFullScreenPlayerView player = playerReference.get();
            player.detachPlayerShow();
            return true;
        }
        return false;
    }
}
