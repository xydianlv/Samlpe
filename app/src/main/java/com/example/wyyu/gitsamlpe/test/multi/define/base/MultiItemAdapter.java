package com.example.wyyu.gitsamlpe.test.multi.define.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class MultiItemAdapter extends RecyclerView.Adapter implements IMultiItemAdapter {

    // 对列表多类型进行管理的类
    private MultiTypeManager typeManager;
    // 数据列表，Object 需要自定义 equals 方法
    private List<Object> itemList;

    // 用来动态获取 ViewHolder 的 RecyclerView
    private RecyclerView recyclerView;

    public MultiItemAdapter() {
        typeManager = new MultiTypeManager();
        itemList = new ArrayList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return typeManager.onCreateViewHolder(viewGroup, i);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        typeManager.onBindViewHolder(viewHolder, itemList.get(i));
    }

    @Override public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override public int getItemViewType(int position) {
        return typeManager.getItemViewType(itemList.get(position));
    }

    @Override public void register(Class<?> dataClass, HolderBinder holderBinder) {
        typeManager.register(dataClass, holderBinder);
    }

    @Override public void bindRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override public void setItemList(List<?> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override public void updateItem(Object item, int updateType) {
        RecyclerView.ViewHolder viewHolder =
            recyclerView.findViewHolderForAdapterPosition(itemList.indexOf(item));
        if (viewHolder == null) {
            return;
        }
        typeManager.updateItem(viewHolder, item, updateType);
    }
}
