package com.wyyu.multi.adapter;

import androidx.annotation.NonNull;
import com.wyyu.multi.binder.HolderBinder;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 *
 * 约定一个 MultiAdapter 的基本功能
 **/

public interface IMultiAdapter<T, V> {

    /**
     * 注册一种 Holder 类型到 Adapter
     *
     * @param keyValue 可唯一标识一种 Holder 类型的 数据对象
     * @param holderBinder 构造该 Holder 的 HolderBinder
     */
    void register(@NonNull T keyValue, @NonNull HolderBinder holderBinder);

    /**
     * 初始化列表
     *
     * @param itemList 初始化列表数据
     * @param needClear 是否需要清空列表，true —— 清空列表重新填充，false —— 将这一组数据插入到列表头
     */
    void initItemList(List<V> itemList, boolean needClear);

    /**
     * 添加一组数据到列表尾部
     *
     * @param itemList 待添加数据
     */
    void appendItemList(List<V> itemList);

    /**
     * 插入一个数据到指定位置
     *
     * @param item 待插入数据
     * @param position 指定位置
     */
    void insertItem(V item, int position);

    /**
     * 移除列表中指定数据
     *
     * @param item 待移除数据
     */
    void removeItem(V item);
}
