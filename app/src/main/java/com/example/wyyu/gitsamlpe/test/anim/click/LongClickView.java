package com.example.wyyu.gitsamlpe.test.anim.click;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-06-18.
 **/

public class LongClickView extends FrameLayout {

    public LongClickView(@NonNull Context context) {
        super(context);
        initClickView();
    }

    public LongClickView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initClickView();
    }

    public LongClickView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClickView();
    }

    // 数字图标
    private static final int[] NUM_ICON_ARRAY = new int[] {
        R.mipmap.long_anim_0, R.mipmap.long_anim_1, R.mipmap.long_anim_2, R.mipmap.long_anim_3,
        R.mipmap.long_anim_4, R.mipmap.long_anim_5, R.mipmap.long_anim_6, R.mipmap.long_anim_7,
        R.mipmap.long_anim_8, R.mipmap.long_anim_9
    };
    // 文案图标
    private static final int[] TAG_ICON_ARRAY = new int[] {
        R.mipmap.long_anim_float_w, R.mipmap.long_anim_float_h, R.mipmap.long_anim_float_f
    };

    private static final int SIZE_SCREEN_WIDTH = UIUtils.getScreenWidth();
    private static final int SIZE_PADDING = UIUtils.dpToPx(12.0f);

    // 数字+文案布局
    private LinearLayout container;
    // 表情元素
    private AnimView animView;
    // 缩放图标
    private View imageView;

    // 主图标缩放动画
    private ObjectAnimator animator;
    // 时长自增的个数
    private int count;
    // 锚点X
    private int x;

    // 当前锚点ID
    private long currentId;

    private void initClickView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_long_click_view, this);

        initValue();
        initView();
        initAnim();
    }

    private void initValue() {
        count = 0;
        x = 0;
    }

    private void initView() {
        container = findViewById(R.id.long_click_container);
        imageView = findViewById(R.id.long_click_image);
        animView = findViewById(R.id.long_click_anim_view);

        animView.setRateListener(this::refreshCount);
    }

    private void refreshCount() {
        if (container == null) {
            return;
        }
        count++;
        container.removeAllViews();

        int viewCount = String.valueOf(count).length();
        int funCount = count;

        for (int index = 0; index < viewCount; index++) {
            ImageView view = new ImageView(getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            int data = (int) Math.pow(10, viewCount - 1 - index);
            view.setImageResource(NUM_ICON_ARRAY[funCount / data]);
            funCount = funCount % data;

            container.addView(view);
        }

        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (count < 10) {
            imageView.setImageResource(TAG_ICON_ARRAY[0]);
        } else if (count < 20) {
            imageView.setImageResource(TAG_ICON_ARRAY[1]);
        } else {
            imageView.setImageResource(TAG_ICON_ARRAY[2]);
        }
        container.addView(imageView);
    }

    private void initAnim() {
        PropertyValuesHolder animSX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.4f, 1.0f);
        PropertyValuesHolder animSY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.4f, 1.0f);

        animator = ObjectAnimator.ofPropertyValuesHolder(imageView, animSX, animSY);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(AnimView.DURATION_ANIM);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (container != null) {
                    container.setVisibility(INVISIBLE);
                }
                if (imageView != null) {
                    imageView.setVisibility(INVISIBLE);
                }
                if (animView != null) {
                    animView.endAnim();
                }
            }

            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (container != null) {
                    container.setVisibility(VISIBLE);
                }
                if (imageView != null) {
                    imageView.setVisibility(VISIBLE);
                }
            }

            @Override public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                if (container != null) {
                    container.setVisibility(INVISIBLE);
                }
                if (imageView != null) {
                    imageView.setVisibility(INVISIBLE);
                }
                if (animView != null) {
                    animView.endAnim();
                }
            }
        });
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (container == null || imageView == null) {
            return;
        }
        int widthLayout = container.getWidth() == 0 ? UIUtils.dpToPx(64.0f) : container.getWidth();
        if (x >= SIZE_SCREEN_WIDTH * 2 / 3) {
            container.setX(x - widthLayout + imageView.getWidth() + SIZE_PADDING);
        } else if (x <= SIZE_SCREEN_WIDTH / 3) {
            container.setX(x - SIZE_PADDING);
        } else {
            container.setX(x + imageView.getWidth() * 1.0f / 2 - widthLayout * 1.0f / 2);
        }
    }

    /**
     * 展示长按动画
     *
     * @param anchorView 锚点View
     */
    public void showAnim(@NonNull View anchorView) {
        int[] location = new int[2];
        anchorView.getLocationInWindow(location);

        this.x = location[0];
        if (anchorView.getId() != currentId) {
            count = 0;
            currentId = anchorView.getId();
        }
        if (imageView != null) {
            imageView.setX(location[0]);
            imageView.setY(location[1]);
        }
        if (container != null) {
            container.setY(location[1] - anchorView.getHeight() - UIUtils.dpToPx(24.0f));
            container.setX(location[0]);
        }
        if (animView != null) {
            animView.showAnim(location[0], location[1]);
        }
        if (animator == null) {
            initAnim();
        }
        animator.start();
    }

    /**
     * 结束动画
     */
    public void endAnim() {
        if (animator != null && animator.isStarted()) {
            animator.cancel();
        }
    }
}
