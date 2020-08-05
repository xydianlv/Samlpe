package com.example.wyyu.gitsamlpe.test.video.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import com.example.wyyu.gitsamlpe.test.video.widget.CMVideoPlayerView;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2020-08-03.
 **/

public class HolderVideoListItem extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_holder_video_list_item;

    private TextView titleView;
    private TextView infoView;
    private CMVideoPlayerView playerView;

    HolderVideoListItem(@NonNull View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.video_list_item_title);
        infoView = itemView.findViewById(R.id.video_list_item_info);
        playerView = itemView.findViewById(R.id.video_list_item_player);
    }

    void bindItemData(VideoItem videoItem) {
        titleView.setText(TextUtils.isEmpty(videoItem.title) ? "Title" : videoItem.title);
        infoView.setText(
            TextUtils.isEmpty(videoItem.sourceValue) ? "SourceValue" : videoItem.sourceValue);

        playerView.setPlayerValue(videoItem, UIUtils.getScreenWidth());
    }
}
