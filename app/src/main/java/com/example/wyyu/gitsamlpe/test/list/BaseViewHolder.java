package com.example.wyyu.gitsamlpe.test.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2019/3/1.
 **/

class BaseViewHolder extends RecyclerView.ViewHolder {

    static final int BASE_VIEW_LAYOUT = R.layout.layout_base_view_holder;

    @BindView(R.id.base_holder_view) BaseHolderView holderView;

    BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bindItemValue(MultiData multiData) {
        if (multiData == null || holderView == null) {
            return;
        }
        holderView.setMultiData(multiData);
    }
}
