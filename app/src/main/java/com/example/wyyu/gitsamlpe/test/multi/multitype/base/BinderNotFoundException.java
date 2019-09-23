package com.example.wyyu.gitsamlpe.test.multi.multitype.base;

import android.support.annotation.NonNull;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class BinderNotFoundException extends RuntimeException {

    BinderNotFoundException(@NonNull Class<?> clazz) {
        super("Have you registered {className}.class to the binder in the adapter/pool?"
            .replace("{className}", clazz.getSimpleName()));
    }
}