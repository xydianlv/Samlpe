package com.example.wyyu.gitsamlpe.test.image.matisse;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import com.example.wyyu.gitsamlpe.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by wyyu on 2018/7/23.
 **/

public class MatisseHelper {

    private static final Set<MimeType> mimeTypes =
        EnumSet.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF);
    private static final int NUMBER_NINE = 9;

    public static void openOnlySelectImageWithLimit(Activity activity, int requestCode, int count) {
        imageSelect(activity, requestCode, count);
    }

    public static void openOnlySelectImage(Activity activity, int requestCode) {
        imageSelect(activity, requestCode, NUMBER_NINE);
    }

    private static void imageSelect(Activity activity, int requestCode, int count) {
        Matisse.from(activity)
            .choose(mimeTypes)
            .countable(true)
            .capture(true)
            .captureStrategy(new CaptureStrategy(false, ""))
            .maxSelectable(count)
            .gridExpectedSize(
                activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.6f)
            .imageEngine(new FrescoEngine())
            .forResult(requestCode);
    }
}
