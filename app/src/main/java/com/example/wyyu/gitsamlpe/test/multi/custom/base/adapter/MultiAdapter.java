package com.example.wyyu.gitsamlpe.test.multi.custom.base.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.test.multi.custom.base.holder.BinderManager;
import com.example.wyyu.gitsamlpe.test.multi.custom.base.holder.HolderBinder;
import com.example.wyyu.gitsamlpe.test.multi.custom.base.holder.IBinderManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class MultiAdapter extends RecyclerView.Adapter implements IMultiAdapter {

    private IBinderManager binderManager;
    private List<Object> itemList;

    public MultiAdapter() {
        binderManager = new BinderManager();
        itemList = new ArrayList<>();
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

    @Override
    public void register(@NonNull Class<?> dataClass, @NonNull HolderBinder holderBinder) {
        this.binderManager.register(dataClass, holderBinder);
    }

    @Override public void initItemList(List<?> itemList, boolean needClear) {
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

    @Override public void appendItemList(List<?> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override public void insertItem(Object item, int position) {
        if (item == null) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.add(position, item);

        notifyItemInserted(position);
    }

    @Override public void deleteItem(Object item) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        int index = this.itemList.indexOf(item);

        this.itemList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void updateItem(@NonNull RecyclerView recyclerView, Object item, int updateType) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        RecyclerView.ViewHolder viewHolder =
            recyclerView.findViewHolderForAdapterPosition(this.itemList.indexOf(item));
        if (viewHolder == null) {
            return;
        }
        this.binderManager.updateItem(viewHolder, item, updateType);
    }

    @Override public void notifyItem(Object item) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        notifyItemChanged(this.itemList.indexOf(item));
    }
}
