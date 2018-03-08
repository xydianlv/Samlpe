package com.example.wyyu.gitsamlpe.test.file;

import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/3/8.
 **/

class PresenterGetFile {

    private LinkedList<LocalFileBean> checkFileList;
    private LocalFileBeanList fileBeanList;

    private ILocalFileListView fileListView;
    private IModelGetFile modelGetFile;

    private boolean showHidden;


    PresenterGetFile(ILocalFileListView fileListView) {
        this.fileListView = fileListView;
        modelGetFile = new ModelGetFile();

        checkFileList = new LinkedList<>();
        showHidden = false;
    }

    void initPresenter() {

        fileListView.showLoading();

        visitRootLocalFolder();
    }

    // 访问 根目录
    private void visitRootLocalFolder() {

        checkFileList.clear();

        modelGetFile.getRootData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<LocalFileBeanList>() {
                @Override
                public void call(LocalFileBeanList localFileBeanList) {
                    if (localFileBeanList != null && !localFileBeanList.getLocalFileBeanList().isEmpty()) {

                        checkFileList.add(localFileBeanList.getParentFolder());
                        fileBeanList = localFileBeanList;

                        fileListView.refreshGuestView(checkFileList);
                        fileListView.setFileList(localFileBeanList);
                        fileListView.showList();
                    } else {
                        fileListView.showEmpty();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.d("", "" + throwable.getMessage());
                }
            });
    }

    // 访问 指定目录文件
    void visitLocalFolder(LocalFileBean localFileBean) {

        modelGetFile.getFileData(localFileBean, showHidden)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<LocalFileBeanList>() {
                @Override
                public void call(LocalFileBeanList localFileBeanList) {
                    refreshBasicData(localFileBeanList);

                    fileListView.refreshGuestView(checkFileList);
                    fileListView.setFileList(localFileBeanList);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.d("", "" + throwable.getMessage());
                }
            });

    }

    // 返回 或者 显示／隐藏 隐藏文件时，判断是否需要更新 checkFileList
    private void refreshBasicData(LocalFileBeanList localFileBeanList) {

        if (!checkFileList.getLast().getFileKey().equals(localFileBeanList.getParentFolder().getFileKey())) {
            checkFileList.add(localFileBeanList.getParentFolder());
        }

        fileBeanList = localFileBeanList;
    }

    // 点击导航栏，返回到 指定文件目录
    void backLocalFolder(LocalFileBean localFileBean) {

        while (!checkFileList.getLast().getFileKey().equals(localFileBean.getFileKey())) {
            checkFileList.removeLast();
        }

        if (isRoot()) {
            visitRootLocalFolder();
        } else {
            visitLocalFolder(checkFileList.getLast());
        }

        refreshFileListView();
    }

    // 点击返回键，返回到 上一目录文件
    boolean backLastFolder() {

        if (isRoot()) return false;

        checkFileList.removeLast();

        backLocalFolder(checkFileList.getLast());
        return true;
    }

    // 根据当前获取到的数据，刷新导航栏和文件列表
    private void refreshFileListView() {
        fileListView.refreshGuestView(checkFileList);
        fileListView.setFileList(fileBeanList);
    }

    private boolean isRoot() {
        return checkFileList != null && checkFileList.size() == 1;
    }

    boolean isShowHidden() {
        return showHidden;
    }

    void showHiddenFile(boolean showHidden) {

        if (this.showHidden == showHidden) return;

        this.showHidden = showHidden;

        visitLocalFolder(fileBeanList.getParentFolder());
    }


    interface ILocalFileListView {

        void setFileList(LocalFileBeanList localFileBeanList);

        void refreshGuestView(List<LocalFileBean> localFileBeanList);

        void showLoading();

        void showEmpty();

        void showList();
    }

    interface IModelGetFile {

        Observable<LocalFileBeanList> getFileData(LocalFileBean localFileBean, boolean showHidden);

        Observable<LocalFileBeanList> getRootData();
    }
}
