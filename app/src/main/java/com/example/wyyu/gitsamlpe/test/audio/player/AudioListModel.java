package com.example.wyyu.gitsamlpe.test.audio.player;

import androidx.lifecycle.ViewModel;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/9/20.
 **/

public class AudioListModel extends ViewModel {

    private List<File> scanFileList;
    private int currentIndex;

    public AudioListModel() {
        scanFileList = new ArrayList<>();
        currentIndex = 0;
    }

    void loadAudio(@NonNull final LoadAudioListCallback callback) {
        File rootFile = Environment.getExternalStorageDirectory();
        if (rootFile == null || !rootFile.exists()) {
            callback.onFailure();
            return;
        }
        if (scanFileList == null) {
            scanFileList = new ArrayList<>();
        }
        File[] fileArray = rootFile.listFiles();
        for (File file : fileArray) {
            if (file == null || !file.exists()) {
                continue;
            }
            if (file.isDirectory()) {
                scanAllFile(file);
            } else {
                scanFileList.add(file);
            }
        }

        currentIndex = 0;
        scanFileList(callback);
    }

    private void scanFileList(@NonNull final LoadAudioListCallback callback) {
        if (scanFileList == null || scanFileList.isEmpty()) {
            callback.onFailure();
            return;
        }
        if (currentIndex >= scanFileList.size()) {
            loadAudioList(callback);
            return;
        }
        MediaScannerConnection.scanFile(AppController.getAppContext(),
            new String[] { scanFileList.get(currentIndex).getAbsolutePath() }, null,
            (path, uri) -> {
                currentIndex++;
                scanFileList(callback);
            });
    }

    private void scanAllFile(File rootFile) {
        if (rootFile == null) {
            return;
        }
        File[] fileArray = rootFile.listFiles();
        for (File file : fileArray) {
            if (file == null || !file.exists()) {
                continue;
            }
            if (file.isDirectory()) {
                scanAllFile(file);
            } else {
                MediaScannerConnection.scanFile(AppController.getAppContext(), new String[] {
                    file.getAbsolutePath()
                }, null, null);
            }
        }
    }

    void loadAudioList(@NonNull final LoadAudioListCallback callback) {

        Observable.unsafeCreate((Observable.OnSubscribe<List<AudioDataBean>>) subscriber -> {
            List<AudioDataBean> audioDataList = new LinkedList<>();
            //Cursor cursor = AppController.getAppContext()
            //    .getContentResolver()
            //    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {
            //        MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            //        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE
            //    }, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
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
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
                    audio.path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    audio.duration = cursor.getInt(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    audio.size =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                    audioDataList.add(audio);
                    cursor.moveToNext();
                }
            }

            subscriber.onNext(audioDataList);

            cursor.close();
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(audioDataBeanList -> {
                if (audioDataBeanList == null || audioDataBeanList.isEmpty()) {
                    callback.onFailure();
                    return;
                }
                callback.onSuccess(audioDataBeanList);
            }, throwable -> {
                UToast.showShort(AppController.getAppContext(), throwable.getMessage());
                callback.onFailure();
            });
    }

    public interface LoadAudioListCallback {

        void onSuccess(List<AudioDataBean> audioDataList);

        void onFailure();
    }
}
