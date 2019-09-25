package com.example.wyyu.gitsamlpe.test.multi.define.base;

import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * Created by wyyu on 2019-09-24.
 **/

public interface IMultiItemAdapter {

    void register(Class<?> dataClass, HolderBinder holderBinder);

    void bindRecyclerView(RecyclerView recyclerView);

    void setItemList(List<?> itemList);

    void updateItem(Object item, int updateType);
}
