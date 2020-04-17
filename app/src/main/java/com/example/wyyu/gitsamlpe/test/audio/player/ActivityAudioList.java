package com.example.wyyu.gitsamlpe.test.audio.player;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import java.util.List;

/**
 * Created by wyyu on 2018/9/20.
 **/

public class ActivityAudioList extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAudioList.class));
    }

    @BindView(R.id.audio_list) RecyclerView recyclerView;

    private AudioListAdapter adapter;
    private AudioListModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        AudioPlayer.getPlayer().stop();
    }

    private void initActivity() {
        initBasicView();
        initRecyclerView();
        initBasicValue();
        loadAudioList();
    }

    private void initBasicView() {

        initToolbar("播放测试");
    }

    private void initRecyclerView() {

        adapter = new AudioListAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
    }

    private void initBasicValue() {

        model = ViewModelProviders.of(this).get(AudioListModel.class);
    }

    private void loadAudioList() {

        model.loadAudioList(new AudioListModel.LoadAudioListCallback() {
            @Override public void onSuccess(List<AudioDataBean> audioDataList) {
                if (adapter != null) {
                    adapter.setAudioDataList(audioDataList);
                }
            }

            @Override public void onFailure() {

            }
        });
    }
}
