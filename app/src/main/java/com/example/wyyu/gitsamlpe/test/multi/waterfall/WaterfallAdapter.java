package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class WaterfallAdapter extends RecyclerView.Adapter {

    private List<ItemBean> itemBeanList;

    WaterfallAdapter() {
        itemBeanList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WaterfallItem(LayoutInflater.from(viewGroup.getContext())
            .inflate(WaterfallItem.LAYOUT, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((WaterfallItem) viewHolder).cacheValue(itemBeanList.get(i));
    }

    @Override public int getItemCount() {
        return itemBeanList == null ? 0 : itemBeanList.size();
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
