package com.example.wyyu.gitsamlpe.test.algorithm.tree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.BaseActivity;

/**
 * Created by wyyu on 2020-08-06.
 **/

public class ActivityAlgorithmTree extends BaseActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAlgorithmTree.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_tree);

        initActivity();
    }

    private void initActivity() {

    }
}
