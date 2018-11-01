package com.example.wyyu.gitsamlpe.test.softkeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2018/11/1.
 **/

public class ActivitySwitchPanelTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySwitchPanelTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
