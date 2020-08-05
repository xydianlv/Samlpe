package com.example.wyyu.gitsamlpe.test.multi.waterfall;

import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ImageBean;
import com.example.wyyu.gitsamlpe.test.litho.multi.data.ItemBean;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2020-01-13.
 **/

public class WaterFallModel extends ViewModel {

    private static final String[] CONTENT_ARRAY = new String[] {
        "这是一段短的测试文字，在屏幕上最多最多展示为两行，再加一段后缀文字",
        "这是一段长的测试文字，最多展示四行，用一些乱七八糟的语言让它长度尽可能地长，比如复制一段Litho的描述：Litho 是 FaceBook "
            + "2017年上半年开源的声明式UI渲染框架，采用 Yoga 这个布局引擎 把 measure 和 layout 放到后台线程"
    };
    private static final String[] TITLE_ARRAY = new String[] { "大圣", "罗小黑", "哪吒" };

    private static final ImageBean[] IMAGE_ARRAY = new ImageBean[] {
        new ImageBean(R.mipmap.image_test_1, 800, 1066),
        new ImageBean(R.mipmap.image_test_2, 800, 480),
        new ImageBean(R.mipmap.image_test_3, 700, 934),
        new ImageBean(R.mipmap.image_test_4, 700, 990)
    };

    public WaterFallModel() {

    }

    void loadList(@NonNull LoadCallback callback) {

        Observable.unsafeCreate((Observable.OnSubscribe<List<ItemBean>>) subscriber -> {
            List<ItemBean> itemList = new ArrayList<>();
            for (int index = 0; index < 64; index++) {
                ItemBean itemBean = new ItemBean();
                itemBean.content = CONTENT_ARRAY[index % CONTENT_ARRAY.length];
                itemBean.title = TITLE_ARRAY[index % TITLE_ARRAY.length];
                itemBean.imageBean = IMAGE_ARRAY[index % IMAGE_ARRAY.length];
                itemBean.index = index;
                itemList.add(itemBean);
            }
            subscriber.onNext(itemList);
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(datumList -> {
                if (datumList == null || datumList.isEmpty()) {
                    callback.onFailure();
                } else {
                    callback.onSuccess(datumList);
                }
            }, throwable -> callback.onFailure());
    }

    public interface LoadCallback {

        void onSuccess(List<ItemBean> datumList);

        void onFailure();
    }
}
