package com.example.wyyu.gitsamlpe.test.video.simple;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.video.player.SimpleVideoPlayer;

import java.io.File;

public class ActivitySimplePlayer extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivitySimplePlayer.class));
    }

    private FrameLayout videoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_player);

        initActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SimpleVideoPlayer.getPlayer().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleVideoPlayer.getPlayer().stop();
    }

    private void initActivity() {
        initView();
        tryPlay();
    }

    private void initView() {
        initToolbar("SimpleVideoTest", 0xffffffff, 0xff84919b);

        videoContainer = findViewById(R.id.player_container);
    }

    private void tryPlay() {
        TextureView textureView = new TextureView(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;

        videoContainer.removeAllViews();
        videoContainer.addView(textureView, params);

        File testFile = new File("/storage/emulated/0/DCIM/Camera/lv_0_20220214142017.mp4");
        SimpleVideoPlayer.getPlayer().setDataAndPlay(Uri.fromFile(testFile), textureView);
    }
}
