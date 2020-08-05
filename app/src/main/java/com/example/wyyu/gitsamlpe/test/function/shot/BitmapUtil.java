package com.example.wyyu.gitsamlpe.test.function.shot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class BitmapUtil {

    // 将一张 Bitmap 保存为本地文件
    public static void saveToFile(Bitmap bitmap, String filePath) {
        if (bitmap == null || TextUtils.isEmpty(filePath)) {
            return;
        }
        File f = new File(filePath);
        try {
            f.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fOut == null) {
            return;
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从SD卡上获取图片。如果不存在则返回null</br>
     *
     * @param path 图片的path地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return 代表图片的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String path, int width, int height) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
            if (inputStream != null && inputStream.available() > 0) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                    getScaleBitmapOptions(path, width, height));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据指定的宽高设置相关参数，避免出现OOM现象</br>
     *
     * @param url 图片得url地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return BitmapFactory.Options对象
     */
    private static BitmapFactory.Options getScaleBitmapOptions(String url, int width, int height) {
        InputStream inputStream = getBitmapStream(url);
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);

            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / height);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / width);

            /*
             * If both of the ratios are greater than 1, one of the sides of the
             * image is greater than the screen
             */
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }

            // Decode it for real
            bmpFactoryOptions.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭java 层的stream
        closeInputStream(inputStream);

        return bmpFactoryOptions;
    }

    /**
     * 根据url地址获取图片本地Stream</br>
     *
     * @return 本地图片的Stream，否则返回null
     */
    public static InputStream getBitmapStream(String path) {
        InputStream is = null;
        try {
            try {
                is = new FileInputStream(new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (is == null || is.available() <= 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BitmapUtil", "读取图片流出错" + e.toString());
        }
        return is;
    }

    /**
     * 关闭输入流</br>
     *
     * @param inputStream 输入流
     */
    private static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据普通 View 生成一张 Bitmap
    public static Bitmap getViewBitmap(@NonNull final View view) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
            view.getMeasuredHeight());

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bitmap;
    }

    // 由一个 ScrollView 生成一张 Bitmap
    public static Bitmap shotScrollView(ScrollView scrollView) {
        int viewHeight = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            viewHeight += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }

        Bitmap bitmap =
            Bitmap.createBitmap(scrollView.getWidth(), viewHeight, Bitmap.Config.RGB_565);

        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        return bitmap;
    }
}
