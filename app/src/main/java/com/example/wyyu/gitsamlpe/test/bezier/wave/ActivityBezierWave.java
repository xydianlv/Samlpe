package com.example.wyyu.gitsamlpe.test.bezier.wave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2020-04-20.
 **/

public class ActivityBezierWave extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityBezierWave.class));
    }

    @BindView(R.id.bezier_wave_static) WaveViewStatic waveStatic;
    @BindView(R.id.bezier_wave_quick) WaveViewQuick waveQuick;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_wave);
    }

    @OnClick({ R.id.bezier_wave_thirty, R.id.bezier_wave_fifty, R.id.bezier_wave_seventy })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bezier_wave_thirty:
                waveStatic.refreshProgress(30);
                waveQuick.refreshProgress(30);
                break;
            case R.id.bezier_wave_fifty:
                waveStatic.refreshProgress(50);
                waveQuick.refreshProgress(50);
                break;
            case R.id.bezier_wave_seventy:
                waveStatic.refreshProgress(70);
                waveQuick.refreshProgress(70);
                break;
        }
    }
}
