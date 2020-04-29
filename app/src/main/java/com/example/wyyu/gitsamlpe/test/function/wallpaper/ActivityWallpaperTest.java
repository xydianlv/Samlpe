package com.example.wyyu.gitsamlpe.test.function.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import com.example.wyyu.gitsamlpe.test.function.shot.BitmapUtil;
import java.io.IOException;

import static android.app.WallpaperManager.FLAG_LOCK;

/**
 * Created by wyyu on 2020-04-27.
 **/

public class ActivityWallpaperTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityWallpaperTest.class));
    }

    @BindView(R.id.wallpaper_test_layout) View imageContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_test);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.wallpaper_btn_lock).setOnClickListener(v -> setLockPaper());
        findViewById(R.id.wallpaper_btn_home).setOnClickListener(v -> setHomePaper());
    }

    private void setLockPaper() {
        WallpaperManager manager = WallpaperManager.getInstance(this);
        Bitmap bitmap = BitmapUtil.getViewBitmap(imageContainer);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, FLAG_LOCK);
                UToast.showShort(ActivityWallpaperTest.this, "锁屏设置成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHomePaper() {
        WallpaperManager manager = WallpaperManager.getInstance(this);
        Bitmap bitmap = BitmapUtil.getViewBitmap(imageContainer);
        try {
            manager.setBitmap(bitmap);
            UToast.showShort(ActivityWallpaperTest.this, "壁纸设置成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
