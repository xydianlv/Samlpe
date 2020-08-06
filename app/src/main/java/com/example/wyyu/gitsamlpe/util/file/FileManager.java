package com.example.wyyu.gitsamlpe.util.file;

import android.os.Environment;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import java.io.File;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class FileManager {

    private static class FileManagerHolder {
        private static FileManager fileManager = new FileManager();
    }

    public static FileManager getFileManager() {
        return FileManagerHolder.fileManager;
    }

    private static final String DIR_NAME_BITMAP = "bitmap";
    private static final String DIR_NAME_AUDIO = "audio";
    private static final String DIR_NAME_IMAGE = "image";
    private static final String DIR_NAME_ROOT = "ExTest";

    private File cacheBitmap;
    private File cacheDir;

    private File imageDir;
    private File audioDir;
    private File rootDir;

    private FileManager() {
        String sdPath = StorageUtil.getExternalStoragePath();
        if (sdPath != null) {
            rootDir = new File(sdPath + "/" + DIR_NAME_ROOT);
            if (!rootDir.exists()) {
                rootDir.mkdirs();
            } else if (!rootDir.isDirectory()) {
                rootDir.delete();
                rootDir.mkdirs();
            }
        } else {
            ULog.show("SD卡不可用");
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || !Environment.isExternalStorageRemovable()) {
            cacheDir = AppController.getAppContext().getExternalCacheDir();
        } else {
            cacheDir = AppController.getAppContext().getCacheDir();
        }
    }

    public String getAudioDir() {

        if (rootDir == null) {
            return null;
        }
        if (audioDir == null) {
            audioDir = new File(rootDir, DIR_NAME_AUDIO);
            if (!audioDir.exists()) {
                audioDir.mkdirs();
            }
        }
        return audioDir.getAbsolutePath();
    }

    public String getImageDir() {

        if (rootDir == null) {
            return null;
        }
        if (imageDir == null) {
            imageDir = new File(rootDir, DIR_NAME_IMAGE);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
        }
        return imageDir.getAbsolutePath();
    }

    public String getBitmapCache() {
        if (cacheDir == null) {
            return null;
        }
        if (cacheBitmap == null) {
            cacheBitmap = new File(cacheDir, DIR_NAME_BITMAP);
            if (!cacheBitmap.exists()) {
                cacheBitmap.mkdirs();
            }
        }
        return cacheBitmap.getAbsolutePath();
    }
}
