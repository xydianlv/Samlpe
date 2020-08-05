package com.wyyu.multi.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import com.wyyu.multi.binder.HolderBinder;
import com.wyyu.multi.binder.IBinderManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class MultiAdapter<T, V> extends RecyclerView.Adapter implements IMultiAdapter<T, V> {

    /**
     * 在 Adapter 中执行类型分配管理功能
     */
    IBinderManager<T, V> binderManager;
    /**
     * Adapter 持有的数据列表
     */
    List<V> itemList;

    public MultiAdapter(@NonNull IBinderManager<T, V> binderManager) {
        this.binderManager = binderManager;
        this.itemList = new ArrayList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return binderManager.onCreateViewHolder(viewGroup, i);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        binderManager.onBindViewHolder(viewHolder, itemList.get(i));
    }

    @Override public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override public int getItemViewType(int position) {
        return this.binderManager.getItemViewType(itemList.get(position));
    }

    @Override public void register(@NonNull T keyValue, @NonNull HolderBinder holderBinder) {
        this.binderManager.register(keyValue, holderBinder);
    }

    @Override public void initItemList(List<V> itemList, boolean needClear) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        if (needClear) {
            this.itemList.clear();
        }
        this.itemList.addAll(0, itemList);
        notifyDataSetChanged();
    }

    @Override public void appendItemList(List<V> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override public void insertItem(V item, int position) {
        if (item == null) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.add(position, item);

        notifyItemInserted(position);
    }

    @Override public void removeItem(V item) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        int index = this.itemList.indexOf(item);

        this.itemList.remove(index);
        notifyItemRemoved(index);
    }
}