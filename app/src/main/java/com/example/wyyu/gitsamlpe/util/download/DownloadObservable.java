package com.example.wyyu.gitsamlpe.util.download;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.contact.GlobalFloatView;
import com.example.wyyu.gitsamlpe.framework.contact.IGlobalFloatView;
import java.util.HashMap;

/**
 * Created by wyyu on 2018/6/28.
 **/

public class DownloadObservable implements IDownloadObservable {

    private static class ObservableHolder {
        private static DownloadObservable observable = new DownloadObservable();
    }

    private static class DownloadValue {

        // 该文件已下载的数据量
        int progress;
        // 待下载文件的总数据量
        int total;

        DownloadValue(int total, int progress) {
            this.progress = progress;
            this.total = total;
        }
    }

    public static DownloadObservable getObservable() {
        return ObservableHolder.observable;
    }

    // 启动的 Activity 栈
    private HashMap<Activity, IGlobalFloatView> activityMap;
    // 每个下载任务对应的下载量
    private HashMap<Long, DownloadValue> progressMap;

    private DownloadObservable() {
        activityMap = new HashMap<>();
        progressMap = new HashMap<>();
    }

    @Override public void refreshProgress(long id, int progress) {
        if (progressMap == null || progressMap.isEmpty() || !progressMap.containsKey(id)) {
            return;
        }
        progressMap.get(id).progress = progress;
        refreshDownloadValue();
    }

    @Override public void refreshPercent(long id, float percent) {
        if (progressMap == null || progressMap.isEmpty() || !progressMap.containsKey(id)) {
            return;
        }
        progressMap.get(id).progress = (int) (progressMap.get(id).total * percent);
        refreshDownloadValue();
    }

    @Override public void addNewProgress(long id, int total) {
        if (progressMap == null) {
            progressMap = new HashMap<>();
        }
        if (progressMap.isEmpty() && activityMap != null) {
            for (Activity activity : activityMap.keySet()) {
                activityMap.get(activity).show(activity);
            }
        }
        progressMap.put(id, new DownloadValue(total, 0));
        refreshDownloadValue();
    }

    @Override public void removeProgress(long id) {
        if (progressMap == null || progressMap.isEmpty() || !progressMap.containsKey(id)) {
            return;
        }
        progressMap.remove(id);
        refreshDownloadValue();
    }

    @Override public void attachActivity(@NonNull Activity activity) {
        if (activityMap == null) {
            activityMap = new HashMap<>();
        }
        IGlobalFloatView globalFloatView = new GlobalFloatView();
        activityMap.put(activity, globalFloatView);
        if (progressMap.isEmpty()) {
            return;
        }
        globalFloatView.show(activity);
        globalFloatView.refreshDownloadValue(getPresentPercent());
    }

    @Override public void detachActivity(@NonNull Activity activity) {
        if (activityMap == null || activityMap.isEmpty()) {
            return;
        }
        if (activityMap.get(activity) != null) {
            activityMap.get(activity).hide();
        }
        activityMap.remove(activity);
    }

    @Override public void release() {
        if (activityMap == null || activityMap.isEmpty()) {
            return;
        }
        for (Activity data : activityMap.keySet()) {
            activityMap.get(data).hide();
        }
        activityMap.clear();
        activityMap = null;
    }

    private void refreshDownloadValue() {

        if (progressMap == null || progressMap.isEmpty()) {
            for (Activity data : activityMap.keySet()) {
                activityMap.get(data).hide();
            }
            return;
        }
        float percent = getPresentPercent();
        for (Activity data : activityMap.keySet()) {
            activityMap.get(data).refreshDownloadValue(percent);
        }
    }

    private float getPresentPercent() {
        int downloadSize = 0;
        int totalSize = 0;
        for (Long key : progressMap.keySet()) {
            downloadSize = downloadSize + progressMap.get(key).progress;
            totalSize = totalSize + progressMap.get(key).total;
        }
        return (float) downloadSize / totalSize;
    }
}
