package com.example.wyyu.gitsamlpe.test.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;

/**
 * Created by wyyu on 2019/3/20.
 **/

public class ActivityLiveDataTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityLiveDataTest.class));
    }

    private LiveDataTimerViewModel liveDataTimerViewModel;

    private TextView time;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_data_test);

        time = findViewById(R.id.live_data_test_time);

        liveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);

        subscribe();
    }

    private void subscribe() {
        Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override public void onChanged(@Nullable Long aLong) {
                time.setText(String.format("Time : %s", String.valueOf(aLong)));
            }
        };

        liveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }
}
