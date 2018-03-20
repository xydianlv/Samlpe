package com.example.wyyu.gitsamlpe.test.recyclerview.weight;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class FastScrollBar extends LinearLayout {

    private static final String ANIMATOR_TYPE = "translationX";
    private static final long ANIMATOR_DELAY = 500;
    private static final int ANIMATOR_TIME = 160;

    private RecyclerView.OnScrollListener scrollListener;
    private LinearLayoutManager layoutManager;

    private float moveSection;
    private float downY;

    private float measuredWidth;

    private int moveCount;

    private ObjectAnimator animator;
    private boolean isScroll;

    public FastScrollBar(Context context) {
        super(context);
        initFastScrollBar();
    }

    public FastScrollBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFastScrollBar();
    }

    public FastScrollBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFastScrollBar();
    }

    private void initFastScrollBar() {
        LayoutInflater.from(getContext()).inflate(R.layout.fast_scroll_bar, this);

        initBasicData();

        initBasicView();
    }

    private void initBasicData() {
        moveSection = 0;
        downY = 0;

        animator = new ObjectAnimator();

        isScroll = false;
    }

    private void initBasicView() {

        scrollListener = new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (moveCount < 20 || isScroll) return;

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    showFastScrollBar();
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    hideFastScrollBar();
                }
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isScroll) return;

                setY(getScrollPercent(recyclerView) * moveSection);
            }
        };

        post(new Runnable() {
            @Override public void run() {
                measuredWidth = getMeasuredWidth();
                setTranslationX(measuredWidth);
            }
        });
    }

    private float getScrollPercent(RecyclerView recyclerView) {
        int i = recyclerView.computeVerticalScrollOffset();
        int j = recyclerView.computeVerticalScrollExtent();
        int k = recyclerView.computeVerticalScrollRange();
        return i / (float) (k - j);
    }

    private void showFastScrollBar() {

        if (animator != null) animator.cancel();

        animator = ObjectAnimator.ofFloat(this, ANIMATOR_TYPE, 0);

        animator.setDuration(ANIMATOR_TIME);
        animator.start();
    }

    private void hideFastScrollBar() {

        if (animator != null) animator.cancel();

        animator = ObjectAnimator.ofFloat(this, ANIMATOR_TYPE, measuredWidth);

        animator.setStartDelay(ANIMATOR_DELAY);
        animator.setDuration(ANIMATOR_TIME);
        animator.start();
    }

    public void setRecyclerView(final RecyclerView recyclerView) {

        recyclerView.post(new Runnable() {
            @Override public void run() {

                moveCount = recyclerView.getAdapter().getItemCount() - recyclerView.getChildCount();

                moveSection = recyclerView.getHeight() - getHeight();
            }
        });

        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(scrollListener);
    }

    @SuppressLint("ClickableViewAccessibility") @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showFastScrollBar();
                downY = event.getRawY();
                isScroll = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                refreshFromScrollBar(event.getRawY() - downY);
                downY = event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                isScroll = false;
                hideFastScrollBar();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private void refreshFromScrollBar(float moveValue) {

        float locationValue = getY() + moveValue;
        float divideValue;

        if (locationValue <= 0) {
            divideValue = 0f;
            setY(0);
        } else if (locationValue >= moveSection) {
            divideValue = 1f;
            setY(moveSection);
        } else {
            divideValue = locationValue / (moveSection);
            setY(locationValue);
        }

        layoutManager.scrollToPositionWithOffset((int) (divideValue * moveCount), 0);
    }
}
