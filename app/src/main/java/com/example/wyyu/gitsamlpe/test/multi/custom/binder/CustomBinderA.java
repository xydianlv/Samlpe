package com.example.wyyu.gitsamlpe.test.multi.custom.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.custom.data.CustomDataA;
import com.wyyu.multi.binder.HolderBinder;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class CustomBinderA extends HolderBinder {

    public static final int UPDATE_TYPE_A = 0;

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderCustomA(onCreateView(parent, HolderCustomA.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((HolderCustomA) holder).cacheValue((CustomDataA) item);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderCustomA extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_custom_a;

        private TextView textView;

        HolderCustomA(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_custom_text);
        }

        void cacheValue(CustomDataA dataA) {
            textView.setText(String.valueOf(dataA.index));
        }
    }
}
