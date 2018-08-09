package com.example.wyyu.gitsamlpe.test.number.value;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2018/8/8.
 **/

@Retention(RetentionPolicy.SOURCE) @IntDef public @interface Symbol {
    int 加 = 101;
    int 减 = 102;
    int 乘 = 103;
    int 除 = 104;
    int 取余 = 105;
    int 累加 = 106;
    int 开方 = 107;
    int 阶乘 = 108;
}
