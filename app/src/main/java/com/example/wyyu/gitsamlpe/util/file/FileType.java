package com.example.wyyu.gitsamlpe.util.file;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2018/3/20.
 **/

@IntDef({ FileType.IMAGE }) @Retention(RetentionPolicy.SOURCE) public @interface FileType {

    int IMAGE = 0;

    int VIDEO = 1;

    int AUDIO = 2;
}
