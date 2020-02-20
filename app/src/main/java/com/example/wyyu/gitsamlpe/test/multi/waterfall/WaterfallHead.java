package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2020-02-20.
 **/

public class WaterfallHead extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_waterfall_head;

    WaterfallHead(@NonNull View itemView) {
        super(itemView);

        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        StaggeredGridLayoutManager.LayoutParams layoutParams =
            new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                params.height);
        layoutParams.setFullSpan(true);
        itemView.setLayoutParams(layoutParams);
    }

    void cacheItem() {

    }
}
