package com.example.wyyu.gitsamlpe.test.algorithm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.algorithm.sort.ActivityAlgorithmSort;
import com.example.wyyu.gitsamlpe.test.algorithm.tree.ActivityAlgorithmTree;

/**
 * Created by wyyu on 2020-08-06.
 **/

public class ActivityAlgorithmTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAlgorithmTest.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("AlgorithmTest");

        ListViewMain listViewMain = findViewById(R.id.algorithm_test_list);

        listViewMain.addItem("Sort", v -> ActivityAlgorithmSort.open(ActivityAlgorithmTest.this))
            .addItem("Tree", v -> ActivityAlgorithmTree.open(ActivityAlgorithmTest.this))
            .refreshList();
    }
}
