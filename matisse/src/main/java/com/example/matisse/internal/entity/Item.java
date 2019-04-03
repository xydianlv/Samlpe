package com.example.matisse.internal.entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.example.matisse.MimeType;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {

        @NonNull @Override public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public static long ITEM_ID_CAPTURE = -1;
    public static String ITEM_DISPLAY_NAME_CAPTURE = "Capture";
    public long id;
    public String mimeType;
    public Uri uri;
    public String path;
    public int width;
    public int height;
    public long size;
    public long duration; // only for video, in ms
    public long time;

    public String videoThumbnail;
    //图片选择界面该图片在选中列表中的序列
    public int visibleIndex;

    public Item(long id, String mimeType, String data, long size, int width, int height,
        long duration, long time) {
        this.id = id;
        this.mimeType = mimeType;
        Uri contentUri;
        if (isImage()) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if (isVideo()) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
            // ?
            contentUri = MediaStore.Files.getContentUri("external");
        }
        this.uri = ContentUris.withAppendedId(contentUri, id);
        this.path = data;
        this.size = size;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.time = time;
    }

    private Item(Parcel source) {
        id = source.readLong();
        mimeType = source.readString();
        uri = source.readParcelable(Uri.class.getClassLoader());
        path = source.readString();
        size = source.readLong();
        width = source.readInt();
        height = source.readInt();
        duration = source.readLong();
        time = source.readLong();
    }

    public static Item valueOf(Cursor cursor) {
        return new Item(cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)),
            cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)),
            cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
            cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)),
            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH)),
            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT)),
            cursor.getLong(cursor.getColumnIndex("duration")),
            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mimeType);
        dest.writeParcelable(uri, 0);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeLong(duration);
        dest.writeLong(time);
    }

    public Uri getContentUri() {
        return uri;
    }

    public boolean isCapture() {
        return id == ITEM_ID_CAPTURE;
    }

    public boolean isImage() {
        try {
            if (TextUtils.isEmpty(mimeType)) {
                return true;
            }
            return mimeType.equals(MimeType.JPEG.toString())
                || mimeType.equals(MimeType.PNG.toString())
                || mimeType.equals(MimeType.GIF.toString())
                || mimeType.equals(MimeType.BMP.toString())
                || mimeType.equals(MimeType.WEBP.toString());
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public boolean isGif() {
        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }
        return mimeType.equals(MimeType.GIF.toString());
    }

    public boolean isVideo() {
        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }
        return mimeType.equals(MimeType.MPEG.toString())
            || mimeType.equals(MimeType.MP4.toString())
            || mimeType.equals(MimeType.QUICKTIME.toString())
            || mimeType.equals(MimeType.THREEGPP.toString())
            || mimeType.equals(MimeType.THREEGPP2.toString())
            || mimeType.equals(MimeType.MKV.toString())
            || mimeType.equals(MimeType.WEBM.toString())
            || mimeType.equals(MimeType.TS.toString())
            || mimeType.equals(MimeType.AVI.toString());
    }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }

        Item other = (Item) obj;
        return uri.equals(other.uri) || id == other.id;
    }

    @Override public int hashCode() {
        int result = 1;
        result = 31 * result + Long.valueOf(id).hashCode();
        if (!TextUtils.isEmpty(mimeType)) {
            result = 31 * result + mimeType.hashCode();
        }
        result = 31 * result + uri.hashCode();
        result = 31 * result + Long.valueOf(size).hashCode();
        result = 31 * result + Long.valueOf(duration).hashCode();
        return result;
    }
}