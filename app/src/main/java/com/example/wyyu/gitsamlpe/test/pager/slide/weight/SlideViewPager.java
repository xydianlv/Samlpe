package com.example.wyyu.gitsamlpe.test.pager.slide.weight;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wyyu.gitsamlpe.framework.ULog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2018/1/5.
 * 滑动中动态加载数据的 ViewPager
 **/

public class SlideViewPager extends ViewPager implements ISlideViewPager {

    private static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private int presentIndex;

    private OnLoadNextListener loadNextListener;

    private SlideViewCache slideViewCache;
    private SlideAdapter slideAdapter;

    private boolean canSlideRight;
    private boolean canSlideLeft;


    public SlideViewPager(Context context) {
        super(context);
        initSlideViewPager();
    }

    public SlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSlideViewPager();
    }

    private void initSlideViewPager() {

        slideViewCache = new SlideViewCache();
        slideAdapter = new SlideAdapter();

        setOffscreenPageLimit(3);
        setAdapter(slideAdapter);

        canSlideRight = false;
        canSlideLeft = false;

        addPageChangeListener();

        setCurrentItem(1, false);
    }

    private void addPageChangeListener() {

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ULog.show("SlideViewPager -> onSelected -> position : " + position);

                presentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                ULog.show("SlideViewPager -> onStateChanged -> state : " + state);

                if (state == 0) adjustViewPager();
            }
        });
    }

    private void adjustViewPager() {

        switch (presentIndex) {
            case 0:
                loadPreView();
                break;
            case 1:
                loadPresentView();
                break;
            case 2:
                loadNextView();
                break;
        }

        presentIndex = 1;
    }

    private void loadPresentView() {

        ULog.show("SlideViewPager -> loadPresentView ");

        ViewGroup viewGroup = slideAdapter.parentViewList.get(1);

        viewGroup.removeAllViews();
        viewGroup.addView(slideViewCache.getAccountView(slideViewCache.presentIndex));

        if (slideViewCache.isEdgeView()) {
            loadNextListener.loadNextView(slideViewCache.getNextIndex());
        } else {
            cacheFunView();
        }
    }

    private void cacheFunView() {

        ULog.show("SlideViewPager -> cacheFunView");

        int nextIndex = slideViewCache.presentIndex + 1;
        int preIndex = slideViewCache.presentIndex - 1;

        canSlideRight = nextIndex < slideViewCache.cacheNum;
        canSlideLeft = preIndex >= 0;

        if (canSlideLeft) {

            ULog.show("SlideViewPager -> cacheLeftView");

            slideAdapter.parentViewList.get(0).removeAllViews();
            slideAdapter.parentViewList.get(0).addView(slideViewCache.getAccountView(preIndex));
        }
        if (canSlideRight) {

            ULog.show("SlideViewPager -> cacheRightView");

            slideAdapter.parentViewList.get(1).removeAllViews();
            slideAdapter.parentViewList.get(1).addView(slideViewCache.getAccountView(nextIndex));
        }
    }

    private void loadPreView() {

        slideViewCache.refreshPresentIndex(-1);

        loadPresentView();

        setCurrentItem(1, false);
    }

    private void loadNextView() {

        slideViewCache.refreshPresentIndex(1);

        loadPresentView();

        setCurrentItem(1, false);
    }

    @Override
    public void setOnLoadNextListener(OnLoadNextListener loadNextListener) {
        this.loadNextListener = loadNextListener;
    }

    @Override
    public void addNewView(View view) {

        if (view != null) {

            ULog.show("SlideViewPager -> addNewView");

            view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            slideViewCache.cacheNewView(view);

            if (slideViewCache.cacheNum == 0) {

                ULog.show("SlideViewPager -> addFirstView");

                slideViewCache.cacheNum++;
                loadPresentView();

                return;
            }
        }

        cacheFunView();
    }


    private class SlideAdapter extends PagerAdapter {

        private static final int SLIDE_VIEW_NUM = 3;

        private List<LinearLayout> parentViewList;

        SlideAdapter() {
            parentViewList = new ArrayList<>();

            for (int index = 0; index < SLIDE_VIEW_NUM; index++) {
                parentViewList.add(new LinearLayout(getContext()));
            }
        }

        @Override
        public int getCount() {
            return parentViewList == null ? 0 : parentViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(parentViewList.get(position));
            return parentViewList.get(position);
        }
    }

    private class SlideViewCache {

        private static final int CACHE_SIZE = 5;
        private int presentIndex;
        private int cacheNum;

        private LinkedList<View> viewList;

        SlideViewCache() {

            viewList = new LinkedList<>();

            presentIndex = 0;
            cacheNum = 0;
        }

        void cacheNewView(View view) {

            if (presentIndex == 0 && CACHE_SIZE == 1) {
                addViewToLast(view);
            }

            if (presentIndex == 0) {
                addViewToFirst(view);
            } else {
                addViewToLast(view);
            }
        }

        private void addViewToFirst(View view) {

            if (viewList.size() == CACHE_SIZE) {
                viewList.removeLast();
                cacheNum--;
            }

            viewList.addFirst(view);
        }

        private void addViewToLast(View view) {

            if (viewList.size() == CACHE_SIZE) {
                viewList.removeFirst();
            }

            viewList.addLast(view);
            cacheNum++;
        }

        void refreshPresentIndex(int funValue) {
            presentIndex = presentIndex + funValue;
        }

        View getAccountView(int index) {
            return viewList.get(index);
        }

        int getNextIndex() {
            return cacheNum - (viewList.size() - presentIndex - 1);
        }

        boolean isEdgeView() {
            return presentIndex == 0 || presentIndex == viewList.size() - 1;
        }
    }
}
