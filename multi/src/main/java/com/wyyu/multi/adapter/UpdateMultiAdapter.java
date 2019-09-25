package com.wyyu.multi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.wyyu.multi.binder.IBinderManager;

/**
 * Created by wyyu on 2019-09-25.
 *
 * 一个具有局部刷新功能的 Adapter
 **/

public class UpdateMultiAdapter extends MultiAdapter<Class<?>, Object>
    implements IUpdateItem<Object> {

    public UpdateMultiAdapter(@NonNull IBinderManager<Class<?>, Object> binderManager) {
        super(binderManager);
    }

    @Override
    public void updateItem(@NonNull RecyclerView recyclerView, Object item, int updateType) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        replaceItem(item);

        RecyclerView.ViewHolder viewHolder =
            recyclerView.findViewHolderForAdapterPosition(this.itemList.indexOf(item));
        if (viewHolder == null) {
            return;
        }
        this.binderManager.updateItem(viewHolder, item, updateType);
    }

    @Override
    public void updateItem(@NonNull RecyclerView recyclerView, int position, int updateType) {
        if (this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        if (position < 0 || position >= this.itemList.size()) {
            return;
        }
        Object item = itemList.get(position);
        replaceItem(item);

        RecyclerView.ViewHolder viewHolder =
            recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
            return;
        }
        this.binderManager.updateItem(viewHolder, item, updateType);
    }

    @Override public void notifyItem(Object item) {
        if (item == null || this.itemList == null || this.itemList.isEmpty()) {
            return;
        }
        replaceItem(item);
        notifyItemChanged(this.itemList.indexOf(item));
    }

    private void replaceItem(Object item) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        int index = itemList.indexOf(item);
        itemList.remove(index);
        itemList.add(index, item);
    }
}
