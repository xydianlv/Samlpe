package com.example.wyyu.gitsamlpe.framework.window;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyyu on 2019/3/11.
 **/

@IntDef({ ShowType.左上角, ShowType.左下角, ShowType.右上角, ShowType.右下角 })
@Retention(RetentionPolicy.SOURCE) public @interface ShowType {
    int 左上角 = 0;
    int 左下角 = 1;
    int 右上角 = 2;
    int 右下角 = 3;
}
