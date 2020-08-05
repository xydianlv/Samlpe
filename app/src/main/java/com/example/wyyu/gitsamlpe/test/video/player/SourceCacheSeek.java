package com.example.wyyu.gitsamlpe.test.video.player;

import java.util.Arrays;

/**
 * Created by wyyu on 2020-07-27.
 **/

class SourceCacheSeek {

    private static final int MAX_COUNT = 10;

    private long[] seekArray;
    private long[] idArray;

    SourceCacheSeek() {
        initArray();
    }

    private void initArray() {
        seekArray = new long[MAX_COUNT];
        idArray = new long[MAX_COUNT];

        Arrays.fill(seekArray, 0);
        Arrays.fill(idArray, -1);
    }

    void cacheSeek(long sourceId, long sourceSeek) {
        if (seekArray == null || idArray == null) {
            initArray();
        }
        // 已经缓存过的，更新缓存的位置
        for (int index = 0; index < MAX_COUNT; index++) {
            if (idArray[index] == sourceId) {
                seekArray[index] = sourceSeek;
                return;
            }
        }
        // 从未缓存的，插入到列表第一位
        for (int index = MAX_COUNT - 1; index > 0; index--) {
            if (idArray[index - 1] == -1) {
                continue;
            }
            idArray[index] = idArray[index - 1];
            seekArray[index] = seekArray[index - 1];
        }
        idArray[0] = sourceId;
        seekArray[0] = sourceSeek;
    }

    long lastSeek(long sourceId) {
        if (idArray == null || seekArray == null) {
            return 0;
        }
        for (int index = 0; index < MAX_COUNT; index++) {
            if (idArray[index] == sourceId) {
                return seekArray[index];
            }
        }
        return 0;
    }
}
