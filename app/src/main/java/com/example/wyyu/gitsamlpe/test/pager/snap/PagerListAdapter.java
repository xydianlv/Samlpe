package com.example.wyyu.gitsamlpe.test.pager.snap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wyyu on 2020-05-13.
 **/

public class PagerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == HolderRecycler.LAYOUT) {
            return new HolderRecycler(LayoutInflater.from(viewGroup.getContext())
                .inflate(HolderRecycler.LAYOUT, viewGroup, false));
        } else {
            return new HolderPager(LayoutInflater.from(viewGroup.getContext())
                .inflate(HolderPager.LAYOUT, viewGroup, false));
        }
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == HolderPager.LAYOUT) {
            ((HolderPager) viewHolder).cacheData(i);
        }
    }

    @Override public int getItemViewType(int position) {
        return position == 0 ? HolderRecycler.LAYOUT : HolderPager.LAYOUT;
    }

    @Override public int getItemCount() {
        return 12;
    }
}
