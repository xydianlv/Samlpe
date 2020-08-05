package com.example.wyyu.gitsamlpe.test.anim.svga;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityAnimSvgA extends FullScreenActivity implements View.OnClickListener {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimSvgA.class));
    }

    @BindView(R.id.anim_svg_lottie) LottieAnimationView animationView;
    @BindView(R.id.anim_svg_surface) FrameLayout surfaceContainer;
    @BindView(R.id.anim_svg_file) FrameLayout fileContainer;
    @BindView(R.id.anim_svg_a) SVGAImageView imageView;

    @BindView(R.id.anim_svg_click_a) TextView clickA;
    @BindView(R.id.anim_svg_click_surface) TextView clickSurface;
    @BindView(R.id.anim_svg_click_file) TextView clickFile;
    @BindView(R.id.anim_svg_click_lottie) TextView clickLottie;

    private SvgAFileSurface surfaceB;
    private SvgASurface surfaceA;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_svg_a);

        initActivity();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anim_svg_click_a:
                refreshPage(1);
                break;
            case R.id.anim_svg_click_surface:
                refreshPage(2);
                break;
            case R.id.anim_svg_click_file:
                refreshPage(3);
                break;
            case R.id.anim_svg_click_lottie:
                refreshPage(4);
                break;
        }
    }

    private void initActivity() {
        initClick();
        refreshPage(0);
    }

    private void initClick() {
        clickA.setOnClickListener(this);
        clickSurface.setOnClickListener(this);
        clickFile.setOnClickListener(this);
        clickLottie.setOnClickListener(this);
    }

    private void refreshPage(int currentPage) {
        setAImageShow(currentPage);
        setSurfaceShow(currentPage);
        setFileSurfaceShow(currentPage);
        setLottieShow(currentPage);

        clickA.setTextColor(
            getResources().getColor(currentPage == 1 ? R.color.ct_1 : R.color.ct_3));
        clickSurface.setTextColor(
            getResources().getColor(currentPage == 2 ? R.color.ct_1 : R.color.ct_3));
        clickFile.setTextColor(
            getResources().getColor(currentPage == 3 ? R.color.ct_1 : R.color.ct_3));
        clickLottie.setTextColor(
            getResources().getColor(currentPage == 4 ? R.color.ct_1 : R.color.ct_3));
    }

    private void setAImageShow(int currentPage) {
        if (imageView.isAnimating()) {
            imageView.stopAnimation(true);
        }
        if (currentPage != 1) {
            imageView.setVisibility(View.GONE);
            return;
        }
        imageView.setVisibility(View.VISIBLE);
        imageView.setLoops(1);
        SVGAParser parser = new SVGAParser(this);
        parser.decodeFromAssets("anim_svg_test.svga", new SVGAParser.ParseCompletion() {
            @Override public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {
                if (imageView != null) {
                    imageView.setVideoItem(svgaVideoEntity);
                    imageView.stepToFrame(0, true);
                }
            }

            @Override public void onError() {

            }
        });
    }

    private void setSurfaceShow(int currentPage) {
        if (surfaceA != null) {
            surfaceA.cancelAnim();
            surfaceContainer.removeAllViews();
        }
        if (currentPage != 2) {
            surfaceContainer.setVisibility(View.GONE);
            return;
        }
        surfaceA = new SvgASurface(this);

        surfaceContainer.setVisibility(View.VISIBLE);
        surfaceContainer.addView(surfaceA);
    }

    private void setFileSurfaceShow(int currentPage) {
        if (surfaceB != null) {
            surfaceB.cancelAnim();
            fileContainer.removeAllViews();
        }
        if (currentPage != 3) {
            fileContainer.setVisibility(View.GONE);
            return;
        }

        Observable.unsafeCreate((Observable.OnSubscribe<String>) subscriber -> {
            File cacheFile = getCacheDir();
            if (cacheFile == null) {
                subscriber.onError(new Throwable("缓存目录获取失败"));
            } else {
                File file = new File(cacheFile.getAbsolutePath() + "/anim_svg_a.svga");
                if (file.exists()) {
                    subscriber.onNext(file.getAbsolutePath());
                } else {
                    ByteArrayOutputStream byteOutputStream = null;
                    FileOutputStream outputStream = null;
                    InputStream inputStream = null;
                    byte[] buffer = new byte[10];
                    try {
                        byteOutputStream = new ByteArrayOutputStream();
                        outputStream = new FileOutputStream(file);
                        // 需要保留文件格式的资源放在 assets 目录下加载到 SD
                        inputStream = getAssets().open("anim_svg_test.svga");
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteOutputStream.write(buffer, 0, len);
                        }
                        outputStream.write(byteOutputStream.toByteArray());
                        outputStream.flush();
                        subscriber.onNext(file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable("缓存本地失败"));
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (byteOutputStream != null) {
                            try {
                                byteOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            subscriber.onCompleted();
        }).subscribe(s -> {
            UToast.showShort(ActivityAnimSvgA.this, "缓存SVGA成功");
            if (fileContainer != null) {
                surfaceB = new SvgAFileSurface(this);
                fileContainer.setVisibility(View.VISIBLE);
                fileContainer.addView(surfaceB);
            }
        }, throwable -> {
            UToast.showShort(ActivityAnimSvgA.this, throwable.getMessage());
            ULog.show(throwable.getMessage());
        });
    }

    private void setLottieShow(int currentPage) {
        if (animationView.isAnimating()) {
            animationView.pauseAnimation();
        }
        if (currentPage != 4) {
            animationView.setVisibility(View.GONE);
            return;
        }
        animationView.setVisibility(View.VISIBLE);
        Observable.unsafeCreate((Observable.OnSubscribe<LottieComposition>) subscriber -> {
            try {
                if (animationView != null) {
                    animationView.setImageAssetsFolder("anim_svg_lottie/images");
                }
                String cacheKey = "anim_svg_lottie/data.json";
                InputStream inStream = ActivityAnimSvgA.this.getAssets().open(cacheKey);
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
                    if (animationView != null && composition != null) {
                        animationView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        animationView.setComposition(composition);
                        animationView.setProgress(0.0f);
                        animationView.playAnimation();
                    }
                }
            });
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationView != null) {
                    animationView.pauseAnimation();
                }
            }
        });
    }
}
