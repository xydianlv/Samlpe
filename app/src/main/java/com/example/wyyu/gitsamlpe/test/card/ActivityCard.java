package com.example.wyyu.gitsamlpe.test.card;

import android.os.Bundle;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

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
    }
}
