package com.example.wyyu.gitsamlpe.test.video.waterfall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class ActivityVideoWaterfall extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityVideoWaterfall.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
