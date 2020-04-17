package com.example.wyyu.gitsamlpe.test.animation;

import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

/**
 * Created by wyyu on 2019-11-27.
 **/

public class ActivityBezierAnim extends ToolbarActivity {

    @BindView(R.id.wave_view_test) WaveViewTest waveViewTest;
    @BindView(R.id.wave_view_just) WaveViewJust waveViewJust;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_anim);

        initActivity();
    }

    private void initActivity() {
        initToolbar("BezierAnim", 0xffffffff, 0xff84919b);
    }

    @OnClick({ R.id.wave_num_t, R.id.wave_num_f, R.id.wave_num_s }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wave_num_t:
                waveViewTest.refreshProgress(30);
                waveViewJust.refreshProgress(30);
                break;
            case R.id.wave_num_f:
                waveViewTest.refreshProgress(50);
                waveViewJust.refreshProgress(50);
                break;
            case R.id.wave_num_s:
                waveViewTest.refreshProgress(70);
                waveViewJust.refreshProgress(70);
                break;
        }
    }
}
