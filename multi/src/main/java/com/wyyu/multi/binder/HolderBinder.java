package com.wyyu.multi.binder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wyyu on 2019-09-24.
 **/

public abstract class HolderBinder {

    @NonNull
    protected abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
        @NonNull Object item);

    protected abstract void updateItem(@NonNull RecyclerView.ViewHolder viewHolder,
        @NonNull Object item, int updateType);

    protected View onCreateView(@NonNull ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }
}
