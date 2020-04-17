package com.example.wyyu.gitsamlpe.test.anim.wave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-17.
 **/

public class ActivityAnimWave extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAnimWave.class));
    }

    @BindView(R.id.wave_test_anim) RecordAnimView animView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_test);
    }

    @OnClick({ R.id.wave_test_start, R.id.wave_test_stop }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wave_test_start:
                animView.startMove();
                break;
            case R.id.wave_test_stop:
                animView.stopMove();
                break;
        }
    }
}
