package com.example.wyyu.gitsamlpe.framework.pagedialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.dialog.FullScreenDialogTest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/11/19.
 **/

public class PageDialog extends LinearLayout {

    public PageDialog(Context context) {
        this(context, null);
    }

    public PageDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPageDialog();
    }

    private ViewPager viewPager;
    private int presentPosition;

    private void initPageDialog() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_page_dialog, this);

        viewPager = findViewById(R.id.page_dialog_view_pager);

        viewPager.setAdapter(new PageDialogAdapter());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {
                presentPosition = position;
            }

            @Override public void onPageScrollStateChanged(int state) {

            }
        });
        presentPosition = 0;
    }

    private void onHeadClick(boolean isFirst) {
        if (viewPager == null) {
            return;
        }
        if (isFirst) {
            viewPager.setCurrentItem(presentPosition + 1);
        } else {
            viewPager.setCurrentItem(presentPosition - 1);
        }
    }

    private class PageDialogAdapter extends PagerAdapter {

        List<LinearLayout> layoutList;

        PageDialogAdapter() {
            TestLayout testLayoutA = new TestLayout(getContext());
            testLayoutA.bindPosition(0);
            testLayoutA.setOnHeadClickListener(new TestLayout.OnHeadClickListener() {
                @Override public void onClick(boolean isFirst) {
                    onHeadClick(isFirst);
                }
            });

            TestLayout testLayoutB = new TestLayout(getContext());
            testLayoutB.bindPosition(1);
            testLayoutB.setOnHeadClickListener(new TestLayout.OnHeadClickListener() {
                @Override public void onClick(boolean isFirst) {
                    onHeadClick(isFirst);
                }
            });

            layoutList = new ArrayList<>(2);

            layoutList.add(testLayoutA);
            layoutList.add(testLayoutB);
        }

        @Override public int getCount() {
            return layoutList == null ? 0 : layoutList.size();
        }

        @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(layoutList.get(position));
            return layoutList.get(position);
        }
    }

    public static void show(Context context) {
        new FullScreenDialogTest.Builder(context).show(new PageDialog(context));
    }
}
