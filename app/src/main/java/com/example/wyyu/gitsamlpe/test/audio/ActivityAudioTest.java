package com.example.wyyu.gitsamlpe.test.audio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.ListViewMain;
import com.example.wyyu.gitsamlpe.test.audio.player.ActivityAudioList;
import com.example.wyyu.gitsamlpe.test.audio.recorder.ActivityAudioRecorder;

/**
 * Created by wyyu on 2020-04-16.
 **/

public class ActivityAudioTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAudioTest.class));
    }

    @BindView(R.id.audio_test_list) ListViewMain listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_test);

        initActivity();
    }

    private void initActivity() {
        initToolbar("AudioTest", 0xffffffff, 0xff84919b);

        listView.addItem("Recorder", v -> ActivityAudioRecorder.open(ActivityAudioTest.this))
            .addItem("Player", v -> ActivityAudioList.open(ActivityAudioTest.this))
            .refreshList();
    }
}
