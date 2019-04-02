package com.example.matisse.filter;

import android.content.Context;
import android.graphics.Point;
import com.example.matisse.MimeType;
import com.example.matisse.R;
import com.example.matisse.internal.utils.PhotoMetadataUtils;
import com.example.matisse.internal.entity.IncapableCause;
import com.example.matisse.internal.entity.Item;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wyyu on 2019/4/2.
 **/
public class GifSizeFilter extends Filter {

    private int mMinWidth;
    private int mMinHeight;
    private int mMaxSize;

    public GifSizeFilter(int minWidth, int minHeight, int maxSizeInBytes) {
        mMinWidth = minWidth;
        mMinHeight = minHeight;
        mMaxSize = maxSizeInBytes;
    }

    @Override
    public Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            add(MimeType.GIF);
        }};
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
        if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            return new IncapableCause(context.getString(R.string.error_gif, mMinWidth,
                String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
        }
        return null;
    }

}
