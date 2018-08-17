package com.example.wyyu.gitsamlpe.test.image.edit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.yalantis.ucrop.UCrop;

/**
 * Created by wyyu on 2018/8/17.
 **/

public class Crop {

    public static final int REQUEST_CROP = UCrop.REQUEST_CROP;
    public static final int REQUEST_SYS_CROP = 70;
    public static final int RESULT_ERROR = UCrop.RESULT_ERROR;

    public static void squareCrop(@NonNull Activity activity, @NonNull Uri src, @NonNull Uri dest,
        @Nullable String title) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        squareCrop(activity, src, dest, displayMetrics.widthPixels, title);
    }

    public static void squareCrop(@NonNull Activity activity, @NonNull Uri src, @NonNull Uri dest,
        int maxSize, @Nullable String title) {
        crop(activity, src, dest, maxSize, title, 1, 1);
    }

    public static void crop(@NonNull Activity activity, @NonNull Uri src, @NonNull Uri dest,
        int maxSize, @Nullable String title, int aspectX, int aspectY) {
        UCrop uCrop =
            UCrop.of(src, dest).withMaxResultSize(maxSize, maxSize).withAspectRatio(aspectX, aspectY);
        UCrop.Options options = new UCrop.Options();
        if (!TextUtils.isEmpty(title)) {
            options.setToolbarTitle("裁剪");
        }
        uCrop.withOptions(options);
        uCrop.start(activity);
    }

    public static Uri getOutput(@NonNull Intent intent) {
        return (Uri) intent.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);
    }

    public static Uri systemSquareCrop(@NonNull Activity activity, Uri src, Uri dest,
        int requestCode) {
        return systemCrop(activity, src, dest, requestCode, 1, 1);
    }

    public static Uri systemCrop(@NonNull Activity activity, Uri src, Uri dest, int requestCode,
        int aspectX, int aspectY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(src, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, dest);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
        return dest;
    }
}
