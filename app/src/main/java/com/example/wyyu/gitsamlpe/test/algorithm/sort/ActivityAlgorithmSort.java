package com.example.wyyu.gitsamlpe.test.algorithm.sort;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2020-08-06.
 **/

public class ActivityAlgorithmSort extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAlgorithmSort.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_sort);
    }

    private void initActivity() {

    }
}
