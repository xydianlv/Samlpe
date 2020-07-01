package com.example.wyyu.gitsamlpe.test.pager.transform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class ActivityPagerTransformTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerTransformTest.class));
    }

    @BindView(R.id.pager_main_list) ListViewMain showList;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_anim);

        initActivity();
    }

    private void initActivity() {
        initToolbar("PagerTransform", 0xffffffff, 0xff84919b);

        showList.addItem("立方体切换动画", v -> ActivityPagerAnimCube.open(ActivityPagerTransformTest.this))
            .addItem("翻转式切换动画", v -> ActivityPagerAnimOver.open(ActivityPagerTransformTest.this))
            .addItem("风车式切换动画", v -> ActivityPagerAnimWind.open(ActivityPagerTransformTest.this))
            .addItem("挤压式切换动画", v -> ActivityPagerAnimCrimp.open(ActivityPagerTransformTest.this))
            .addItem("侧边缩放式切换动画", v -> ActivityPagerAnimScaleSide.open(ActivityPagerTransformTest.this))
            .addItem("中心缩放式切换动画", v -> ActivityPagerAnimScaleCenter.open(ActivityPagerTransformTest.this))
            .addItem("景深式切换动画", v -> ActivityPagerAnimDepth.open(ActivityPagerTransformTest.this))
            .addItem("淡入淡出式切换动画", v -> ActivityPagerAnimFade.open(ActivityPagerTransformTest.this))
            .addItem("预览式切换动画", v -> ActivityPagerAnimPreview.open(ActivityPagerTransformTest.this))
            .addItem("简易缩放式切换动画", v -> ActivityPagerAnimSimple.open(ActivityPagerTransformTest.this))
            .refreshList();
    }
}
