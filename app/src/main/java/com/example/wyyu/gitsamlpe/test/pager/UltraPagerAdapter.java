package com.example.wyyu.gitsamlpe.test.pager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/6/29.
 **/

public class UltraPagerAdapter extends PagerAdapter {

    @Override public int getCount() {
        return 3;
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return LayoutInflater.from(container.getContext())
            .inflate(R.layout.layout_banner_element, container, false);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
