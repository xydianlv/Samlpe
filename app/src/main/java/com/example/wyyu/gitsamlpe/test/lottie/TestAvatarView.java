package com.example.wyyu.gitsamlpe.test.lottie;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class TestAvatarView extends FrameLayout {

    public TestAvatarView(@NonNull Context context) {
        super(context);
        initAvatarView();
    }

    public TestAvatarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAvatarView();
    }

    public TestAvatarView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAvatarView();
    }

    private View avatarImage;
    private View avatarAnim;

    private AnimatorSet animSet;

    private void initAvatarView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_test_avatar_view, this);

        initView();
        initClick();
        initAnim();
    }

    private void initView() {
        avatarImage = findViewById(R.id.avatar_image);
        avatarAnim = findViewById(R.id.avatar_anim);
    }

    private void initClick() {
        setOnClickListener(v -> {
            if (animSet == null) {
                return;
            }
            if (animSet.isRunning()) {
                animSet.cancel();
            } else {
                animSet.start();
            }
        });
    }

    private void initAnim() {
        ObjectAnimator animISX = ObjectAnimator.ofFloat(avatarImage, "scaleX", 1.0f, 0.9f, 1.0f);
        ObjectAnimator animISY = ObjectAnimator.ofFloat(avatarImage, "scaleY", 1.0f, 0.9f, 1.0f);
        ObjectAnimator animASX = ObjectAnimator.ofFloat(avatarAnim, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator animASY = ObjectAnimator.ofFloat(avatarAnim, "scaleY", 1.0f, 1.2f, 1.0f);
        ObjectAnimator animAA = ObjectAnimator.ofFloat(avatarAnim, "alpha", 1.0f, 0.0f, 0.0f);

        animISX.setRepeatCount(ValueAnimator.INFINITE);
        animISY.setRepeatCount(ValueAnimator.INFINITE);
        animASX.setRepeatCount(ValueAnimator.INFINITE);
        animASY.setRepeatCount(ValueAnimator.INFINITE);
        animAA.setRepeatCount(ValueAnimator.INFINITE);

        animSet = new AnimatorSet();
        animSet.playTogether(animISX, animISY, animASX, animASY, animAA);
        animSet.setDuration(1200);
        animSet.setInterpolator(new LinearInterpolator());
    }
}
