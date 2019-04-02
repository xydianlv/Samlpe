package com.example.matisse;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class ResultItem implements Parcelable {

    public long id;
    public int width;
    public int height;
    public String path;
    public String mimeType;
    public String thumbnailPath;

    // Item 为视频时，表示时长信息
    public long duration;

    public ResultItem(long id, String path, String mimeType, String thumbnailPath, int width,
        int height) {
        this.id = id;
        this.path = path;
        this.mimeType = mimeType;
        this.thumbnailPath = thumbnailPath;
        this.width = width;
        this.height = height;
    }

    public ResultItem(ResultItem resultItem) {
        if (resultItem == null) {
            return;
        }
        this.id = resultItem.id;
        this.path = resultItem.path;
        this.mimeType = resultItem.mimeType;
        this.thumbnailPath = resultItem.thumbnailPath;
        this.width = resultItem.width;
        this.height = resultItem.height;
        this.duration = resultItem.duration;
    }

    public ResultItem() {

    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.path);
        dest.writeString(this.mimeType);
        dest.writeString(this.thumbnailPath);
        dest.writeLong(duration);
    }

    protected ResultItem(Parcel in) {
        this.id = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.path = in.readString();
        this.mimeType = in.readString();
        this.thumbnailPath = in.readString();
        this.duration = in.readLong();
    }

    public boolean isVideo() {
        return !TextUtils.isEmpty(mimeType) && (mimeType.equals(MimeType.MPEG.toString())
            || mimeType.equals(MimeType.MP4.toString())
            || mimeType.equals(MimeType.QUICKTIME.toString())
            || mimeType.equals(MimeType.THREEGPP.toString())
            || mimeType.equals(MimeType.THREEGPP2.toString())
            || mimeType.equals(MimeType.MKV.toString())
            || mimeType.equals(MimeType.WEBM.toString())
            || mimeType.equals(MimeType.TS.toString())
            || mimeType.equals(MimeType.AVI.toString()));
    }

    public static final Creator<ResultItem> CREATOR = new Creator<ResultItem>() {
        @Override public ResultItem createFromParcel(Parcel source) {
            return new ResultItem(source);
        }

        @Override public ResultItem[] newArray(int size) {
            return new ResultItem[size];
        }
    };
}
