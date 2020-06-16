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
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.util.permission.IPermissionObserver;
import com.example.wyyu.gitsamlpe.util.permission.PermissionCheck;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItemCreator;
import com.example.wyyu.gitsamlpe.util.permission.PermissionItemKey;
import com.example.wyyu.gitsamlpe.util.permission.PermissionObservable;
import java.util.List;

/**
 * Created by wyyu on 2018/9/20.
 **/

public class ActivityAudioList extends ToolbarActivity implements IPermissionObserver {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityAudioList.class));
    }

    @BindView(R.id.audio_list) RecyclerView recyclerView;

    private AudioListAdapter adapter;
    private AudioListModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        PermissionObservable.getObservable().attach(this);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        AudioPlayer.getPlayer().stop();
        PermissionObservable.getObservable().detach(this);
    }

    @Override public void permissionGranted(int itemKey) {
        if (itemKey == PermissionItemKey.存储卡) {
            loadAudioList();
        }
    }

    @Override public void permissionDenied(int itemKey) {
        if (itemKey == PermissionItemKey.存储卡) {
            UToast.showShort(ActivityAudioList.this, "你已拒绝磁盘读取权限");
        }
    }

    private void initActivity() {
        initBasicView();
        initRecyclerView();
        initBasicValue();

        loadAudioList();
        //checkPermission();
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

    private void checkPermission() {
        new PermissionCheck().check(ActivityAudioList.this,
            PermissionItemCreator.createExtraPermission());
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
