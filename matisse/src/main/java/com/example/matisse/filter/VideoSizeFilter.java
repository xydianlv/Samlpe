package com.example.matisse.filter;

import android.content.Context;
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

public class VideoSizeFilter extends Filter {
    private int mMaxSize;
    private int mDuration;

    public VideoSizeFilter(int size, int duration) {
        mMaxSize = size;
        mDuration = duration;
    }

    @Override protected Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            addAll(MimeType.ofVideo());
        }};
    }

    @Override public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item)) return null;

        if (item.duration > mDuration) {
            return new IncapableCause(IncapableCause.TOAST,
                context.getString(R.string.error_video_length,
                    String.valueOf(PhotoMetadataUtils.getLengthInMinute(mDuration))));
        } else if (item.size > mMaxSize) {
            return new IncapableCause(IncapableCause.TOAST,
                context.getString(R.string.error_video_size,
                    String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
        }
        return null;
    }
}
