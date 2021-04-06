package com.example.wyyu.gitsamlpe.test.pager.vertical.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wyyu on 2021/3/18.
 **/

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildHolder(
            LayoutInflater.from(parent.getContext()).inflate(ChildHolder.LAYOUT, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ChildHolder) holder).bindIndex(position);
    }

    @Override public int getItemCount() {
        return 6;
    }
}
