package com.example.wyyu.gitsamlpe.test.pager.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.banner.ConvenientBanner;
import com.example.banner.holder.CBViewHolderCreator;
import com.example.banner.holder.Holder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-06-24.
 **/

public class ActivityBannerTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBannerTest.class));
    }

    @BindView(R.id.banner_test_banner) ConvenientBanner<Integer> banner;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_test);

        initActivity();
    }

    private void initActivity() {

        initToolbar("BannerTest");

        List<Integer> imageList = new ArrayList<>(3);
        for (int index = 0; index < 3; index++) {
            imageList.add(R.mipmap.image_test_2);
        }
        banner.setPages(new CBViewHolderCreator() {
            @Override public Holder createHolder(View itemView) {
                return new BannerHolder(itemView);
            }

            @Override public int getLayoutId() {
                return BannerHolder.LAYOUT;
            }
        }, imageList)
            .setCanLoop(true)
            .setPageIndicator(
                new int[] { R.drawable.button_circle_back_grey, R.drawable.button_circle_back_red })
            .setCurrentItem(0, true)
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }
}
