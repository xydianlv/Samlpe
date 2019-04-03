package com.example.wyyu.gitsamlpe.test.video.player;

import android.net.Uri;
import com.example.matisse.internal.entity.Item;

/**
 * Created by wyyu on 2019/4/3.
 **/
public class VideoInfo {

    public long id;

    public String localPath;

    public int height;

    public int width;

    public long duration;

    public long size;

    public Uri uri;

    public VideoInfo(Item item) {
        if (item == null) {
            return;
        }
        id = item.id;
        localPath = item.path;
        height = item.height;
        width = item.width;
        duration = item.duration;
        size = item.size;
        uri = item.uri;
    }

    VideoInfo(VideoInfo videoInfo) {
        if (videoInfo == null) {
            return;
        }
        id = videoInfo.id;
        localPath = videoInfo.localPath;
        height = videoInfo.height;
        width = videoInfo.width;
        duration = videoInfo.duration;
        size = videoInfo.size;
        uri = videoInfo.uri;
    }
}
