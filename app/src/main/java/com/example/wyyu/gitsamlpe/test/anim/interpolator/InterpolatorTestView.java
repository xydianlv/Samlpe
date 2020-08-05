package com.example.wyyu.gitsamlpe.test.anim.interpolator;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-07-01.
 **/

public class InterpolatorTestView extends LinearLayout {

    public InterpolatorTestView(@NonNull Context context) {
        this(context, null);
    }

    public InterpolatorTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InterpolatorTestView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InterpolatorTestView);
        try {
            type = typedArray.getInteger(R.styleable.InterpolatorTestView_animType, 0);
        } finally {
            typedArray.recycle();
        }
        initTestView();
    }

    private static final String[] TITLE_ARRAY = new String[] {
        "AccelerateInterpolator", "AccelerateDecelerateInterpolator", "AnticipateInterpolator",
        "AnticipateOvershootInterpolator", "BounceInterpolator", "CycleInterpolator",
        "DecelerateInterpolator", "FastOutLinearInInterpolator", "FastOutSlowInInterpolator",
        "LinearInterpolator", "LinearOutSlowInInterpolator", "OvershootInterpolator",
        "PathInterpolator"
    };

    private static final int DURATION = 2000;

    private ObjectAnimator animator;
    private View animView;
    private int type;

    private void initTestView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_interpolator_test_view, this);

        TextView animTitle = findViewById(R.id.interpolator_test_title);
        animView = findViewById(R.id.interpolator_test_anim);

        animTitle.setText(TITLE_ARRAY[type]);
        setOnClickListener(v -> startAnim());
    }

    private void initAnim() {
        animator = ObjectAnimator.ofFloat(animView, "translationX", 0,
            getMeasuredWidth() - UIUtils.dpToPx(12.0f * 2 + 32.0f));

        animator.setInterpolator(interpolator());
        animator.setDuration(DURATION);
        animator.setRepeatCount(0);
    }

    private TimeInterpolator interpolator() {
        switch (type) {
            case 0:
                return new AccelerateInterpolator();
            case 1:
                return new AccelerateDecelerateInterpolator();
            case 2:
                return new AnticipateInterpolator();
            case 3:
                return new AnticipateOvershootInterpolator();
            case 4:
                return new BounceInterpolator();
            case 5:
                return new CycleInterpolator(1.0f);
            case 6:
                return new DecelerateInterpolator();
            case 7:
                return new FastOutLinearInInterpolator();
            case 8:
                return new FastOutSlowInInterpolator();
            case 9:
                return new LinearInterpolator();
            case 10:
                return new LinearOutSlowInInterpolator();
            case 11:
                return new OvershootInterpolator();
            //case 12:
            //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //        return new PathInterpolator(new Path(0, 0, 1, 1));
            //    }
            default:
                return new LinearInterpolator();
        }
    }

    void startAnim() {
        if (animator == null) {
            initAnim();
        }
        if (animator.isRunning()) {
            animator.cancel();
        }
        animator.start();
    }
}
