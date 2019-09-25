package com.example.wyyu.gitsamlpe.test.multi.define.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.multi.define.base.HolderBinder;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataB;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class DefineBinderB extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new DefineBinderB.HolderDefineB(
            onCreateView(parent, DefineBinderB.HolderDefineB.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((DefineBinderB.HolderDefineB) holder).cacheValue((DefineDataB) item);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderDefineB extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_define_b;

        HolderDefineB(@NonNull View itemView) {
            super(itemView);
        }

        void cacheValue(DefineDataB dataB) {
            ULog.show(String.valueOf(dataB.index));
        }
    }
}