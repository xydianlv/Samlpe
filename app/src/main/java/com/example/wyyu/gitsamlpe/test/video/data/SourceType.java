package com.example.wyyu.gitsamlpe.test.video.data;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2020-07-28.
 **/

@IntDef({ SourceType.URL, SourceType.PATH }) @Retention(RetentionPolicy.SOURCE)
public @interface SourceType {

    // 服务器URL
    int URL = 0;

    // 本地文件路径
    int PATH = 1;
}
