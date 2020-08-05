package com.example.wyyu.gitsamlpe.test.video.player;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.io.IOException;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class SourceChangeLoadErrorPolicy extends DefaultLoadErrorHandlingPolicy {

    @Override
    public long getRetryDelayMsFor(int dataType, long loadDurationMs, IOException exception,
        int errorCount) {
        // 如果数据源未连接发生失败，优先换源
        if(loadDurationMs == C.TIME_UNSET)
            return C.TIME_UNSET;

        // 如果数据源连接后发生失败，走默认的重试策略
        return super.getRetryDelayMsFor(dataType, loadDurationMs, exception, errorCount);
    }
}
