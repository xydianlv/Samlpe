package com.wyyu.multi.binder;

import android.support.annotation.NonNull;

/**
 * Created by wyyu on 2019-09-24.
 *
 * 使用数据结构的 Class 来做 唯一标识Key 的 BinderManager
 **/

public class DefaultBinderManager extends AbsBinderManager<Class<?>, Object> {

    @Override public Class<?>[] loadKeyArray() {
        return new Class[AbsBinderManager.DEFAULT_LENGTH];
    }

    @Override public Class<?> loadKeyFromItem(@NonNull Object item) {
        return item.getClass();
    }
}
