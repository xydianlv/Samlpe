package com.example.wyyu.gitsamlpe.test.audio;

import android.os.Environment;
import java.io.File;

/**
 * Created by wyyu on 2018/1/9.
 * 本地文件操作类
 **/

public class UtilFile {

    private static final String CACHE_NAME = "/audio/cache/cache.pcm";

    static File getCacheFile() {

        return new File(Environment.getExternalStorageDirectory().getPath() + CACHE_NAME);
    }
}
