package com.example.wyyu.gitsamlpe.test.pager.snap;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class PagerListAdapter extends RecyclerView.Adapter {

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HolderPager(LayoutInflater.from(viewGroup.getContext())
            .inflate(HolderPager.LAYOUT, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((HolderPager) viewHolder).cacheData(i);
    }

    @Override public int getItemCount() {
        return 12;
    }
}
