package com.example.wyyu.gitsamlpe.test.text;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-01-20.
 **/

public class ActivityTextShadow extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTextShadow.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_shadow);

        initActivity();
    }

    private void initActivity() {
        initToolbar("TextShadowTest", 0xffffffff, 0xff84919b);
    }
}
