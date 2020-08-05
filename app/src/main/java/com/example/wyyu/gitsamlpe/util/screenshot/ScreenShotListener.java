package com.example.wyyu.gitsamlpe.util.screenshot;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppInstances;
import com.example.wyyu.gitsamlpe.test.function.shot.BitmapUtil;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.example.wyyu.gitsamlpe.util.file.FileManager;

/**
 * Created by wyyu on 2018/10/26.
 **/

public class ScreenShotListener implements IScreenShotListener {

    private static class ListenerHolder {
        private static IScreenShotListener screenCutListener = new ScreenShotListener();
    }

    public static IScreenShotListener getListener() {
        return ListenerHolder.screenCutListener;
    }

    private static final String[] MEDIA_PROJECTIONS = {
        MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.WIDTH,
        MediaStore.Images.ImageColumns.HEIGHT,
    };

    private static final String[] SCREEN_SHOT_PATH_ARRAY = {
        "screenshot", "screen_shot", "screen-shot", "screencapture", "screen_capture",
        "screen-capture", "screencap", "screen_cap", "screen-cap", "Screenshot", "Screencapture",
        "Screencap", "ScreenShot", "ScreenCapture", "ScreenCap",
    };

    private ContentObserver internalObserver;
    private ContentObserver externalObserver;

    private Activity activity;

    private ScreenShotListener() {
        internalObserver = new ContentObserver(new Handler()) {
            @Override public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);

                dispatchContentUri(uri);
            }
        };
        externalObserver = new ContentObserver(new Handler()) {
            @Override public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);

                dispatchContentUri(uri);
            }
        };
    }

    @Override public void registerListener(@NonNull Activity activity) {
        activity.getContentResolver()
            .registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false,
                internalObserver);
        activity.getContentResolver()
            .registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
                externalObserver);

        this.activity = activity;
    }

    @Override public void unregisterListener(@NonNull Activity activity) {
        activity.getContentResolver().unregisterContentObserver(internalObserver);
        activity.getContentResolver().unregisterContentObserver(externalObserver);

        this.activity = null;
    }

    private void dispatchContentUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            // 数据改变时查询数据库中最后加入的一条数据
            cursor = activity.getContentResolver()
                .query(contentUri, MEDIA_PROJECTIONS, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1");

            if (cursor == null) {
                ULog.show("Deviant logic.");
                return;
            }
            if (!cursor.moveToFirst()) {
                ULog.show("Cursor no data.");
                return;
            }

            int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH));
            int height =
                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT));
            String path =
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            handleImageData(path, width, height);
        } catch (Exception e) {
            ULog.show(e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void handleImageData(String path, int width, int height) {
        if (!checkScreenShot(path, width, height)) {
            ULog.show("非屏幕截图");
            return;
        }
        saveAndShare(path);
    }

    /**
     * 判断图片是否是屏幕截图
     *
     * @param path 图片路径
     * @param width 宽度
     * @param height 高度
     * @return 返回判断结果
     */
    private boolean checkScreenShot(String path, int width, int height) {

        if (TextUtils.isEmpty(path)) {
            return false;
        }

        if (width > UIUtils.getScreenWidth() || height > UIUtils.getScreenHeight()) {
            return false;
        }

        for (String pathKey : SCREEN_SHOT_PATH_ARRAY) {
            if (path.contains(pathKey)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 拿到屏幕截图后，保存在缓存中并进行分享
     *
     * @param path 截图路径
     */
    private void saveAndShare(final String path) {

        AppInstances.getPoolExecutor().forLocalStorageWrite().execute(new Runnable() {
            @Override public void run() {
                Bitmap bitmapShot = BitmapUtil.getBitmapFromSDCard(path, UIUtils.getScreenWidth(),
                    UIUtils.getScreenHeight());
                if (bitmapShot == null) {
                    return;
                }
                // 截图缓存路径
                final String filePath =
                    FileManager.getFileManager().getImageDir() + "_share_shot" + ".jpg";

                // 裁剪掉状态栏的手机截图
                int statusBarHeight = UIUtils.getStatusHeightByDecor(activity);
                bitmapShot =
                    Bitmap.createBitmap(bitmapShot, 0, statusBarHeight, bitmapShot.getWidth(),
                        bitmapShot.getHeight() - statusBarHeight);

                // 底部的Logo
                Bitmap bitmapLogo =
                    BitmapFactory.decodeResource(activity.getResources(), R.mipmap.navbar_select);

                // Logo 为 NULL 时，只分享截图
                if (bitmapLogo == null) {
                    BitmapUtil.saveToFile(bitmapShot, filePath);
                    return;
                }

                // 将 Logo 的 Bitmap 等比缩放到与截图的 Bitmap 一致
                float scale = ((float) bitmapShot.getWidth()) / bitmapLogo.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                bitmapLogo = Bitmap.createBitmap(bitmapLogo, 0, 0, bitmapLogo.getWidth(),
                    bitmapLogo.getHeight(), matrix, true);

                // 将 bitmapLogo 与 bitmapShot 拼接
                int width = bitmapShot.getWidth();
                int height = bitmapShot.getHeight() + bitmapLogo.getHeight();

                Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

                Canvas canvas = new Canvas(result);
                canvas.drawBitmap(bitmapShot, 0, 0, null);
                canvas.drawBitmap(bitmapLogo, 0, bitmapShot.getHeight(), null);

                // 存入缓存并展示分享弹窗
                BitmapUtil.saveToFile(result, filePath);
            }
        });
    }
}
