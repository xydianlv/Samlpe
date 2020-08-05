package com.example.wyyu.gitsamlpe.test.pager.slide.weight;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.wyyu.gitsamlpe.test.pager.slide.data.ViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/1/3.
 **/

public class ViewPagerAdapter extends PagerAdapter {

    private List<ContentView> contentViewList;

    public ViewPagerAdapter(Context context) {

        contentViewList = new ArrayList<>();

        ContentView contentViewA = new ContentView(context);
        ContentView contentViewB = new ContentView(context);
        ContentView contentViewC = new ContentView(context);

        contentViewA.setViewData(new ViewData("1", 0x44ff0000));
        contentViewB.setViewData(new ViewData("2", 0x44ff00ff));
        contentViewC.setViewData(new ViewData("3", 0x4400ff00));

        contentViewList.add(contentViewA);
        contentViewList.add(contentViewB);
        contentViewList.add(contentViewC);
    }

    public void setViewData(int position, ViewData viewData) {

        contentViewList.get(position).setViewData(viewData);
    }

    @Override
    public int getCount() {
        return contentViewList == null ? 0 : contentViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(contentViewList.get(position));
        return contentViewList.get(position);
    }
}
