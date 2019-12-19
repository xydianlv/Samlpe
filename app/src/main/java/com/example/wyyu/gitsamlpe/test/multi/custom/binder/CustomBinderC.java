package com.example.wyyu.gitsamlpe.test.multi.custom.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataC;
import com.wyyu.multi.binder.HolderBinder;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class CustomBinderC extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CustomBinderC.HolderCustomC(
            onCreateView(parent, CustomBinderC.HolderCustomC.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((CustomBinderC.HolderCustomC) holder).cacheValue((CustomDataC) item);

        Log.e("CustomListTest", "index -> " + ((CustomDataC) item).index);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderCustomC extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_custom_c;

        private TextView textView;

        HolderCustomC(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_custom_text);
        }

        void cacheValue(CustomDataC dataC) {
            textView.setText(String.valueOf(dataC.index));
        }
    }
}