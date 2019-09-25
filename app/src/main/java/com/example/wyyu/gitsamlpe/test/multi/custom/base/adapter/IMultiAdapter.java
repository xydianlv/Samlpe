package com.example.wyyu.gitsamlpe.test.multi.custom.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.example.wyyu.gitsamlpe.test.multi.custom.base.holder.HolderBinder;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public interface IMultiAdapter {

    /**
     * 注册一种 Holder 类型到 Adapter
     *
     * @param dataClass 该 Holder 对应的数据结构
     * @param holderBinder 构造该 Holder 的 HolderBinder
     */
    void register(@NonNull Class<?> dataClass, @NonNull HolderBinder holderBinder);

    /**
     * 初始化列表
     *
     * @param itemList 初始化列表数据
     * @param needClear 是否需要清空列表，true —— 清空列表重新填充，false —— 将这一组数据插入到列表头
     */
    void initItemList(List<?> itemList, boolean needClear);

    /**
     * 添加一组数据到列表尾部
     *
     * @param itemList 待添加数据
     */
    void appendItemList(List<?> itemList);

    /**
     * 插入一个数据到指定位置
     *
     * @param item 待插入数据
     * @param position 指定位置
     */
    void insertItem(Object item, int position);

    /**
     * 移除列表中指定数据
     *
     * @param item 待移除数据
     */
    void deleteItem(Object item);

    /**
     * 根据新变更的数据局部刷新 ItemView
     *
     * @param recyclerView 用来获取 ViewHolder
     * @param item 变更后的数据
     * @param updateType 刷新类型，在 HolderBinder 中定制局部刷新方式
     */
    void updateItem(@NonNull RecyclerView recyclerView, Object item, int updateType);

    /**
     * 更新整个 Item
     *
     * @param item 待更新的 Item
     */
    void notifyItem(Object item);
}
