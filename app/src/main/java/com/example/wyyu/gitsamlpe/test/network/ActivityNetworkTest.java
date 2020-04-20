package com.example.wyyu.gitsamlpe.test.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.network.custom.ActivityNetworkCustom;
import com.example.wyyu.gitsamlpe.test.network.retrofit.ActivityNetworkRetrofit;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityNetworkTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNetworkTest.class));
    }

    @BindView(R.id.network_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("NetworkTest", 0xffffffff, 0xff84919b);

        listView.addItem("Custom", v -> ActivityNetworkCustom.open(ActivityNetworkTest.this))
            .addItem("Retrofit", v -> ActivityNetworkRetrofit.open(ActivityNetworkTest.this))
            .refreshList();
    }
}
