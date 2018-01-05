package com.example.wyyu.gitsamlpe.test.slide;

import android.os.Bundle;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.slide.data.DataSource;
import com.example.wyyu.gitsamlpe.test.slide.data.ViewData;
import com.example.wyyu.gitsamlpe.test.slide.weight.ContentView;
import com.example.wyyu.gitsamlpe.test.slide.weight.ISlideViewPager;
import com.example.wyyu.gitsamlpe.test.slide.weight.SlideViewPager;

/**
 * Created by wyyu on 2018/1/2.
 **/

public class ActivitySlide extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        initActivitySlide();
    }

    private void initActivitySlide() {

        initToolbar("SlideTest", 0xffe6e6e6, 0xff84919b);

        initSlideViewPager();
    }

    private void initSlideViewPager() {

        final SlideViewPager slideViewPager = findViewById(R.id.slide_view_pager);

        slideViewPager.setOnLoadNextListener(new ISlideViewPager.OnLoadNextListener() {

            @Override
            public void loadNextView(int nextPosition) {
                slideViewPager.addNewView(getContentView(nextPosition));
            }
        });

        slideViewPager.addNewView(getContentView(0));
    }

    private ContentView getContentView(int position) {

        ViewData viewData = DataSource.getDataSource().getViewDataFromIndex(position);

        if (viewData == null) return null;

        ContentView contentView = new ContentView(this);

        contentView.setViewData(viewData);

        return contentView;
    }

}
