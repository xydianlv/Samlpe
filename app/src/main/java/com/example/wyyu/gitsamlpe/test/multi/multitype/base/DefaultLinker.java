package com.example.wyyu.gitsamlpe.test.multi.multitype.base;

import androidx.annotation.NonNull;

/**
 * Created by wyyu on 2019-09-23.
 **/

class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}