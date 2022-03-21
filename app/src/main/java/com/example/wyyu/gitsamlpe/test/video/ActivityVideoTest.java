package com.example.wyyu.gitsamlpe.test.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.video.list.ActivityVideoList;
import com.example.wyyu.gitsamlpe.test.video.simple.ActivitySimplePlayer;
import com.example.wyyu.gitsamlpe.test.video.waterfall.ActivityVideoWaterfall;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class ActivityVideoTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityVideoTest.class));
    }

    @BindView(R.id.video_test_list) ListViewMain list;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("VideoTest");

        list.addItem("List", v -> ActivityVideoList.open(ActivityVideoTest.this))
            .addItem("WaterFall", v -> ActivityVideoWaterfall.open(ActivityVideoTest.this))
            .addItem("SimplePlayer", v -> ActivitySimplePlayer.open(ActivityVideoTest.this))
            .refreshList();
    }
}
