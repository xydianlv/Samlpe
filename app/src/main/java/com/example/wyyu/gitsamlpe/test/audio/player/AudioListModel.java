package com.example.wyyu.gitsamlpe.test.audio.player;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/9/20.
 **/

public class AudioListModel extends ViewModel {

    public AudioListModel() {

    }

    void loadAudioList(@NonNull final LoadAudioListCallback callback) {

        Observable.unsafeCreate(new Observable.OnSubscribe<List<AudioDataBean>>() {
            @Override public void call(Subscriber<? super List<AudioDataBean>> subscriber) {
                List<AudioDataBean> audioDataList = new LinkedList<>();
                Cursor cursor = AppController.getAppContext()
                    .getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

                if (cursor == null) {
                    subscriber.onError(new Throwable("音频数据获取失败"));
                    subscriber.onCompleted();
                    return;
                }
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        AudioDataBean audio = new AudioDataBean();

                        audio.id =
                            cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                        audio.name = cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                        audio.album = cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                        audio.albumId =
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        audio.artist = cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                        audio.path = cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                        audio.duration = cursor.getInt(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                        audio.size = cursor.getLong(
                            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                        audioDataList.add(audio);
                        cursor.moveToNext();
                    }
                }

                subscriber.onNext(audioDataList);

                cursor.close();
                subscriber.onCompleted();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<AudioDataBean>>() {
                @Override public void call(List<AudioDataBean> audioDataBeanList) {
                    if (audioDataBeanList == null || audioDataBeanList.isEmpty()) {
                        callback.onFailure();
                        return;
                    }
                    callback.onSuccess(audioDataBeanList);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    UToast.showShort(AppController.getAppContext(), throwable.getMessage());
                    callback.onFailure();
                }
            });
    }

    public interface LoadAudioListCallback {

        void onSuccess(List<AudioDataBean> audioDataList);

        void onFailure();
    }
}
