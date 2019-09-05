package com.example.wyyu.gitsamlpe.test.lottie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import java.io.InputStream;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-09-05.
 **/

public class ActivityLottieTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLottieTest.class));
    }

    @BindView(R.id.lottie_test_webp) SimpleDraweeView webpTest;
    @BindView(R.id.lottie_test_a) LottieAnimationView lottieA;
    @BindView(R.id.lottie_test_b) LottieAnimationView lottieB;
    @BindView(R.id.lottie_test_c) LottieAnimationView lottieC;

    private boolean lottieAEnd;
    private boolean lottieBEnd;
    private boolean lottieCEnd;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("LottieTest", 0xffffffff, 0xff84919b);

        initAnim();
        initClick();
    }

    private void initAnim() {
        loadLottie("anim_on_up.json", lottieA);
        loadLottie("anim_on_down.json", lottieB);
        loadLottie("anim_up_guide.json", lottieC);

        lottieA.enableMergePathsForKitKatAndAbove(true);
        lottieB.enableMergePathsForKitKatAndAbove(true);
        lottieC.enableMergePathsForKitKatAndAbove(true);

        lottieAEnd = false;
        lottieBEnd = false;
        lottieCEnd = false;

        FrescoLoader.newFrescoLoader()
            .uri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_game_get_phone))
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .autoPlayAnimation(true)
            .into(webpTest);
    }

    private void loadLottie(final String cacheKey, final LottieAnimationView lottieAnim) {
        Observable.unsafeCreate(new Observable.OnSubscribe<LottieComposition>() {
            @Override public void call(Subscriber<? super LottieComposition> subscriber) {
                try {
                    final InputStream isDown = ActivityLottieTest.this.getAssets().open(cacheKey);
                    subscriber.onNext(
                        LottieCompositionFactory.fromJsonInputStreamSync(isDown, cacheKey)
                            .getValue());
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<LottieComposition>() {
                @Override public void call(LottieComposition composition) {
                    if (lottieAnim != null && composition != null) {
                        lottieAnim.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        lottieAnim.setComposition(composition);
                        lottieAnim.setProgress(0.0f);
                    }
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    ULog.show(throwable.getMessage());
                }
            });
    }

    private void initClick() {
        lottieA.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (lottieAEnd) {
                    lottieA.setProgress(0.0f);
                    lottieAEnd = false;
                } else {
                    lottieA.playAnimation();
                    lottieAEnd = true;
                }
            }
        });
        lottieB.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (lottieBEnd) {
                    lottieB.setSpeed(-1.0f);
                    lottieB.playAnimation();
                    lottieBEnd = false;
                } else {
                    lottieB.setSpeed(1.0f);
                    lottieB.playAnimation();
                    lottieBEnd = true;
                }
            }
        });
        lottieC.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (lottieCEnd) {
                    lottieC.setProgress(0.0f);
                    lottieCEnd = false;
                } else {
                    lottieC.playAnimation();
                    lottieCEnd = true;
                }
            }
        });
    }
}
