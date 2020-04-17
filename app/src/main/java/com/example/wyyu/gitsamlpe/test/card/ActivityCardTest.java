package com.example.wyyu.gitsamlpe.test.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.card.custom.ActivityCardCustom;
import com.example.wyyu.gitsamlpe.test.card.system.ActivityCardSystem;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityCardTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCardTest.class));
    }

    @BindView(R.id.card_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("CardTest", 0xffffffff, 0xff84919b);

        listView.addItem("Custom", v -> ActivityCardCustom.open(ActivityCardTest.this))
            .addItem("System", v -> ActivityCardSystem.open(ActivityCardTest.this))
            .refreshList();
    }
}
