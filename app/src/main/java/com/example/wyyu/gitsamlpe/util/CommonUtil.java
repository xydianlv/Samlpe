package com.example.wyyu.gitsamlpe.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.util.http.HttpGet;
import com.example.wyyu.gitsamlpe.util.http.IHttpCallBack;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2017/12/28.
 **/

public class CommonUtil {

    public static String getFileDateTime(long changeTime) {
        Date date = new Date(changeTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    public static String formatFileSize(Context context, long fileS) {
        return Formatter.formatFileSize(context, fileS).replace("", "");
    }

    // 判读某一组权限是否都有注册
    public static boolean lackPermission(Context context, String... permissionArray) {

        for (String permission : permissionArray) {
            if (lackPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    // 判读是否缺少某权限
    private static boolean lackPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_DENIED;
    }

    // 打开软键盘
    public static void showSoftInput(Context context, View view) {

        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm == null) return;

        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    // 关闭软键盘
    public static void hideSoftInput(Context context, View view) {

        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm == null) return;

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // 获取图片宽高
    public static BitmapFactory.Options getImageValue(int imageId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Resources.getSystem(), imageId, options);
        return options;
    }

    public static boolean savePictureFromIntentToFile(Intent data, ContentResolver contentResolver, int maxSideLen, File saveFile) {
        Uri selectedImage = data.getData();
        String path = CommonUtil.getPicturePathFromUri(selectedImage, contentResolver);
        if (null != path) {
            return compressPicture(new File(path), saveFile, 80, maxSideLen);
        }

        Bitmap bitmap = null;
        try {
            ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(selectedImage, "r");
            bitmap = CommonUtil.getPhotoFileBitmap(pfd.getFileDescriptor(), maxSideLen);
        } catch (Throwable e) {
            return false;
        }

        return saveBitmap2file(bitmap, saveFile, Bitmap.CompressFormat.JPEG, 80);
    }

    public static boolean compressPicture(File srcFile, File destFile, int quality, int maxSideLength) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (maxSideLength > 0) {
            options.inJustDecodeBounds = true;
            decodeFileUnthrow(srcFile.getPath(), options);
            ULog.show("src width: " + options.outWidth + ", height: " + options.outHeight);

            options.inJustDecodeBounds = false;
            options.inSampleSize = Math.max(options.outWidth / maxSideLength, options.outHeight / maxSideLength);
            ULog.show("inSampleSize: " + options.inSampleSize);
        }

        Bitmap bmp = decodeFileUnthrow(srcFile.getPath(), options);
        if (null == bmp) {
            ULog.show("decodeFile failed");
            return false;
        }

        bmp = rotateAndScale(bmp, getPictureDegree(srcFile.getPath()), maxSideLength, true);

        boolean ret = CommonUtil.saveBitmap2file(bmp, destFile, Bitmap.CompressFormat.JPEG, quality);
        ULog.show("dst width: " + bmp.getWidth() + ", height: " + bmp.getHeight() + ", size: " + destFile.length());
        bmp.recycle();

        return ret;
    }

    public static Bitmap decodeFileUnthrow(String pathName, BitmapFactory.Options opts) {
        try {
            return BitmapFactory.decodeFile(pathName, opts);
        } catch (Throwable e) {
            ULog.show(e.toString());
        }

        return null;
    }

    public static int getPictureDegree(String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            return getExifDegree(exif);
        } catch (Exception e) {
        }

        return 0;
    }

    public static int getExifDegree(ExifInterface exif) {
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        if (orientation != -1) {
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        }
        return 0;
    }

    public static boolean saveBitmap2file(Bitmap bmp, File file, Bitmap.CompressFormat format, int quality) {
        if (null == bmp) {
            return false;
        }
        if (file.isFile())
            file.delete();
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            return false;
        }

        return bmp.compress(format, quality, stream);
    }

    public static String getPicturePathFromUri(Uri uri, ContentResolver contentResolver) {
        if (null == uri) {
            return null;
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA};
        String picturePath = null;
        Cursor cursor = null;

        try {
            cursor = contentResolver.query(uri, filePathColumn, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                }
            }
        } catch (Throwable e) {
        }

        if (null != cursor) {
            cursor.close();
        }

        return picturePath;
    }

    public static Bitmap getPhotoFileBitmap(FileDescriptor fd, float maxSideLen) {
        if (null == fd) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = (int) Math.max(options.outWidth / maxSideLen, options.outHeight / maxSideLen);
        Bitmap bmp = BitmapFactory.decodeFileDescriptor(fd, null, options);
        if (null == bmp)
            return null;

        return rotateAndScale(bmp, 0, maxSideLen, true);
    }

    public static Bitmap rotateAndScale(Bitmap b, int degrees, float maxSideLen, boolean recycle) {
        if (null == b) {
            return b;
        }

        boolean modify = false;
        Matrix m = new Matrix();
        if (degrees != 0) {
            m.setRotate(degrees);
            modify = true;
        }

        float scale = Math.min(maxSideLen / b.getWidth(), maxSideLen / b.getHeight());
        if (scale > 0 && scale < 0.99) {
            m.postScale(scale, scale);
            modify = true;
        }
        //ZLog.i("degrees: " + degrees + ", scale: " + scale);

        if (modify) {
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (null != b2 && b != b2) {
                    if (recycle) {
                        b.recycle();
                    }
                    b = b2;
                }
            } catch (OutOfMemoryError e) {
            }
        }
        return b;
    }
}
