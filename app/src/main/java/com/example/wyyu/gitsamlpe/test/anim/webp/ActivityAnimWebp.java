package com.example.wyyu.gitsamlpe.test.anim.webp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimWebp extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimWebp.class));
    }

    private Animatable webpAnim;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_webp);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (webpAnim != null) {
            webpAnim.stop();
            webpAnim = null;
        }
    }

    private void initActivity() {
        initAutoPlay();
        initAnimClick();
        initAnimLaugh();
        initAnimThrow();
    }

    private void initAutoPlay() {

        SimpleDraweeView webpAuto = findViewById(R.id.anim_webp_auto);

        FrescoLoader.newFrescoLoader()
            .uri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_game_get_phone))
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .autoPlayAnimation(true)
            .into(webpAuto);
    }

    private void initAnimClick() {

        SimpleDraweeView webpClick = findViewById(R.id.anim_webp_click);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_feed_loading))
            .setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo,
                    @Nullable Animatable animatable) {
                    if (animatable != null) {
                        webpAnim = animatable;
                    }
                }
            })
            .build();

        webpClick.setController(controller);
        webpClick.setOnClickListener(v -> {
            if (webpAnim == null) {
                return;
            }
            if (webpAnim.isRunning()) {
                webpAnim.stop();
            } else {
                webpAnim.start();
            }
        });
    }

    private void initAnimLaugh() {
        SimpleDraweeView webpAuto = findViewById(R.id.anim_webp_laugh);

        FrescoLoader.newFrescoLoader()
            .uri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_webp_laugh))
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .autoPlayAnimation(true)
            .into(webpAuto);
    }

    private void initAnimThrow() {
        SimpleDraweeView webpAuto = findViewById(R.id.anim_webp_throw);

        FrescoLoader.newFrescoLoader()
            .uri(Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.mipmap.anim_webp_throw))
            .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
            .autoPlayAnimation(true)
            .into(webpAuto);
    }
}
