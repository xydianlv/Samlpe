package com.example.matisse.internal.entity;

import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StyleRes;
import com.example.matisse.MimeType;
import com.example.matisse.R;
import com.example.matisse.ResultItem;
import com.example.matisse.filter.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class SelectionSpec implements Parcelable {

    public Set<MimeType> mimeTypeSet = MimeType.ofNormal();
    public boolean mediaTypeExclusive;
    public boolean showSingleMediaType;
    @StyleRes public int themeId;
    public int orientation;
    public boolean isNightMode;
    public String thumbnailDir;
    public boolean countable;
    public int maxSelectable;
    public List<Filter> filters;
    public boolean capture;
    public int spanCount;
    public int gridExpectedSize;
    public float thumbnailScale;
    public List<ResultItem> selectedItems;

    public int limitVideo;

    private SelectionSpec() {
    }

    protected SelectionSpec(Parcel in) {
        mediaTypeExclusive = in.readByte() != 0;
        showSingleMediaType = in.readByte() != 0;
        themeId = in.readInt();
        orientation = in.readInt();
        isNightMode = in.readByte() != 0;
        thumbnailDir = in.readString();
        countable = in.readByte() != 0;
        maxSelectable = in.readInt();
        capture = in.readByte() != 0;
        spanCount = in.readInt();
        gridExpectedSize = in.readInt();
        thumbnailScale = in.readFloat();
        selectedItems = in.createTypedArrayList(ResultItem.CREATOR);

        limitVideo = in.readInt();
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mediaTypeExclusive ? 1 : 0));
        dest.writeByte((byte) (showSingleMediaType ? 1 : 0));
        dest.writeInt(themeId);
        dest.writeInt(orientation);
        dest.writeByte((byte) (isNightMode ? 1 : 0));
        dest.writeString(thumbnailDir);
        dest.writeByte((byte) (countable ? 1 : 0));
        dest.writeInt(maxSelectable);
        dest.writeByte((byte) (capture ? 1 : 0));
        dest.writeInt(spanCount);
        dest.writeInt(gridExpectedSize);
        dest.writeFloat(thumbnailScale);
        dest.writeTypedList(selectedItems);

        dest.writeInt(limitVideo);
    }

    @Override public int describeContents() {
        return 0;
    }

    public static final Creator<SelectionSpec> CREATOR = new Creator<SelectionSpec>() {
        @Override public SelectionSpec createFromParcel(Parcel in) {
            return new SelectionSpec(in);
        }

        @Override public SelectionSpec[] newArray(int size) {
            return new SelectionSpec[size];
        }
    };

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    public static void restoreSavedInstance(SelectionSpec spec) {
        InstanceHolder.INSTANCE = spec;
    }

    private void reset() {
        mimeTypeSet = null;
        mediaTypeExclusive = true;
        showSingleMediaType = false;
        themeId = isNightMode ? R.style.Matisse_Dracula : R.style.Matisse_Zhihu;
        isNightMode = false;
        thumbnailDir = null;
        orientation = 0;
        countable = false;
        maxSelectable = 1;
        filters = null;
        capture = false;
        spanCount = 3;
        gridExpectedSize = 0;
        thumbnailScale = 0.5f;
        if (selectedItems != null) {
            selectedItems = null;
        }

        limitVideo = 0;
    }

    public boolean singleSelectionModeEnabled() {
        return !countable && maxSelectable == 1;
    }

    public boolean needOrientationRestriction() {
        return orientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public boolean onlyShowImages() {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypeSet);
    }

    public boolean onlyShowVideos() {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypeSet);
    }

    private static final class InstanceHolder {
        private static SelectionSpec INSTANCE = new SelectionSpec();
    }

    public ArrayList<Item> loadItemList() {
        if (selectedItems == null) {
            return null;
        }
        ArrayList<Item> itemArrayList = new ArrayList<>(selectedItems.size());
        for (ResultItem tempItem : selectedItems) {
            if (tempItem != null) {
                itemArrayList.add(
                    new Item(tempItem.id, tempItem.mimeType, tempItem.path, 0, tempItem.width,
                        tempItem.height, 0, 0));
            }
        }
        return itemArrayList;
    }
}
