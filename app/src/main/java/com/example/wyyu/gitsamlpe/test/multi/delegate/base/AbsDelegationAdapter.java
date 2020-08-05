package com.example.wyyu.gitsamlpe.test.multi.delegate.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public abstract class AbsDelegationAdapter<T> extends RecyclerView.Adapter {

    protected AdapterDelegatesManager<T> delegatesManager;
    protected T items;

    public AbsDelegationAdapter() {
        this(new AdapterDelegatesManager<T>());
    }

    public AbsDelegationAdapter(@NonNull AdapterDelegatesManager<T> delegatesManager) {
        this.delegatesManager = delegatesManager;
    }

    public AbsDelegationAdapter(@NonNull AdapterDelegate<T>... delegates) {
        this(new AdapterDelegatesManager<T>(delegates));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder, null);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull
        List payloads) {
        delegatesManager.onBindViewHolder(items, position, holder, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        delegatesManager.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return delegatesManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        delegatesManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        delegatesManager.onViewDetachedFromWindow(holder);
    }

    /**
     * Get the items / data source of this adapter
     *
     * @return The items / data source
     */
    public T getItems() {
        return items;
    }

    /**
     * Set the items / data source of this adapter
     *
     * @param items The items / data source
     */
    public void setItems(T items) {
        this.items = items;
    }
}
