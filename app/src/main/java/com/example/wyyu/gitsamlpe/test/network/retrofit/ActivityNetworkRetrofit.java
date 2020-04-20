package com.example.wyyu.gitsamlpe.test.network.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityNetworkRetrofit extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNetworkRetrofit.class));
    }

    @BindView(R.id.network_re_text) TextView textView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_retrofit);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.network_re_btn).setOnClickListener(v -> loadServerData());
    }

    private void loadServerData() {

    }
}
