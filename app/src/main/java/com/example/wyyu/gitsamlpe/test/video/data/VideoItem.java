package com.example.wyyu.gitsamlpe.test.video.data;

import android.net.Uri;
import io.reactivex.annotations.Nullable;
import java.io.File;

/**
 * Created by wyyu on 2020-07-27.
 *
 * 视频数据结构
 **/

public class VideoItem {

    // 视频唯一标识
    public long id;

    // 视频名称
    public String name;

    // 视频标题
    public String title;

    // 播放次数
    public int playCount;

    // 视频时长
    public long duration;

    // 视频大小
    public long size;

    // 视频源数据
    public String sourceValue;

    // 视频源类型
    public @SourceType int sourceType;

    // 视频封面宽度
    public int width;

    // 视频封面高度
    public int height;

    // 视频封面
    public String cover;

    // 视频源的 Uri
    public @Nullable Uri uri() {
        switch (sourceType) {
            case SourceType.PATH:
                return Uri.fromFile(new File(sourceValue));
            case SourceType.URL:
                return Uri.parse(sourceValue);
            default:
                return null;
        }
    }
}
