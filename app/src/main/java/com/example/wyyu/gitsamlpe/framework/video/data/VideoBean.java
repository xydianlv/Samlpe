package com.example.wyyu.gitsamlpe.framework.video.data;

import java.util.LinkedHashMap;

/**
 * Created by wyyu on 2019-12-26.
 **/

public class VideoBean {

    public long id;

    public int width;

    public int height;

    // 单位秒
    public int duration;

    public int playCount;

    public String localPath;

    public LinkedHashMap<String, String> composeUrlMap() {
        LinkedHashMap<String, String> pathMap = new LinkedHashMap<>();
        pathMap.put("default", localPath);
        return pathMap;
    }
}
