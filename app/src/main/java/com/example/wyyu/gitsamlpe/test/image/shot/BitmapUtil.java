package com.example.wyyu.gitsamlpe.test.image.shot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ScrollView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class BitmapUtil {

    // 将一张 Bitmap 保存为本地文件
    public static void saveToFile(Bitmap bitmap, String filePath) {
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            assert fOut != null;
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
