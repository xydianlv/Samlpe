package com.example.wyyu.gitsamlpe.test.video.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2020-07-31.
 **/

class VideoListAdapter extends RecyclerView.Adapter {

    private List<VideoItem> itemList;

    VideoListAdapter() {
        itemList = new ArrayList<>();
    }

    void setItemList(List<VideoItem> itemList, boolean needClear) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        if (needClear) {
            this.itemList.clear();
        }
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HolderVideoListItem(LayoutInflater.from(viewGroup.getContext())
            .inflate(HolderVideoListItem.LAYOUT, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((HolderVideoListItem) viewHolder).bindItemData(itemList.get(i));
    }

    @Override public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
