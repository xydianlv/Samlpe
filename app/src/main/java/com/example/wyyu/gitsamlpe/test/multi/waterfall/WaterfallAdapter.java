package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.test.image.preview.ActivityImagePreview;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ImageLocation;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class WaterfallAdapter extends RecyclerView.Adapter {

    private static final int ITEM_HEADER = 0;
    private static final int ITEM_NORMAL = 1;

    private List<ItemBean> itemBeanList;

    WaterfallAdapter() {
        itemBeanList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ITEM_HEADER) {
            return new WaterfallHead(LayoutInflater.from(viewGroup.getContext())
                .inflate(WaterfallHead.LAYOUT, viewGroup, false));
        } else {
            return new WaterfallItem(LayoutInflater.from(viewGroup.getContext())
                .inflate(WaterfallItem.LAYOUT, viewGroup, false));
        }
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == ITEM_HEADER) {
            ((WaterfallHead) viewHolder).cacheItem();
        } else {
            WaterfallItem item = (WaterfallItem) viewHolder;
            item.cacheValue(itemBeanList.get(i));
            item.itemView.setOnClickListener(v -> {
                ActivityImagePreview.itemBeanList = itemBeanList;
                int[] location = item.loadImageLocation();
                ItemBean itemBean = itemBeanList.get(i);
                itemBean.imageLocation =
                    new ImageLocation(location[0], location[1], location[2], location[3]);
                ActivityImagePreview.open(item.itemView.getContext(), i);
            });
        }
    }

    @Override public int getItemCount() {
        return itemBeanList == null ? 0 : itemBeanList.size();
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        } else {
            return ITEM_NORMAL;
        }
    }

    void setItemBeanList(List<ItemBean> itemBeanList) {
        if (itemBeanList == null || itemBeanList.isEmpty()) {
            return;
        }
        if (this.itemBeanList == null) {
            this.itemBeanList = new LinkedList<>();
        }
        this.itemBeanList.clear();
        this.itemBeanList.addAll(itemBeanList);
        this.notifyDataSetChanged();
    }
}
