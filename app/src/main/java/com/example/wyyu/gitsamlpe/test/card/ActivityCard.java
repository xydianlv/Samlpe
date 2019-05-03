package com.example.wyyu.gitsamlpe.test.card;

import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.http.HttpGet;
import com.example.wyyu.gitsamlpe.util.http.IHttpCallBack;
import java.io.IOException;
import okhttp3.Response;

/**
 * Created by wyyu on 2018/1/10.
 **/

public class ActivityCard extends FullScreenActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        initView();
    }

    private void initView() {

        final RecordAnimView animationView = findViewById(R.id.animation_view);

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                animationView.startMove();
            }
        });

        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                animationView.stopMove();
            }
        });

        findViewById(R.id.bt_request).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        HttpGet.asyncGet("http://47.105.199.13/collection/list_all", "", new IHttpCallBack() {
            @Override public void onFailure(IOException exception) {
                ULog.show(exception.getMessage());
            }

            @Override public void onResponse(Response response) {
                ULog.show(response.message());
            }
        });
    }
}
