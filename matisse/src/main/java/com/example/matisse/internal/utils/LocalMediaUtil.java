package com.example.matisse.internal.utils;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;
import com.example.matisse.internal.entity.Item;

/**
 * Created by wyyu on 2019/4/3.
 **/
public class LocalMediaUtil {

    private static class UtilHolder {
        private static final LocalMediaUtil mediaUtil = new LocalMediaUtil();
    }

    public static LocalMediaUtil getMediaUtil() {
        return UtilHolder.mediaUtil;
    }

    private MediaMetadataRetriever retriever;

    private LocalMediaUtil() {
        retriever = new MediaMetadataRetriever();
    }

    public void judgeVideoShow(Item item) {
        try {
            if (TextUtils.isEmpty(item.path)) {
                return;
            }
            retriever.setDataSource(item.path);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                item.height = Integer.valueOf(
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                item.width = Integer.valueOf(
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            }
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
        }
    }
}
