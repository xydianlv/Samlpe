package com.example.wyyu.gitsamlpe.test.pager.snap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class ActivitySnapPagerTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySnapPagerTest.class));
    }

    @BindView(R.id.snap_pager_list) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_pager_test);

        initActivity();
    }

    private void initActivity() {
        recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PagerListAdapter());
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }
}
