package com.example.wyyu.gitsamlpe.test.file;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wyyu on 2018/3/8.
 **/

class LocalFileBean implements Serializable {

    private String fileName;
    private File file;

    private long changeTime;
    private long fileSize;

    private boolean isDir;
    private String key;

    LocalFileBean(File file) {

        changeTime = file.lastModified();
        key = "" + new Date().getTime();
        fileSize = file.length();
        isDir = file.isDirectory();
        fileName = file.getName();

        this.file = file;
    }

    LocalFileBean(String title) {

        key = "" + new Date().getTime();
        fileName = title;
        isDir = false;
    }


    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String getFileName() {
        return fileName;
    }

    long getChangeTime() {
        return changeTime;
    }

    long getFileSize() {
        return fileSize;
    }

    String getFileKey() {
        return key;
    }

    boolean isDir() {
        return isDir;
    }

    File getFile() {
        return file;
    }
}
