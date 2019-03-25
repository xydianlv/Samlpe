package com.example.wyyu.gitsamlpe.test.image.shot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.io.File;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class ActivityShotScreen extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityShotScreen.class));
    }

    @BindView(R.id.shot_screen_scroll) ScrollView scrollView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_screen);

        initActivity();
    }

    private void initActivity() {
        findViewById(R.id.shot_screen_save).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                saveShotScreen();
            }
        });
    }

    private void saveShotScreen() {
        final File file =
            new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "img_share_personal.jpg");
        if (file.exists()) {
            file.delete();
        }
        final String filePath = file.getAbsolutePath();

        scrollView.post(new Runnable() {
            @Override public void run() {
                Bitmap bitmap = BitmapUtil.shotScrollView(scrollView);
                BitmapUtil.saveToFile(bitmap, filePath);
                bitmap.recycle();

                sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                UToast.showShort(ActivityShotScreen.this, "图片已保存到本地相册");
            }
        });
    }
}
