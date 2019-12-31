package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityTextShowTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextShowTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_show_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("TextShowTest", 0xffffffff, 0xff84919b);

        ListViewMain viewMain = findViewById(R.id.text_show_list);

        viewMain.addItem("AdaptTextShow", v -> ActivityAdapterText.open(ActivityTextShowTest.this))
            .addItem("MultiTextShow", v -> ActivityMultiText.open(ActivityTextShowTest.this))
            .addItem("AnimTextShow", v -> ActivityAnimText.open(ActivityTextShowTest.this))
            .addItem("TextFontShow", v -> ActivityTextFont.open(ActivityTextShowTest.this))
            .refreshList();
    }
}
