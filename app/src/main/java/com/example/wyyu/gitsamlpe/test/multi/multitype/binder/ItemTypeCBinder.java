package com.example.wyyu.gitsamlpe.test.multi.multitype.binder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.multitype.base.ItemViewBinder;
import com.example.wyyu.gitsamlpe.test.multi.multitype.data.ItemTypeC;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ItemTypeCBinder extends ItemViewBinder<ItemTypeC, ItemTypeCBinder.HolderItemC> {

    @NonNull @Override
    protected ItemTypeCBinder.HolderItemC onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        return new ItemTypeCBinder.HolderItemC(
            inflater.inflate(ItemTypeCBinder.HolderItemC.LAYOUT, parent, false));
    }

    @Override protected void onBindViewHolder(@NonNull ItemTypeCBinder.HolderItemC holder,
        @NonNull ItemTypeC item) {
        holder.cacheValue(item);
    }

    static class HolderItemC extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_multi_item_type_c;

        private TextView itemText;

        HolderItemC(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.multi_item_text);
        }

        void cacheValue(ItemTypeC itemTypeC) {
            itemText.setText(String.valueOf(itemTypeC.index));
        }
    }
}