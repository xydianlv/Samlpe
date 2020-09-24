package com.example.wyyu.gitsamlpe.test.anim.lottie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import java.io.InputStream;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimLottie extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimLottie.class));
    }

    @BindView(R.id.anim_lottie_up) LottieAnimationView lottieUp;
    @BindView(R.id.anim_lottie_down) LottieAnimationView lottieDown;
    @BindView(R.id.anim_lottie_click) LottieAnimationView lottieClick;
    @BindView(R.id.anim_lottie_switch) LottieAnimationView lottieSwitch;

    private boolean lottieAEnd;
    private boolean lottieBEnd;
    private boolean lottieCEnd;
    private boolean lottieDEnd;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_lottie);

        initActivity();
    }

    private void initActivity() {
        initAnim();
        initClick();
    }

    private void initAnim() {
        loadLottie("anim_on_up.json", lottieUp);
        loadLottie("anim_on_down.json", lottieDown);
        loadLottie("anim_up_guide.json", lottieClick);
        loadLottie("anim_on_up.json", lottieSwitch);

        lottieAEnd = false;
        lottieBEnd = false;
        lottieCEnd = true;
        lottieDEnd = false;

        lottieClick.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (lottieClick != null) {
                    lottieClick.playAnimation();
                }
            }
        });
    }

    private void loadLottie(final String cacheKey, final LottieAnimationView lottieAnim) {
        Observable.unsafeCreate((Observable.OnSubscribe<LottieComposition>) subscriber -> {
            try {
                final InputStream isDown = ActivityAnimLottie.this.getAssets().open(cacheKey);
                subscriber.onNext(
                    LottieCompositionFactory.fromJsonInputStreamSync(isDown, cacheKey).getValue());
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(composition -> {
                if (lottieAnim != null && composition != null) {
                    lottieAnim.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    lottieAnim.setComposition(composition);
                    lottieAnim.setProgress(0.0f);
                }
            }, throwable -> ULog.show(throwable.getMessage()));
    }

    private void initClick() {
        lottieUp.setOnClickListener(v -> {
            if (lottieAEnd) {
                lottieUp.setProgress(0.0f);
                lottieAEnd = false;
            } else {
                lottieUp.playAnimation();
                lottieAEnd = true;
            }
        });
        lottieDown.setOnClickListener(v -> {
            if (lottieBEnd) {
                lottieDown.setSpeed(-1.0f);
                lottieDown.playAnimation();
                lottieBEnd = false;
            } else {
                lottieDown.setSpeed(1.0f);
                lottieDown.playAnimation();
                lottieBEnd = true;
            }
        });
        lottieClick.setOnClickListener(v -> {
            if (lottieCEnd) {
                lottieClick.playAnimation();
                lottieCEnd = false;
            } else {
                lottieClick.cancelAnimation();
                lottieCEnd = true;
            }
        });
        lottieSwitch.setOnClickListener(v -> {
            if (lottieDEnd) {
                loadLottie("anim_on_up.json", lottieSwitch);
            } else {
                loadLottie("anim_on_down.json", lottieSwitch);
            }
            lottieDEnd = !lottieDEnd;
            lottieSwitch.playAnimation();
        });
    }
}
