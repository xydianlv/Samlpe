package com.example.wyyu.gitsamlpe.test.text.xfermode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by wyyu on 2020-06-08.
 **/

public class XModeAdapter extends RecyclerView.Adapter {

    private List<XModeData> list;

    XModeAdapter(List<XModeData> list) {
        this.list = list;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XModeHolder(LayoutInflater.from(viewGroup.getContext())
            .inflate(XModeHolder.LAYOUT, viewGroup, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((XModeHolder) viewHolder).bindData(list.get(i));
    }

    @Override public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
