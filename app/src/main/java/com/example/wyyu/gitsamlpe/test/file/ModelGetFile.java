package com.example.wyyu.gitsamlpe.test.file;

import com.example.wyyu.gitsamlpe.util.file.StorageUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class ModelGetFile implements PresenterGetFile.IModelGetFile {

    @Override
    public Observable<LocalFileBeanList> getRootData() {

        return Observable.create(new Observable.OnSubscribe<LocalFileBeanList>() {
            @Override
            public void call(Subscriber<? super LocalFileBeanList> subscriber) {

                LocalFileBeanList fileBeanList = new LocalFileBeanList("根目录");

                String externalStoragePath = StorageUtil.getExternalStoragePath();
                String innerStoragePath = StorageUtil.getInnerStoragePath();

                if (!innerStoragePath.isEmpty()) {
                    LocalFileBean fileBean = new LocalFileBean(new File(innerStoragePath));
                    fileBean.setFileName("内部存储");

                    fileBeanList.getLocalFileBeanList().add(fileBean);
                }

                if (externalStoragePath != null && !externalStoragePath.isEmpty()) {
                    LocalFileBean fileBean = new LocalFileBean(new File(externalStoragePath));
                    fileBean.setFileName("外部存储");

                    fileBeanList.getLocalFileBeanList().add(fileBean);
                }

                subscriber.onNext(fileBeanList);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<LocalFileBeanList> getFileData(final LocalFileBean localFileBean, final boolean showHidden) {

        return Observable.create(new Observable.OnSubscribe<LocalFileBeanList>() {
            @Override
            public void call(Subscriber<? super LocalFileBeanList> subscriber) {

                List<LocalFileBean> fileBeanList = new ArrayList<>();
                File[] files = localFileBean.getFile().listFiles();

                for (File file : files) {
                    if (file.getName().startsWith(".")) {
                        if (showHidden) {
                            fileBeanList.add(new LocalFileBean(file));
                        }
                    } else {
                        fileBeanList.add(new LocalFileBean(file));
                    }
                }

                LocalFileBeanList localFileBeanList = new LocalFileBeanList(localFileBean);
                localFileBeanList.setLocalFileBeanList(fileBeanList);

                subscriber.onNext(localFileBeanList);
                subscriber.onCompleted();
            }
        });
    }
}
