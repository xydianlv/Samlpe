package com.example.wyyu.gitsamlpe.test.multi.multitype.base;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * Created by wyyu on 2019-09-23.
 **/

public interface Linker<T> {

    @IntRange(from = 0) int index(int position, @NonNull T t);
}
