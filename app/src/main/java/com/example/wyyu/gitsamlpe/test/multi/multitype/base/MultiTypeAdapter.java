package com.example.wyyu.gitsamlpe.test.multi.multitype.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/
public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MultiTypeAdapter";

    private @NonNull List<?> items;
    private @NonNull TypePool typePool;

    public MultiTypeAdapter() {
        this(Collections.emptyList());
    }

    public MultiTypeAdapter(@NonNull List<?> items) {
        this(items, new MultiTypePool());
    }

    public MultiTypeAdapter(@NonNull List<?> items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity));
    }

    public MultiTypeAdapter(@NonNull List<?> items, @NonNull TypePool pool) {
        Preconditions.checkNotNull(items);
        Preconditions.checkNotNull(pool);
        this.items = items;
        this.typePool = pool;
    }

    public <T> void register(@NonNull Class<? extends T> clazz,
        @NonNull ItemViewBinder<T, ?> binder) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(binder);
        checkAndRemoveAllTypesIfNeeded(clazz);
        register(clazz, binder, new DefaultLinker<T>());
    }

    private <T> void register(@NonNull Class<? extends T> clazz,
        @NonNull ItemViewBinder<T, ?> binder, @NonNull Linker<T> linker) {
        typePool.register(clazz, binder, linker);
        binder.adapter = this;
    }

    private void checkAndRemoveAllTypesIfNeeded(@NonNull Class<?> clazz) {
        if (typePool.unregister(clazz)) {
            Log.w(TAG, "You have registered the "
                + clazz.getSimpleName()
                + " type. "
                + "It will override the original binder(s).");
        }
    }

    public void setItems(@NonNull List<?> items) {
        Preconditions.checkNotNull(items);
        this.items = items;
    }

    public @NonNull List<?> getItems() {
        return items;
    }

    @Override public final int getItemViewType(int position) {
        return typePool.firstIndexOf(items.get(position).getClass());
        //Object item = items.get(position);
        //return indexInTypesOf(position, item);
    }

    private int indexInTypesOf(int position, @NonNull Object item) throws BinderNotFoundException {
        int index = typePool.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked") Linker<Object> linker =
                (Linker<Object>) typePool.getLinker(index);
            return index + linker.index(position, item);
        }
        throw new BinderNotFoundException(item.getClass());
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
        int indexViewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemViewBinder<?, ?> binder = typePool.getItemViewBinder(indexViewType);
        return binder.onCreateViewHolder(inflater, viewGroup);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder(viewHolder, i, Collections.emptyList());
    }

    @Override @SuppressWarnings("unchecked")
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        Object item = items.get(position);
        ItemViewBinder binder = typePool.getItemViewBinder(holder.getItemViewType());
        binder.onBindViewHolder(holder, item, payloads);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override @SuppressWarnings("unchecked") public final long getItemId(int position) {
        Object item = items.get(position);
        int itemViewType = getItemViewType(position);
        ItemViewBinder binder = typePool.getItemViewBinder(itemViewType);
        return binder.getItemId(item);
    }

    @Override @SuppressWarnings("unchecked")
    public final void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewRecycled(holder);
    }

    @Override @SuppressWarnings("unchecked")
    public final boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return getRawBinderByViewHolder(holder).onFailedToRecycleView(holder);
    }

    @Override @SuppressWarnings("unchecked")
    public final void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewAttachedToWindow(holder);
    }

    @Override @SuppressWarnings("unchecked")
    public final void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewDetachedFromWindow(holder);
    }

    private @NonNull ItemViewBinder getRawBinderByViewHolder(
        @NonNull RecyclerView.ViewHolder holder) {
        return typePool.getItemViewBinder(holder.getItemViewType());
    }
}
