package com.example.wyyu.gitsamlpe.test.pager.snap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class ActivityPagerSnapTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPagerSnapTest.class));
    }

    @BindView(R.id.snap_pager_list) RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_pager_test);

        initActivity();
    }

    private void initActivity() {

        initToolbar("SnapTest");

        recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PagerListAdapter());
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }
}
