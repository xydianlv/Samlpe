package com.example.wyyu.gitsamlpe.test.image.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.List;

/**
 * Created by wyyu on 2020-01-14.
 **/

public class ActivityImagePreview extends BaseActivity {

    private static final String KET_CLICK_ITEM_INDEX = "key_click_item_index";

    public static List<ItemBean> itemBeanList = null;

    public static void open(Context context, int index) {
        if (itemBeanList == null || itemBeanList.isEmpty()) {
            return;
        }
        if (index < 0 || index >= itemBeanList.size()) {
            return;
        }
        Intent intent = new Intent(context, ActivityImagePreview.class);
        intent.putExtra(KET_CLICK_ITEM_INDEX, index);
        context.startActivity(intent);
    }

    @BindView(R.id.pager_image_preview) ViewPager viewPager;

    private int clickIndex;

    @Override protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initActivity();
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (itemBeanList != null) {
            itemBeanList = null;
        }
    }

    private void initActivity() {
        initValue();
        initView();
    }

    private void initValue() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        clickIndex = intent.getIntExtra(KET_CLICK_ITEM_INDEX, 0);
    }

    private void initView() {
        PreviewPagerAdapter adapter =
            new PreviewPagerAdapter(getSupportFragmentManager(), itemBeanList);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(clickIndex);
        viewPager.setOffscreenPageLimit(2);
    }
}
