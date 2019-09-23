package com.example.wyyu.gitsamlpe.test.multi.delegate.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class AdapterDelegatesManager<T> {

    private static final int FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1;

    private static final List<Object> PAYLOADS_EMPTY_LIST = Collections.emptyList();

    private SparseArrayCompat<AdapterDelegate<T>> delegates;
    private AdapterDelegate<T> fallbackDelegate;

    public AdapterDelegatesManager() {
        delegates = new SparseArrayCompat<>();
    }

    public AdapterDelegatesManager(@NonNull AdapterDelegate<T>... delegates) {
        this.delegates = new SparseArrayCompat<>();
        for (AdapterDelegate<T> delegate : delegates) {
            addDelegate(delegate);
        }
    }

    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes
        int viewType = delegates.size();
        while (delegates.get(viewType) != null) {
            viewType++;
            if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw new IllegalArgumentException(
                    "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.");
            }
        }
        return addDelegate(viewType, false, delegate);
    }

    public AdapterDelegatesManager<T> addDelegate(int viewType,
        @NonNull AdapterDelegate<T> delegate) {
        return addDelegate(viewType, false, delegate);
    }

    public AdapterDelegatesManager<T> addDelegate(int viewType, boolean allowReplacingDelegate,
        AdapterDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
            throw new IllegalArgumentException("The view type = "
                + FALLBACK_DELEGATE_VIEW_TYPE
                + " is reserved for fallback adapter delegate (see setFallbackDelegate() ). Please use another view type.");
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                "An AdapterDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered AdapterDelegate is "
                    + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    public AdapterDelegatesManager<T> removeDelegate(@NonNull AdapterDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public AdapterDelegatesManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    public int getItemViewType(T items, int position) {

        if (items == null) {
            throw new NullPointerException("Items datasource is null!");
        }

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(items, position)) {
                return delegates.keyAt(i);
            }
        }

        if (fallbackDelegate != null) {
            return FALLBACK_DELEGATE_VIEW_TYPE;
        }

        throw new NullPointerException(
            "No AdapterDelegate added that matches position=" + position + " in data source");
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterDelegate<T> delegate = getDelegateForViewType(viewType);
        if (delegate == null) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }

        return delegate.onCreateViewHolder(parent);
    }

    public void onBindViewHolder(@NonNull T items, int position,
        @NonNull RecyclerView.ViewHolder holder, List payloads) {

        AdapterDelegate<T> delegate = getDelegateForViewType(holder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException(
                "No delegate found for item at position = " + position + " for viewType = " + holder
                    .getItemViewType());
        }
        delegate.onBindViewHolder(items, position, holder,
            payloads != null ? payloads : PAYLOADS_EMPTY_LIST);
    }

    public void onBindViewHolder(@NonNull T items, int position,
        @NonNull RecyclerView.ViewHolder holder) {
        onBindViewHolder(items, position, holder, PAYLOADS_EMPTY_LIST);
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(holder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException("No delegate found for "
                + holder
                + " for item at position = "
                + holder.getAdapterPosition()
                + " for viewType = "
                + holder.getItemViewType());
        }
        delegate.onViewRecycled(holder);
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(holder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException("No delegate found for "
                + holder
                + " for item at position = "
                + holder.getAdapterPosition()
                + " for viewType = "
                + holder.getItemViewType());
        }
        return delegate.onFailedToRecycleView(holder);
    }

    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(holder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException("No delegate found for "
                + holder
                + " for item at position = "
                + holder.getAdapterPosition()
                + " for viewType = "
                + holder.getItemViewType());
        }
        delegate.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        AdapterDelegate<T> delegate = getDelegateForViewType(holder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException("No delegate found for "
                + holder
                + " for item at position = "
                + holder.getAdapterPosition()
                + " for viewType = "
                + holder.getItemViewType());
        }
        delegate.onViewDetachedFromWindow(holder);
    }

    public AdapterDelegatesManager<T> setFallbackDelegate(
        @Nullable AdapterDelegate<T> fallbackDelegate) {
        this.fallbackDelegate = fallbackDelegate;
        return this;
    }

    public int getViewType(AdapterDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("Delegate is null");
        }

        int index = delegates.indexOfValue(delegate);
        if (index == -1) {
            return -1;
        }
        return delegates.keyAt(index);
    }

    @Nullable public AdapterDelegate<T> getDelegateForViewType(int viewType) {
        return delegates.get(viewType, fallbackDelegate);
    }

    @Nullable public AdapterDelegate<T> getFallbackDelegate() {
        return fallbackDelegate;
    }
}
