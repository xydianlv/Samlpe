package com.example.wyyu.gitsamlpe.test.multi.multitype.binder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.multitype.base.ItemViewBinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeA;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ItemTypeABinder extends ItemViewBinder<ItemTypeA, ItemTypeABinder.HolderItemA> {

    @NonNull @Override protected HolderItemA onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        return new HolderItemA(inflater.inflate(HolderItemA.LAYOUT, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull HolderItemA holder, @NonNull ItemTypeA item) {
        holder.cacheValue(item);
    }

    static class HolderItemA extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_multi_item_type_a;

        private TextView itemText;

        HolderItemA(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.multi_item_text);
        }

        void cacheValue(ItemTypeA itemTypeA) {
            itemText.setText(String.valueOf(itemTypeA.index));
        }
    }
}
