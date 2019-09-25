package com.example.wyyu.gitsamlpe.test.multi.define.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.Arrays;

/**
 * Created by wyyu on 2019-09-24.
 *
 * 一个处理 ItemViewType 的类，用数组的 Index 来构建唯一的 ViewType
 **/

public class MultiTypeManager implements Cloneable {

    private HolderBinder[] binderArray;
    private Class<?>[] classArray;

    MultiTypeManager() {
        this(6);
    }

    private MultiTypeManager(int initialCapacity) {
        binderArray = new HolderBinder[initialCapacity];
        classArray = new Class<?>[initialCapacity];
    }

    // 注册一组 Item 类型到 Manager
    void register(@NonNull Class<?> dataClass, @NonNull HolderBinder holderBinder) {
        int index = loadNewIndex();

        if (index < 0) {
            expand();
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

    // 数组扩容
    private void expand() {
        this.binderArray = Arrays.copyOf(this.binderArray, this.binderArray.length + 6);
        this.classArray = Arrays.copyOf(this.classArray, this.classArray.length + 6);
    }

    // 根据数据对象获取 Adapter 中的 ViewType
    public int getItemViewType(@NonNull Object item) {
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

    // 创建一个 ViewHolder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return binderArray[viewType].onCreateViewHolder(parent);
    }

    // 绑定数据到 ViewHolder
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, Object item) {
        binderArray[holder.getItemViewType()].onBindViewHolder(holder, item);
    }

    // 更新 Item 样式
    public void updateItem(@NonNull RecyclerView.ViewHolder holder, Object item, int updateType) {
        binderArray[holder.getItemViewType()].updateItem(holder, item, updateType);
    }
}
