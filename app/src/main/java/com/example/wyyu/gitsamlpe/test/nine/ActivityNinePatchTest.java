package com.example.wyyu.gitsamlpe.test.nine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.nine.util.NinePatchChunk;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by wyyu on 2019-08-07.
 **/

public class ActivityNinePatchTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNinePatchTest.class));
    }

    @BindView(R.id.nine_patch_height) View testHeight;
    @BindView(R.id.nine_patch_width) View testWidth;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_patch_test);

        cacheAndLoad();
    }

    private void cacheAndLoad() {
        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                File cacheFile = getCacheDir();
                if (cacheFile == null) {
                    subscriber.onError(new Throwable("缓存目录获取失败"));
                } else {
                    File file = new File(cacheFile.getAbsolutePath() + "/nine.9.png");
                    if (file.exists()) {
                        subscriber.onNext(file.getAbsolutePath());
                    } else {
                        ByteArrayOutputStream byteOutputStream = null;
                        FileOutputStream outputStream = null;
                        InputStream inputStream = null;
                        byte[] buffer = new byte[10];
                        try {
                            byteOutputStream = new ByteArrayOutputStream();
                            outputStream = new FileOutputStream(file);
                            // 需要保留文件格式的资源放在 assets 目录下加载到 SD
                            inputStream = getAssets().open("img_nine_patch.9.png");
                            int len;
                            while ((len = inputStream.read(buffer)) != -1) {
                                byteOutputStream.write(buffer, 0, len);
                            }
                            outputStream.write(byteOutputStream.toByteArray());
                            outputStream.flush();
                            subscriber.onNext(file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            subscriber.onError(new Throwable("缓存本地失败"));
                        } finally {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (byteOutputStream != null) {
                                try {
                                    byteOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                subscriber.onCompleted();
            }
        }).map(new Func1<String, Drawable>() {
            @Override public Drawable call(String s) {
                File imgFile = new File(s);
                if (imgFile.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    return NinePatchChunk.create9PatchDrawable(ActivityNinePatchTest.this, bitmap,
                        null);
                } else {
                    return null;
                }
            }
        }).subscribe(new Action1<Drawable>() {
            @Override public void call(Drawable drawable) {
                if (testWidth != null) {
                    testWidth.setBackground(drawable);
                }
                if (testHeight != null) {
                    testHeight.setBackground(drawable);
                }
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                ULog.show(throwable.getMessage());
            }
        });
    }
}
