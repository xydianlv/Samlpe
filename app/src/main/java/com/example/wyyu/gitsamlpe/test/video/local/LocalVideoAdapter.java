package com.example.wyyu.gitsamlpe.test.video.local;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.matisse.internal.entity.Item;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.video.player.LiteVideoPlayerView;
import com.example.wyyu.gitsamlpe.test.video.player.VideoInfo;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2019/4/2.
 **/

public class LocalVideoAdapter extends RecyclerView.Adapter {

    private List<Item> videoItemList;

    LocalVideoAdapter() {
        videoItemList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoItemHolder(LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_local_video_item, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoItemHolder) viewHolder).cache(videoItemList.get(i));
    }

    @Override public int getItemCount() {
        return videoItemList == null ? 0 : videoItemList.size();
    }

    void setVideoItemList(List<Item> videoItemList) {
        if (videoItemList == null || videoItemList.isEmpty()) {
            return;
        }
        if (this.videoItemList == null) {
            this.videoItemList = new LinkedList<>();
        }
        this.videoItemList.clear();
        this.videoItemList.addAll(videoItemList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onClick(ResultItem item, int position);
    }

    private class VideoItemHolder extends RecyclerView.ViewHolder {

        private LiteVideoPlayerView playerView;
        private TextView videoInfo;

        VideoItemHolder(@NonNull View itemView) {
            super(itemView);

            playerView = itemView.findViewById(R.id.video_item_player);
            videoInfo = itemView.findViewById(R.id.video_item_info);
        }

        void cache(Item item) {
            videoInfo.setText(item.getContentUri().toString());
            playerView.setVideoInfo(new VideoInfo(item));
        }
    }
}
