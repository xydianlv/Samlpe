package com.example.wyyu.gitsamlpe.test.video.model;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import androidx.lifecycle.ViewModel;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.test.function.shot.BitmapUtil;
import com.example.wyyu.gitsamlpe.test.video.data.SourceType;
import com.example.wyyu.gitsamlpe.test.video.data.VideoItem;
import com.example.wyyu.gitsamlpe.util.file.FileManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2020-07-27.
 *
 * 获取视频列表
 **/

public class VideoResultModel extends ViewModel {

    public interface LoadCallback {

        void onSuccess(List<VideoItem> videoList, boolean hasMore);

        void onFailure();
    }

    private static final String CACHE_PATH = FileManager.getFileManager().getBitmapCache();

    private static final int COUNT_PAGE = 12;

    private List<VideoItem> videoItemList;
    private int index;

    public VideoResultModel() {
        videoItemList = new ArrayList<>();
    }

    public void loadVideoList(@NonNull LoadCallback callback) {
        Observable.unsafeCreate((Observable.OnSubscribe<List<VideoItem>>) subscriber -> {
            if (videoItemList == null) {
                videoItemList = new ArrayList<>();
            }
            videoItemList.clear();
            index = 0;

            Cursor cursor = AppController.getAppContext()
                .getContentResolver()
                .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Video.Media.DEFAULT_SORT_ORDER);

            if (cursor == null) {
                subscriber.onError(new Throwable("视频文件获取失败"));
                subscriber.onCompleted();
                return;
            }

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    VideoItem videoItem = new VideoItem();

                    videoItem.id =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    videoItem.name = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    videoItem.title = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    videoItem.duration = cursor.getInt(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    videoItem.size =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    videoItem.sourceValue =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    videoItem.width = cursor.getInt(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.WIDTH));
                    videoItem.height = cursor.getInt(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.HEIGHT));

                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        // MediaMetadataRetriever 获取第一帧，每个文件大概需要 100-200 毫秒
                        // 但是这个是必须的，因为 MediaStore 中的 width 和 height 一般不准，导致显示出现问题
                        retriever.setDataSource(videoItem.sourceValue);
                        Bitmap bitmap = retriever.getFrameAtTime(0);
                        videoItem.width = bitmap.getWidth();
                        videoItem.height = bitmap.getHeight();

                        String coverPath = CACHE_PATH + "/" + videoItem.title;
                        File coverFile = new File(coverPath);
                        if (!coverFile.exists()) {
                            BitmapUtil.saveToFile(bitmap, coverPath);
                        }
                        videoItem.cover = coverPath;
                    } catch (Exception e) {
                        ULog.show(e.getMessage());
                    } finally {
                        retriever.release();
                    }

                    videoItem.sourceType = SourceType.PATH;
                    videoItem.playCount = 0;

                    videoItemList.add(videoItem);
                    cursor.moveToNext();
                }
            }

            index = Math.min(videoItemList.size(), COUNT_PAGE);
            cursor.close();

            subscriber.onNext(videoItemList.subList(0, index));
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(videoItemList -> {
                if (videoItemList == null || videoItemList.isEmpty()) {
                    callback.onFailure();
                } else {
                    callback.onSuccess(videoItemList,
                        index < VideoResultModel.this.videoItemList.size());
                }
            }, throwable -> {
                callback.onFailure();
                ULog.show(throwable.getMessage());
            });
    }

    public void appendVideoList(@NonNull LoadCallback callback) {
        if (videoItemList == null || videoItemList.isEmpty() || index == 0 || index == videoItemList
            .size()) {
            callback.onFailure();
        }
        int lastIndex =
            index + COUNT_PAGE > videoItemList.size() ? videoItemList.size() : index + COUNT_PAGE;
        callback.onSuccess(videoItemList.subList(index, lastIndex),
            lastIndex < videoItemList.size());
        index = lastIndex;
    }
}
