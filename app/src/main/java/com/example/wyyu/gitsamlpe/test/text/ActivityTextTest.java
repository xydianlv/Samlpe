package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.text.adapter.ActivityTextAdapter;
import com.example.wyyu.gitsamlpe.test.text.anim.ActivityTextAnim;
import com.example.wyyu.gitsamlpe.test.text.font.ActivityTextFont;
import com.example.wyyu.gitsamlpe.test.text.multi.ActivityTextMulti;
import com.example.wyyu.gitsamlpe.test.text.shadow.ActivityTextShadow;

/**
 * Created by wyyu on 2019-12-31.
 **/

public class ActivityTextTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_show_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("TextShowTest", 0xffffffff, 0xff84919b);

        ListViewMain viewMain = findViewById(R.id.text_show_list);

        viewMain.addItem("Adapt", v -> ActivityTextAdapter.open(ActivityTextTest.this))
            .addItem("Multi", v -> ActivityTextMulti.open(ActivityTextTest.this))
            .addItem("Anim", v -> ActivityTextAnim.open(ActivityTextTest.this))
            .addItem("Font", v -> ActivityTextFont.open(ActivityTextTest.this))
            .addItem("Shadow", v -> ActivityTextShadow.open(ActivityTextTest.this))
            .refreshList();
    }
}
