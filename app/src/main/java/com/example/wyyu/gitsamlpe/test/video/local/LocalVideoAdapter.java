package com.example.wyyu.gitsamlpe.test.video.local;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.matisse.internal.entity.Item;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.image.matisse.AspectRatioFrameLayout;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2019/4/2.
 **/

public class LocalVideoAdapter extends RecyclerView.Adapter {

    private OnItemClickListener itemClickListener;

    private List<Item> videoItemList;
    private Drawable placeHolder;

    LocalVideoAdapter() {
        TypedArray ta = AppController.getAppContext()
            .getTheme()
            .obtainStyledAttributes(new int[] { com.zhihu.matisse.R.attr.item_placeholder });
        placeHolder = ta.getDrawable(0);

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

    void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {

        void onClick(ResultItem item, int position);
    }

    private class VideoItemHolder extends RecyclerView.ViewHolder {

        private AspectRatioFrameLayout ratioFrameLayout;
        private SimpleDraweeView videoCover;
        private TextView videoInfo;

        VideoItemHolder(@NonNull View itemView) {
            super(itemView);

            ratioFrameLayout = itemView.findViewById(R.id.video_item_layout);
            videoCover = itemView.findViewById(R.id.video_item_cover);
            videoInfo = itemView.findViewById(R.id.video_item_info);

            ratioFrameLayout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }

        void cache(Item item) {
            videoInfo.setText(item.getContentUri().toString());

            if (item.width > item.height) {
                ratioFrameLayout.setAspectRatio((float) item.width / item.height);
            } else {
                ratioFrameLayout.setAspectRatio(1.0f);
            }

            FrescoLoader.newFrescoLoader()
                .placeHolder(placeHolder)
                .resize(120, 120)
                .imageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .uri(item.getContentUri())
                .into(videoCover);
        }
    }
}
