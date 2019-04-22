package com.example.wyyu.gitsamlpe.test.text;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by wyyu on 2019/4/22.
 **/
public class AnimTextView extends AppCompatTextView implements LifecycleObserver {

    public AnimTextView(Context context) {
        super(context);
    }

    public AnimTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) public void onResume() {
        isPaused = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) public void onDestroy() {
        isPaused = true;
    }

    private String contentText;
    private int presentIndex;

    private boolean isPaused;

    public void setContentText(String text) {
        contentText = text;
        presentIndex = 1;

        showText();
    }

    private void showText() {
        if (presentIndex > contentText.length() || isPaused) {
            return;
        }
        setText(contentText.substring(0, presentIndex));

        boolean isNextLine =
            presentIndex < contentText.length() && contentText.charAt(presentIndex) == '\n';

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override public void call() {
                presentIndex++;
                showText();
            }
        }, 160 * (isNextLine ? 3 : 1), TimeUnit.MILLISECONDS);
    }
}
