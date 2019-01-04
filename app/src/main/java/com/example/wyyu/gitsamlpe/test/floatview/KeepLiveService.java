package com.example.wyyu.gitsamlpe.test.floatview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.wyyu.gitsamlpe.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019/1/4.
 **/

public class KeepLiveService extends Service {

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String notificationName = "channelName";
    private static final String notificationId = "channelId";

    @Override public void onCreate() {
        super.onCreate();

        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }

        //创建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName,
                NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1, getNotification());
    }

    private Notification getNotification() {
        Notification.Builder builder =
            new Notification.Builder(this).setSmallIcon(R.drawable.ic_empty_zhihu)
                .setContentTitle("")
                .setContentText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }

        return builder.build();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        showAlertToast();
        return super.onStartCommand(intent, flags, startId);
    }

    private static int number = 0;

    private void showAlertToast() {
        Observable<Integer> timeObserver = Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .take(3600)
            .map(new Func1<Long, Integer>() {
                @Override public Integer call(Long aLong) {
                    return aLong.intValue();
                }
            });

        timeObserver.doOnSubscribe(new Action0() {
            @Override public void call() {
                number = 0;
            }
        }).subscribe(new Observer<Integer>() {
            @Override public void onCompleted() {
            }

            @Override public void onError(Throwable e) {
            }

            @Override public void onNext(Integer integer) {
                Log.e("KeepLiveService", "number -> " + String.valueOf(number++));
            }
        });
    }
}
