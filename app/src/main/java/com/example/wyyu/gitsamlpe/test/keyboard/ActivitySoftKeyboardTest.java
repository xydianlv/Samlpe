package com.example.wyyu.gitsamlpe.test.keyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.keyboard.show.ActivityAdjustPan;
import com.example.wyyu.gitsamlpe.test.keyboard.show.ActivityAdjustResize;
import com.example.wyyu.gitsamlpe.test.keyboard.show.ActivityAdjustUnspecified;

/**
 * Created by wyyu on 2021/1/8.
 **/

public class ActivitySoftKeyboardTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySoftKeyboardTest.class));
    }

    @BindView(R.id.keyboard_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_keyboard_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("SoftKeyboardTestList", 0xffffffff, 0xff84919b);

        listView.addItem("AdjustUnspecified",
            v -> ActivityAdjustUnspecified.open(ActivitySoftKeyboardTest.this))
            .addItem("AdjustResize", v -> ActivityAdjustResize.open(ActivitySoftKeyboardTest.this))
            .addItem("AdjustPan", v -> ActivityAdjustPan.open(ActivitySoftKeyboardTest.this))
            .refreshList();
    }
}
