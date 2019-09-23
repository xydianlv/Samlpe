package com.example.wyyu.gitsamlpe.test.multi.multitype.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.multitype.base.ItemViewBinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeB;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ItemTypeBBinder extends ItemViewBinder<ItemTypeB, ItemTypeBBinder.HolderItemB> {

    @NonNull @Override
    protected ItemTypeBBinder.HolderItemB onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        return new ItemTypeBBinder.HolderItemB(
            inflater.inflate(ItemTypeBBinder.HolderItemB.LAYOUT, parent, false));
    }

    @Override protected void onBindViewHolder(@NonNull ItemTypeBBinder.HolderItemB holder,
        @NonNull ItemTypeB item) {
        holder.cacheValue(item);
    }

    static class HolderItemB extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_multi_item_type_b;

        private TextView itemText;

        HolderItemB(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.multi_item_text);
        }

        void cacheValue(ItemTypeB itemTypeB) {
            itemText.setText(String.valueOf(itemTypeB.index));
        }
    }
}