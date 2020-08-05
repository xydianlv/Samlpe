package com.wyyu.multi.binder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.Arrays;

/**
 * Created by wyyu on 2019-09-24.
 **/

public abstract class AbsBinderManager<T, V> implements IBinderManager<T, V> {

    static final int DEFAULT_LENGTH = 6;

    private HolderBinder[] binderArray;
    private T[] keyArray;

    AbsBinderManager() {
        binderArray = new HolderBinder[DEFAULT_LENGTH];
        keyArray = loadKeyArray();
    }

    @Override public void register(@NonNull T keyValue, @NonNull HolderBinder holderBinder) {
        int index = loadNewIndex();

        if (index < 0) {
            this.binderArray =
                Arrays.copyOf(this.binderArray, this.binderArray.length + DEFAULT_LENGTH);
            this.keyArray = Arrays.copyOf(this.keyArray, this.keyArray.length + DEFAULT_LENGTH);

            index = loadNewIndex();
        }

        binderArray[index] = holderBinder;
        keyArray[index] = keyValue;
    }

    private int loadNewIndex() {
        int indexPre = -1;
        int funIndex = 0;

        while (funIndex < keyArray.length && keyArray[funIndex] != null) {
            funIndex++;
        }
        if (funIndex < keyArray.length) {
            indexPre = funIndex;
        }

        return indexPre;
    }

    @Override public int getItemViewType(@NonNull V item) {
        if (keyArray == null || keyArray.length == 0) {
            throw new IllegalArgumentException(
                "The Item = " + item.getClass().getName() + " Not Register To Adapter");
        }
        for (int index = 0; index < keyArray.length; index++) {
            if (loadKeyFromItem(item).equals(keyArray[index])) {
                return index;
            }
        }
        throw new IllegalArgumentException(
            "The Item = " + item.getClass().getName() + " Not Register To Adapter");
    }

    @Override @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return binderArray[viewType].onCreateViewHolder(parent);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, V item) {
        binderArray[holder.getItemViewType()].onBindViewHolder(holder, item);
    }

    @Override
    public void updateItem(@NonNull RecyclerView.ViewHolder holder, V item, int updateType) {
        binderArray[holder.getItemViewType()].updateItem(holder, item, updateType);
    }
}
