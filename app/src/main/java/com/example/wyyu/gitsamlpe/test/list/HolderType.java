package com.example.wyyu.gitsamlpe.test.list;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019/3/1.
 **/

@Retention(RetentionPolicy.SOURCE) @IntDef({
    HolderType.ZERO, HolderType.ONE, HolderType.TWO
}) public @interface HolderType {

    // 内容靠左边显示
    int ZERO = 0;

    // 内容靠中间显示
    int ONE = 1;

    // 内容靠右边显示
    int TWO = 2;
}
