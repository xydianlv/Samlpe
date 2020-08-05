package com.example.wyyu.gitsamlpe.test.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2019/3/1.
 **/

public class MultiHolderAdapter extends RecyclerView.Adapter {

    private List<MultiData> multiDataList;

    MultiHolderAdapter() {
        multiDataList = new LinkedList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return HolderCreator.createMultiHolder(viewGroup, getItemViewType(i));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((BaseViewHolder) viewHolder).bindItemValue(multiDataList.get(i));
    }

    @Override public int getItemCount() {
        return multiDataList == null ? 0 : multiDataList.size();
    }

    @Override public int getItemViewType(int position) {
        return multiDataList.get(position).type;
    }

    void setMultiDataList(List<MultiData> multiDataList) {
        if (multiDataList == null || multiDataList.isEmpty()) {
            return;
        }
        if (this.multiDataList == null) {
            this.multiDataList = new LinkedList<>();
        }
        this.multiDataList.clear();
        this.multiDataList.addAll(multiDataList);
        notifyDataSetChanged();
    }

    void appendDataList(List<MultiData> multiDataList) {
        if (multiDataList == null || multiDataList.isEmpty()) {
            return;
        }
        if (this.multiDataList == null) {
            this.multiDataList = new LinkedList<>();
        }
        int startIndex = this.multiDataList.size() - 1;
        this.multiDataList.addAll(multiDataList);

        notifyItemRangeInserted(startIndex, multiDataList.size());
    }
}
