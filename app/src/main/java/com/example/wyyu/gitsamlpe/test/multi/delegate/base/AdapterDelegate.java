package com.example.wyyu.gitsamlpe.test.multi.delegate.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public abstract class AdapterDelegate<T> {


    protected abstract boolean isForViewType(@NonNull T items, int position);

    @NonNull
    abstract protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull T items, int position,
        @NonNull RecyclerView.ViewHolder holder, @NonNull
        List<Object> payloads);

    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
    }

    protected boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return false;
    }

    protected void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
    }

    protected void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    }
}
