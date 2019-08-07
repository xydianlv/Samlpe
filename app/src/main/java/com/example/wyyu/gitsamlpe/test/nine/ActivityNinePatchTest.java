package com.example.wyyu.gitsamlpe.test.nine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.test.image.shot.BitmapUtil;
import com.example.wyyu.gitsamlpe.test.nine.util.NinePatchChunk;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.example.wyyu.gitsamlpe.util.file.StorageUtil;
import java.io.File;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-08-07.
 **/

public class ActivityNinePatchTest extends FullScreenActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityNinePatchTest.class));
    }

    private static final String NINE_PATCH_PATH = "/Download/img_review_bubble.9.png";

    @BindView(R.id.nine_patch_height) View testHeight;
    @BindView(R.id.nine_patch_width) View testWidth;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_patch_test);

        initActivity();
    }

    private void initActivity() {

        Observable.unsafeCreate(new Observable.OnSubscribe<Drawable>() {
            @Override public void call(Subscriber<? super Drawable> subscriber) {
                File imgFile = new File(StorageUtil.getExternalStoragePath(), NINE_PATCH_PATH);
                if (imgFile.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //byte[] chunk = bitmap.getNinePatchChunk();
                    //NinePatch.isNinePatchChunk(chunk);
                    //NinePatchDrawable drawable =
                    //    new NinePatchDrawable(getResources(), bitmap, bitmap.getNinePatchChunk(),
                    //        new Rect(), null);
                    NinePatchDrawable drawable =
                        NinePatchChunk.create9PatchDrawable(ActivityNinePatchTest.this, bitmap,
                            null);
                    subscriber.onNext(drawable);
                } else {
                    subscriber.onError(new Throwable("文件不存在"));
                }

                subscriber.onCompleted();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Drawable>() {
                @Override public void call(Drawable drawable) {
                    testWidth.setBackground(drawable);
                    testHeight.setBackground(drawable);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    ULog.show(throwable.getMessage());
                }
            });
    }
}
