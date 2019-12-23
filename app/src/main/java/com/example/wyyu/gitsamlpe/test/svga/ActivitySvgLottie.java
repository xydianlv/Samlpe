package com.example.wyyu.gitsamlpe.test.svga;

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
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import java.io.InputStream;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-12-23.
 **/

public class ActivitySvgLottie extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySvgLottie.class));
    }

    @BindView(R.id.svg_lottie) LottieAnimationView lottieAnim;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_lottie);

        initActivity();
    }

    private void initActivity() {
        Observable.unsafeCreate((Observable.OnSubscribe<LottieComposition>) subscriber -> {
            try {
                if (lottieAnim != null) {
                    lottieAnim.setImageAssetsFolder("anim_svg_lottie/images");
                }
                String cacheKey = "anim_svg_lottie/data.json";
                InputStream inStream = ActivitySvgLottie.this.getAssets().open(cacheKey);
                subscriber.onNext(
                    LottieCompositionFactory.fromJsonInputStreamSync(inStream, cacheKey)
                        .getValue());
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<LottieComposition>() {
                @Override public void onCompleted() {
                }

                @Override public void onError(Throwable e) {
                }

                @Override public void onNext(LottieComposition composition) {
                    if (lottieAnim != null && composition != null) {
                        lottieAnim.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        lottieAnim.setComposition(composition);
                        lottieAnim.setProgress(0.0f);
                        lottieAnim.playAnimation();
                    }
                }
            });
        lottieAnim.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (lottieAnim != null) {
                    lottieAnim.pauseAnimation();
                }
            }
        });
    }
}
