package com.example.wyyu.gitsamlpe.test.video;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.file.StorageUtil;

/**
 * Created by wyyu on 2018/7/23.
 **/

public class VideoListAdapter extends RecyclerView.Adapter {

    private static final String VIDEO_PATH = StorageUtil.getExternalStoragePath()
        + "/tencent/MicroMsg/2ac12e0f65da05353a3f32ce41f5193f/video/兔子.mp4";

    private Activity activity;

    VideoListAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(
            LayoutInflater.from(activity).inflate(R.layout.layout_video_list_item, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VideoHolder) holder).cacheVideo();
    }

    @Override public int getItemCount() {
        return 10;
    }

    private class VideoHolder extends RecyclerView.ViewHolder {

        private JZVideoPlayerStandard playerView;

        VideoHolder(View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.video_item_player);
        }

        void cacheVideo() {
            playerView.setUp(VIDEO_PATH, JZVideoPlayer.SCREEN_WINDOW_LIST, "");
        }
    }
}
