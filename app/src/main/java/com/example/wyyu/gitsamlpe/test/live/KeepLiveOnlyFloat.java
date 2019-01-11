package com.example.wyyu.gitsamlpe.test.live;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019/1/11.
 **/

public class KeepLiveOnlyFloat extends Service {

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    // 启动一个悬浮窗
    private void showFloatingWindow() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (windowManager == null) {
            return;
        }

        View view = new View(AppController.getAppContext());
        view.setBackgroundColor(0xffff0000);

        // 设置LayoutParam
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.height = 24;
        layoutParams.width = 24;
        layoutParams.x = 0;
        layoutParams.y = 0;

        // 将悬浮窗控件添加到WindowManager
        windowManager.addView(view, layoutParams);

        doRefreshNumber();
    }

    private static int number = 0;

    private void doRefreshNumber() {
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
