package com.example.wyyu.gitsamlpe.test.multi.custom.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataB;
import com.wyyu.multi.binder.HolderBinder;

/**
 * Created by wyyu on 2019-09-24.
 **/
public class CustomBinderB extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CustomBinderB.HolderCustomB(
            onCreateView(parent, CustomBinderB.HolderCustomB.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((CustomBinderB.HolderCustomB) holder).cacheValue((CustomDataB) item);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderCustomB extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_custom_b;

        private TextView textView;

        HolderCustomB(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_custom_text);
        }

        void cacheValue(CustomDataB dataB) {
            textView.setText(String.valueOf(dataB.index));
        }
    }
}