package com.example.wyyu.gitsamlpe.test.bigimage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.bigimage.weight.ImageDetailLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class ActivityImageDetail extends FullScreenActivity {

    private static final String KEY_IMAGE_RES_LIST = "key_image_res_list";
    private static final String KEY_IMAGE_INDEX = "key_image_index";

    public static void open(Context context, int[] resList, int index) {
        Intent intent = new Intent(context, ActivityImageDetail.class);
        intent.putExtra(KEY_IMAGE_RES_LIST, resList);
        intent.putExtra(KEY_IMAGE_INDEX, index);
        context.startActivity(intent);
    }

    public static void open(Context context, Bundle bundle, int[] resList, int index) {
        Intent intent = new Intent(context, ActivityImageDetail.class);
        intent.putExtra(KEY_IMAGE_RES_LIST, resList);
        intent.putExtra(KEY_IMAGE_INDEX, index);
        ActivityCompat.startActivity(context, intent, bundle);
    }

    private ViewPager viewPager;
    private ImageView funImage;

    private int[] imageArray;
    private int index;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        initImageDetail();
    }

    private void initImageDetail() {

        imageArray = getIntent().getIntArrayExtra(KEY_IMAGE_RES_LIST);
        index = getIntent().getIntExtra(KEY_IMAGE_INDEX, 0);

        viewPager = findViewById(R.id.image_detail_pager);
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setOffscreenPageLimit(imageArray.length - 1);
        viewPager.setCurrentItem(index);
        viewPager.setVisibility(View.GONE);

        funImage = findViewById(R.id.image_detail_fun_view);
        funImage.setImageResource(imageArray[index]);
        new CountDownTimer(500, 500) {

            @Override public void onTick(long l) {

            }

            @Override public void onFinish() {
                funImage.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<ImageDetailLayout> layoutList;

        ViewPagerAdapter() {
            layoutList = new ArrayList<>(imageArray.length);

            for (int imageId : imageArray) {
                ImageDetailLayout detailLayout = new ImageDetailLayout(ActivityImageDetail.this);
                detailLayout.setImageDetailValue(imageId);
                layoutList.add(detailLayout);
            }
        }

        @Override public int getCount() {
            return imageArray == null ? 0 : imageArray.length;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            container.addView(layoutList.get(position));
            return layoutList.get(position);
        }
    }

    @SuppressLint("NewApi") private void onFinish() {
        funImage.setVisibility(View.VISIBLE);
        funImage.setImageResource(imageArray[viewPager.getCurrentItem()]);
        viewPager.setVisibility(View.GONE);
        finishAfterTransition();
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onFinish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
