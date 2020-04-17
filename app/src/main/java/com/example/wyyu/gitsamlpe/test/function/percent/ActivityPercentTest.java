package com.example.wyyu.gitsamlpe.test.function.percent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019/2/18.
 **/

public class ActivityPercentTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityPercentTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_test);

        initActivity();
    }

    private void initActivity() {
        PercentCore percentCoreA = findViewById(R.id.percent_1);

        percentCoreA.setPercentValue(0.41f, 0xffff0000, 0xffcccccc);

        PercentBack percentCoreB = findViewById(R.id.percent_2);

        percentCoreB.setPercentValue(0.24f, 0xff00ff00, 0xffeeeeee);
    }
}
