package com.example.wyyu.gitsamlpe.test.multi.define.binder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.multi.define.base.HolderBinder;
import com.example.wyyu.gitsamlpe.test.multi.define.data.DefineDataA;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class DefineBinderA extends HolderBinder {

    @NonNull @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HolderDefineA(onCreateView(parent, HolderDefineA.LAYOUT));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {
        ((HolderDefineA) holder).cacheValue((DefineDataA) item);
    }

    @Override
    protected void updateItem(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull Object item,
        int updateType) {

    }

    private static class HolderDefineA extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.layout_holder_define_a;

        HolderDefineA(@NonNull View itemView) {
            super(itemView);
        }

        void cacheValue(DefineDataA dataA) {
            ULog.show(String.valueOf(dataA.index));
        }
    }
}
