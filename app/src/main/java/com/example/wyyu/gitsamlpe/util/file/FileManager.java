package com.example.wyyu.gitsamlpe.util.file;

import com.example.wyyu.gitsamlpe.framework.ULog;
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

    private static final String DIR_NAME_AUDIO = "audio";
    private static final String DIR_NAME_IMAGE = "image";
    private static final String DIR_NAME_ROOT = "ExTest";

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
    }

    public String getAudioDir() {

        if (rootDir == null) return null;

        if (audioDir == null) {
            audioDir = new File(rootDir, DIR_NAME_AUDIO);
            if (!audioDir.exists()) {
                audioDir.mkdirs();
            }
        }
        return audioDir.getAbsolutePath();
    }

    public String getImageDir() {

        if (rootDir == null) return null;

        if (imageDir == null) {
            imageDir = new File(rootDir, DIR_NAME_IMAGE);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
        }
        return imageDir.getAbsolutePath();
    }
}
