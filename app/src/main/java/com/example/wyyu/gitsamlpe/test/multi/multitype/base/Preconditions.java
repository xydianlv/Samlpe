package com.example.wyyu.gitsamlpe.test.multi.multitype.base;

import android.support.annotation.NonNull;

/**
 * Created by wyyu on 2019-09-23.
 **/

public final class Preconditions {

    public static @NonNull <T> T checkNotNull(final T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    private Preconditions() {
    }
}
