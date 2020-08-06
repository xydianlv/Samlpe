package com.example.wyyu.gitsamlpe.test.fresco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.fresco.blr.ActivityFrescoBlr;
import com.example.wyyu.gitsamlpe.test.fresco.cache.ActivityFrescoCache;

/**
 * Created by wyyu on 2019/1/2.
 **/

public class ActivityFrescoTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityFrescoTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("FrescoTest");

        ListViewMain listViewMain = findViewById(R.id.fresco_test_list);

        listViewMain.addItem("FrescoBlr", v -> ActivityFrescoBlr.open(ActivityFrescoTest.this))
            .addItem("FrescoCache", v -> ActivityFrescoCache.open(ActivityFrescoTest.this))
            .refreshList();
    }
}
