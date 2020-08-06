package com.example.wyyu.gitsamlpe.test.image.local;

import android.database.Cursor;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

/**
 * Created by wyyu on 2020-08-06.
 **/

public class LocalImageModel extends ViewModel {

    public LocalImageModel() {

    }

    public void loadLocalImageList(@NonNull LoadCallback callback) {
        Observable.unsafeCreate((Observable.OnSubscribe<List<ImageItem>>) subscriber -> {

            List<ImageItem> itemList = new ArrayList<>();

            Cursor cursor = AppController.getAppContext()
                .getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Images.Media.DEFAULT_SORT_ORDER);

            if (cursor == null) {
                subscriber.onError(new Throwable("图片文件获取失败"));
                subscriber.onCompleted();
                return;
            }
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    ImageItem imageItem = new ImageItem();

                    imageItem.id =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    imageItem.name = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    imageItem.size =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                    imageItem.description = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DESCRIPTION));
                    imageItem.width =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
                    imageItem.height =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
                    imageItem.localPath = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    itemList.add(imageItem);
                    cursor.moveToNext();
                }
            }

            cursor.close();

            subscriber.onNext(itemList);
            subscriber.onCompleted();
        }).subscribe(itemList -> {
            if (itemList == null || itemList.isEmpty()) {
                callback.onFailure();
            } else {
                callback.onSuccess(itemList);
            }
        }, throwable -> {
            ULog.show(throwable.getMessage());
            callback.onFailure();
        });
    }

    public interface LoadCallback {

        void onSuccess(List<ImageItem> itemList);

        void onFailure();
    }
}
