package com.example.wyyu.gitsamlpe.test.video.local;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import com.zhihu.matisse.MimeType;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class ResultItem implements Parcelable {
    public static final Creator<ResultItem> CREATOR = new Creator<ResultItem>() {
        @Override @Nullable public ResultItem createFromParcel(Parcel source) {
            return new ResultItem(source);
        }

        @Override public ResultItem[] newArray(int size) {
            return new ResultItem[size];
        }
    };
    public static final long ITEM_ID_CAPTURE = -1;
    public static final String ITEM_DISPLAY_NAME_CAPTURE = "Capture";
    public final long id;
    public final String mimeType;
    public final Uri uri;
    public final long size;
    public final int width;
    public final int height;

    ResultItem(long id, String mimeType, long size, int width, int height) {
        this.id = id;
        this.mimeType = mimeType;
        this.uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        this.size = size;
        this.width = width;
        this.height = height;
    }

    ResultItem(Parcel source) {
        id = source.readLong();
        mimeType = source.readString();
        uri = source.readParcelable(Uri.class.getClassLoader());
        size = source.readLong();
        width = source.readInt();
        height = source.readInt();
    }

    public static ResultItem valueOf(Cursor cursor) {
        return new ResultItem(
            cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)),
            cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)),
            cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)),
            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH)),
            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT)));
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mimeType);
        dest.writeParcelable(uri, 0);
        dest.writeLong(size);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    public Uri getContentUri() {
        return uri;
    }

    public boolean isCapture() {
        return id == ITEM_ID_CAPTURE;
    }

    public boolean isGif() {
        return mimeType.equals(MimeType.GIF.toString());
    }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof ResultItem)) {
            return false;
        }

        ResultItem other = (ResultItem) obj;
        return other.uri.equals(uri);
    }

    @Override public int hashCode() {
        int result = 1;
        result = 31 * result + Long.valueOf(id).hashCode();
        result = 31 * result + mimeType.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + Long.valueOf(size).hashCode();
        result = 31 * result + Integer.valueOf(width).hashCode();
        result = 31 * result + Integer.valueOf(height).hashCode();
        return result;
    }
}