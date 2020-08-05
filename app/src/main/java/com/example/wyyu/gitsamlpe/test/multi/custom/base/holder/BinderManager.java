package com.example.wyyu.gitsamlpe.test.multi.custom.base.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.Arrays;

/**
 * Created by wyyu on 2019-09-24.
 **/

public class BinderManager implements IBinderManager<Class<?>> {

    private HolderBinder[] binderArray;
    private Class<?>[] classArray;

    public BinderManager() {
        this(6);
    }

    private BinderManager(int initialCapacity) {
        binderArray = new HolderBinder[initialCapacity];
        classArray = new Class<?>[initialCapacity];
    }

    @Override
    public void register(@NonNull Class<?> dataClass, @NonNull HolderBinder holderBinder) {
        int index = loadNewIndex();

        if (index < 0) {
            this.binderArray = Arrays.copyOf(this.binderArray, this.binderArray.length + 6);
            this.classArray = Arrays.copyOf(this.classArray, this.classArray.length + 6);

            index = loadNewIndex();
        }

        binderArray[index] = holderBinder;
        classArray[index] = dataClass;
    }

    // 获取新添加的 Class 在数组中可放置的位置
    private int loadNewIndex() {
        int indexPre = -1;
        int funIndex = 0;

        while (funIndex < classArray.length && classArray[funIndex] != null) {
            funIndex++;
        }
        if (funIndex < classArray.length) {
            indexPre = funIndex;
        }

        return indexPre;
    }

    @Override public int getItemViewType(@NonNull Object item) {
        if (classArray == null || classArray.length == 0) {
            throw new IllegalArgumentException(
                "The Item = " + item.getClass().getName() + " Not Register To Adapter");
        }
        for (int index = 0; index < classArray.length; index++) {
            if (item.getClass().equals(classArray[index])) {
                return index;
            }
        }
        throw new IllegalArgumentException(
            "The Item = " + item.getClass().getName() + " Not Register To Adapter");
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return binderArray[viewType].onCreateViewHolder(parent);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, Object item) {
        binderArray[holder.getItemViewType()].onBindViewHolder(holder, item);
    }

    @Override
    public void updateItem(@NonNull RecyclerView.ViewHolder holder, Object item, int updateType) {
        binderArray[holder.getItemViewType()].updateItem(holder, item, updateType);
    }
}
