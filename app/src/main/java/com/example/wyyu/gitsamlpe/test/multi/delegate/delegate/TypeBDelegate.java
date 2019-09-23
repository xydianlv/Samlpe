package com.example.wyyu.gitsamlpe.test.multi.delegate.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.delegate.base.AdapterDelegate;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTest;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeB;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/
public class TypeBDelegate extends AdapterDelegate<List<ItemTest>> {

    @Override protected boolean isForViewType(@NonNull List<ItemTest> items, int position) {
        return items.get(position) instanceof ItemTypeB;
    }

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderTypeB(
            LayoutInflater.from(parent.getContext()).inflate(HolderTypeB.LAYOUT, parent, false));
    }

    @Override protected void onBindViewHolder(@NonNull List<ItemTest> items, int position,
        @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((HolderTypeB) holder).cacheValue((ItemTypeB) items.get(position));
    }

    private static class HolderTypeB extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_delegate_holder_type_b;

        private TextView itemText;

        HolderTypeB(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.delegate_item_text);
        }

        void cacheValue(ItemTypeB itemTypeB) {
            itemText.setText(String.valueOf(itemTypeB.index));
        }
    }
}