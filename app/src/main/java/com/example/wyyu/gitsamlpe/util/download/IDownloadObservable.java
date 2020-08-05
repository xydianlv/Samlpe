package com.example.wyyu.gitsamlpe.util.download;

import android.app.Activity;
import androidx.annotation.NonNull;

/**
 * Created by wyyu on 2018/6/28.
 **/

public interface IDownloadObservable {

    /**
     * 通过当前的下载量刷新指定 下载事件 的下载数据
     *
     * @param id 下载事件的 ID
     * @param progress 当前的下载量
     */
    void refreshProgress(long id, int progress);

    /**
     * 通过当前的下载百分比刷新指定 下载事件 的下载数据
     *
     * @param id 下载事件的 ID
     * @param percent 当前下载量的百分比
     */
    void refreshPercent(long id, float percent);

    /**
     * 开始下载时，将一个新的下载事件添加到 Observable
     *
     * @param id 下载事件 ID
     * @param total 待下载数据的总大小
     */
    void addNewProgress(long id, int total);

    /**
     * 下载完成后，调用该方法解除掉下载事件
     *
     * @param id 下载事件 ID
     */
    void removeProgress(long id);

    /**
     * 打开一个 Activity 时，添加一个 Activity 到对象栈
     *
     * @param activity 打开的新的 Activity
     */
    void attachActivity(@NonNull Activity activity);

    /**
     * 结束一个 Activity 时，从对象栈中删掉该 Activity
     *
     * @param activity 结束的 Activity
     */
    void detachActivity(@NonNull Activity activity);

    /**
     * 退出主界面时，释放掉 Activity 栈
     */
    void release();
}
