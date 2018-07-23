package com.example.wyyu.gitsamlpe.test.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2018/7/23.
 **/

public class ActivityVideoList extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityVideoList.class));
    }

    @BindView(R.id.video_list) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VideoListAdapter(this));
        recyclerView.setItemAnimator(null);
    }
}
