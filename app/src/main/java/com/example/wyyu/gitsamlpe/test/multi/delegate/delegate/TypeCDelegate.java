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
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTypeC;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class TypeCDelegate extends AdapterDelegate<List<ItemTest>> {

    @Override protected boolean isForViewType(@NonNull List<ItemTest> items, int position) {
        return items.get(position) instanceof ItemTypeC;
    }

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderTypeC(
            LayoutInflater.from(parent.getContext()).inflate(HolderTypeC.LAYOUT, parent, false));
    }

    @Override protected void onBindViewHolder(@NonNull List<ItemTest> items, int position,
        @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((HolderTypeC) holder).cacheValue((ItemTypeC) items.get(position));
    }

    private static class HolderTypeC extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_delegate_holder_type_c;

        private TextView itemText;

        HolderTypeC(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.delegate_item_text);
        }

        void cacheValue(ItemTypeC itemTypeC) {
            itemText.setText(String.valueOf(itemTypeC.index));
        }
    }
}
