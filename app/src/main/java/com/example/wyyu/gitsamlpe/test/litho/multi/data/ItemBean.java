package com.example.wyyu.gitsamlpe.test.litho.multi.data;

/**
 * Created by wyyu on 2019-09-27.
 **/

public class ItemBean {

    public ImageBean imageBean;
    public String title;
    public String info;
    public String content;
    public int index;
    public int iconId;
    public int countLike;
    public int countReview;
    public int countShare;

    public ItemBean() {
        this.title = "";
        this.content = "";
        this.iconId = 0;
        this.imageBean = null;

        this.info = String.valueOf(System.currentTimeMillis());
        this.countLike = 315;
        this.countReview = 47;
        this.countShare = 85;
    }
}
