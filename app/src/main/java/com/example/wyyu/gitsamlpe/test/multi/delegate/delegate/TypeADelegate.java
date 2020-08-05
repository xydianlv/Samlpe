package com.example.wyyu.gitsamlpe.test.multi.delegate.delegate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.delegate.base.AdapterDelegate;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTest;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeA;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class TypeADelegate extends AdapterDelegate<List<ItemTest>> {

    @Override protected boolean isForViewType(@NonNull List<ItemTest> items, int position) {
        return items.get(position) instanceof ItemTypeA;
    }

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderTypeA(
            LayoutInflater.from(parent.getContext()).inflate(HolderTypeA.LAYOUT, parent, false));
    }

    @Override protected void onBindViewHolder(@NonNull List<ItemTest> items, int position,
        @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((HolderTypeA) holder).cacheValue((ItemTypeA) items.get(position));
    }

    private static class HolderTypeA extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_delegate_holder_type_a;

        private TextView itemText;

        HolderTypeA(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.delegate_item_text);
        }

        void cacheValue(ItemTypeA itemTypeA) {
            itemText.setText(String.valueOf(itemTypeA.index));
        }
    }
}
