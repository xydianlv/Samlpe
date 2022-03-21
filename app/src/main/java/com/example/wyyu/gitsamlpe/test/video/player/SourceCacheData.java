package com.example.wyyu.gitsamlpe.test.video.player;

import android.net.Uri;
import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2020-07-27.
 **/

public class SourceCacheData {

    private static final int PRELOAD_CACHE_SIZE = 320 * 1024;

    private static final class CacheManager {
        private static final SourceCacheData instance = new SourceCacheData();
    }

    private static class CacheData {

        private String url;
        private String key;

        CacheData(String url, String key) {
            this.url = url;
            this.key = key;
        }

        boolean emptyData() {
            return TextUtils.isEmpty(url) || TextUtils.isEmpty(key);
        }
    }

    public static SourceCacheData getInstance() {
        return CacheManager.instance;
    }

    private List<CacheData> cacheDataList;
    private List<CacheData> funList;

    private SimpleCache simpleCache;
    private boolean onCache;
    private boolean onPre;

    private SourceCacheData() {
        initBasicValue();
        initSimpleCache();
    }

    private void initBasicValue() {
        cacheDataList = new ArrayList<>();
        funList = new ArrayList<>();

        onCache = false;
        onPre = false;
    }

    private void initSimpleCache() {
        File cachePath = AppController.getAppContext().getExternalCacheDir();
        long maxBytes;

        if (cachePath == null || !cachePath.exists()) {
            cachePath = new File(cachePath, "video");
            maxBytes = 512 * 1024 * 1024;
        } else {
            cachePath = new File(AppController.getAppContext().getCacheDir(), "video");
            maxBytes = 50 * 1024 * 1024;
        }

        simpleCache = new SimpleCache(cachePath, new LeastRecentlyUsedCacheEvictor(maxBytes));
    }

    void preload(String url, String uniqueKey) {
        onPre = true;
        if (funList == null) {
            funList = new ArrayList<>();
        }
        funList.add(new CacheData(url, uniqueKey));
        onPre = false;

        if (onCache) {
            return;
        }

        if (cacheDataList == null) {
            cacheDataList = new ArrayList<>();
        }
        cacheDataList.addAll(funList);
        funList.clear();

        cacheSource();
    }

    private void cacheSource() {
        if (!onPre && funList != null && !funList.isEmpty()) {
            if (cacheDataList == null) {
                cacheDataList = new ArrayList<>();
            }
            cacheDataList.addAll(funList);
            funList.clear();
        }
        if (cacheDataList == null || cacheDataList.isEmpty()) {
            onCache = false;
            return;
        }
        CacheData cacheData = cacheDataList.get(0);
        onCache = true;

        Observable.unsafeCreate((Observable.OnSubscribe<Boolean>) subscriber -> {
            if (cacheData == null || cacheData.emptyData()) {
                subscriber.onError(new Throwable("缓存数据为空"));
                return;
            }
            DataSpec dataSpec =
                new DataSpec(Uri.parse(cacheData.url), 0, PRELOAD_CACHE_SIZE, cacheData.key);

            try {
//                CacheUtil.cache(dataSpec, simpleCache, null, null, null, null);
                subscriber.onNext(true);
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            } finally {
                subscriber.onCompleted();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(aBoolean -> {
                ULog.show("CacheSuccess");
                onCacheEnd();
            }, throwable -> {
                ULog.show("CacheError");
                onCacheEnd();
            });
    }

    private void onCacheEnd() {
        if (cacheDataList == null) {
            onCache = false;
            return;
        }
        cacheDataList.remove(0);
        if (cacheDataList.isEmpty()) {
            onCache = false;
            return;
        }
        cacheSource();
    }
}
