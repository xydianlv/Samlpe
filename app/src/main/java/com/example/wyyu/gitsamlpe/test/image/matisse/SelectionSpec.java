package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.content.pm.ActivityInfo;
import android.support.annotation.StyleRes;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import java.util.List;
import java.util.Set;

/**
 * Created by wyyu on 2018/8/20.
 **/

public class SelectionSpec {

    public Set<MimeType> mimeTypeSet;
    @StyleRes public int themeId;
    public int orientation;
    public boolean countable;
    public int maxSelectable;
    public List<Filter> filters;
    public boolean capture;
    public CaptureStrategy captureStrategy;
    public int spanCount;
    public int gridExpectedSize;
    public float thumbnailScale;
    public ImageEngine imageEngine = new FrescoEngine();

    private SelectionSpec() {
    }

    public static SelectionSpec getInstance() {
        return SelectionSpec.InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    void reset() {
        mimeTypeSet = null;
        themeId = 0;
        orientation = 0;
        countable = false;
        maxSelectable = 0;
        filters = null;
        capture = false;
        captureStrategy = null;
        spanCount = 0;
        gridExpectedSize = 0;
        thumbnailScale = 0.0f;
        imageEngine = null;
    }

    public boolean needOrientationRestriction() {
        return orientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    private static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();
    }
}
