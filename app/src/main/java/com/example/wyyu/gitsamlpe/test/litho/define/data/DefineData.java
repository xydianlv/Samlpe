package com.example.wyyu.gitsamlpe.test.litho.define.data;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class DefineData {

    public DefineImage imageBean;
    public String title;
    public String info;
    public String content;
    public int iconId;
    public int countLike;
    public int countReview;
    public int countShare;
    public int index;

    public DefineData() {
        this.title = "";
        this.content = "";
        this.iconId = 0;
        this.imageBean = null;

        this.info = String.valueOf(System.currentTimeMillis());
        this.countLike = 315;
        this.countReview = 47;
        this.countShare = 85;
        this.index = -1;
    }
}
