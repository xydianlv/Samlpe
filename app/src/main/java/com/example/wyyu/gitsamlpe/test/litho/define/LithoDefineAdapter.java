package com.example.wyyu.gitsamlpe.test.litho.define;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LithoContainer;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.example.wyyu.gitsamlpe.test.litho.define.holder.ContainerHolder;
import com.example.wyyu.gitsamlpe.test.litho.define.holder.DefineHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class LithoDefineAdapter extends RecyclerView.Adapter {

    private List<DefineData> dataList;

    LithoDefineAdapter() {
        dataList = new ArrayList<>();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new DefineHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(DefineHolder.LAYOUT, viewGroup, false));
        } else {
            return new ContainerHolder(new LithoContainer(viewGroup.getContext()));
        }
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 0) {
            ((DefineHolder) viewHolder).cacheValue(dataList.get(i));
        } else {
            ((ContainerHolder) viewHolder).cacheValue(dataList.get(i));
        }
    }

    @Override public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override public int getItemViewType(int position) {
        return dataList.get(position).index % 2;
    }

    void setDataList(List<DefineData> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        this.dataList.clear();

        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
