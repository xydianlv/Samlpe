package com.example.wyyu.gitsamlpe.test.multi.define.binder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.multi.define.base.HolderBinder;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataC;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class DefineBinderC extends HolderBinder {

    public static final int UPDATE_TYPE_A = 0;
    public static final int UPDATE_TYPE_B = 1;

    private static boolean testValue = false;

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new DefineBinderC.HolderDefineC(
            onCreateView(parent, DefineBinderC.HolderDefineC.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((DefineBinderC.HolderDefineC) holder).cacheValue((DefineDataC) item);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {
        HolderDefineC holder = (HolderDefineC) viewHolder;
        switch (updateType) {
            case UPDATE_TYPE_A:
                holder.itemText.setText(String.valueOf(((DefineDataC) item).index));
                break;
            case UPDATE_TYPE_B:
                holder.itemText.setTextColor(testValue ? 0xffff0000 : 0xff00ff00);
                testValue = !testValue;
                break;
        }
    }

    private static class HolderDefineC extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_define_c;

        private TextView itemText;

        HolderDefineC(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.define_item_text);
        }

        void cacheValue(DefineDataC dataC) {
            itemText.setText(String.valueOf(dataC.index));
        }
    }
}