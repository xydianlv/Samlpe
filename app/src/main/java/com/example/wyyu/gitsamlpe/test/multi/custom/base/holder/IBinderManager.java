package com.example.wyyu.gitsamlpe.test.multi.custom.base.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by wyyu on 2019-09-24.
 **/

public interface IBinderManager<T> {

    /**
     * 注册一种 Holder 类型到 Adapter
     *
     * @param keyValue 用来做 Key 的数据结构
     * @param holderBinder 构造该 Holder 的 HolderBinder
     */
    void register(@NonNull T keyValue, @NonNull HolderBinder holderBinder);

    /**
     * 获取当前数据对应的 ViewType
     *
     * @param item 数据结构
     * @return ViewType
     */
    int getItemViewType(@NonNull Object item);

    /**
     * 根据 ViewType 创建相应的 ViewHolder
     *
     * @param parent 父布局
     * @param viewType Holder 类型
     * @return ViewHolder
     */
    @NonNull RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    /**
     * 绑定数据到 ViewHolder
     *
     * @param holder ViewHolder
     * @param item 数据
     */
    void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, Object item);

    /**
     * 根据新变更的数据局部刷新 ItemView
     *
     * @param holder 持有该数据的 ViewHolder
     * @param item 变更后的数据
     * @param updateType 刷新类型，在 HolderBinder 中定制局部刷新方式
     */
    void updateItem(@NonNull RecyclerView.ViewHolder holder, Object item, int updateType);
}
