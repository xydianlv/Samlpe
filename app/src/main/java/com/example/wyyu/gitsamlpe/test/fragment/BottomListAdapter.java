package com.example.wyyu.gitsamlpe.test.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/8/29.
 **/

public class BottomListAdapter extends RecyclerView.Adapter {

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BottomItemHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_bottom_list_item, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BottomItemHolder) holder).cacheView(position);
    }

    @Override public int getItemCount() {
        return 20;
    }

    private class BottomItemHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        BottomItemHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.bottom_list_item_text);
        }

        void cacheView(int position) {
            textView.setText(String.valueOf(position));
        }
    }
}
