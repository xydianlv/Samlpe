package com.example.wyyu.gitsamlpe.test.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class LocalFileBeanList {

    private List<LocalFileBean> localFileBeanList;
    private LocalFileBean parentFolder;


    LocalFileBeanList(String folderName) {
        this.parentFolder = new LocalFileBean(folderName);
        this.localFileBeanList = new ArrayList<>();
    }

    LocalFileBeanList(LocalFileBean parentFolder) {
        this.parentFolder = parentFolder;
        this.localFileBeanList = new ArrayList<>();
    }

    void setParentFolder(LocalFileBean parentFolder) {
        this.parentFolder = parentFolder;
    }

    void setLocalFileBeanList(List<LocalFileBean> localFileBeanList) {
        Collections.sort(localFileBeanList, new Comparator<LocalFileBean>() {
            @Override
            public int compare(LocalFileBean o1, LocalFileBean o2) {
                if (o1.getFile().isDirectory() && o2.getFile().isFile()) {
                    return -1;
                } else if (o1.getFile().isFile() && o2.getFile().isDirectory()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        this.localFileBeanList = localFileBeanList;
    }

    LocalFileBean getParentFolder() {
        return parentFolder;
    }

    List<LocalFileBean> getLocalFileBeanList() {
        return localFileBeanList;
    }
}
