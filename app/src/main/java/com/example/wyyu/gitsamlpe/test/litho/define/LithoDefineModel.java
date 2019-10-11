package com.example.wyyu.gitsamlpe.test.litho.define;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineImage;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class LithoDefineModel extends ViewModel {

    private static final String[] CONTENT_ARRAY = new String[] {
        "这是一段短的测试文字，在屏幕上最多最多展示为两行，再加一段后缀文字",
        "这是一段长的测试文字，最多展示四行，用一些乱七八糟的语言让它长度尽可能地长，比如复制一段Litho的描述：Litho 是 FaceBook "
            + "2017年上半年开源的声明式UI渲染框架，采用 Yoga 这个布局引擎 把 measure 和 layout 放到后台线程"
    };
    private static final String[] TITLE_ARRAY = new String[] { "大圣", "罗小黑", "哪吒" };

    private static final DefineImage[] IMAGE_ARRAY = new DefineImage[] {
        new DefineImage(R.mipmap.image_test_1, 800, 1066),
        new DefineImage(R.mipmap.image_test_2, 800, 480),
        new DefineImage(R.mipmap.image_test_3, 700, 934),
        new DefineImage(R.mipmap.image_test_4, 700, 990)
    };

    private static final int[] ICON_ARRAY = new int[] {
        R.mipmap.multi_image_1, R.mipmap.multi_image_2, R.mipmap.multi_image_3
    };

    public LithoDefineModel() {

    }

    void loadList(@NonNull LoadListCallback callback) {
        Observable.unsafeCreate((Observable.OnSubscribe<List<DefineData>>) subscriber -> {
            List<DefineData> dataList = new ArrayList<>();
            for (int index = 0; index < 32; index++) {
                DefineData defineData = new DefineData();
                defineData.content = CONTENT_ARRAY[index % CONTENT_ARRAY.length];
                defineData.title = TITLE_ARRAY[index % TITLE_ARRAY.length];
                defineData.imageBean = IMAGE_ARRAY[index % IMAGE_ARRAY.length];
                defineData.iconId = ICON_ARRAY[index % ICON_ARRAY.length];
                defineData.countLike = defineData.countLike + index;
                defineData.countReview = defineData.countReview + index * 2;
                defineData.index = index;
                dataList.add(defineData);
            }
            subscriber.onNext(dataList);
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(dataList -> {
                if (dataList == null || dataList.isEmpty()) {
                    callback.onFailure();
                } else {
                    callback.onSuccess(dataList);
                }
            }, throwable -> {
                ULog.show(throwable.getMessage());
                callback.onFailure();
            });
    }

    public interface LoadListCallback {

        void onSuccess(List<DefineData> dataList);

        void onFailure();
    }
}
