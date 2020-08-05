package com.example.wyyu.gitsamlpe.util.permission;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2018/8/25.
 **/

@Retention(RetentionPolicy.SOURCE) @IntDef public @interface PermissionItemKey {
    int 拍照 = 1001;
    int 日历 = 1002;
    int 存储卡 = 1003;
}
