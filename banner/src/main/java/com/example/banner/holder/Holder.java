package com.example.banner.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wyyu on 2019-07-01.
 **/
public abstract class Holder<T> extends RecyclerView.ViewHolder {
    public Holder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    protected abstract void initView(View itemView);

    public abstract void updateUI(T data);
}
