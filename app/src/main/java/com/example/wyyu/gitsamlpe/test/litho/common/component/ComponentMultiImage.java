package com.example.wyyu.gitsamlpe.test.litho.common.component;

import android.net.Uri;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponentCacheView;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.Row;
import com.facebook.litho.fresco.FrescoImage;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-15.
 **/

public class ComponentMultiImage extends AbsComponentCacheView<Object> {

    private static final String IMG_A =
        "https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/b294a4391e0c41bea69aa10e83953fe3.jpg";
    private static final String IMG_B =
        "https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/ee58281fb9524f79b3bbcc8d1311c9c4.jpg";
    private static final String IMG_C =
        "https://ssyerv1.oss-cn-hangzhou.aliyuncs.com/picture/def4418e778a49a6bf0c2102f4b25590.jpg";

    private static final float MULTI_SIZE =
        (float) (UIUtils.getScreenWidth() - UIUtils.dpToPx(14 + 2 + 2 + 12)) / 3;

    public ComponentMultiImage() {
        super("ComponentMultiImage");
    }

    @Override public Component createLayout(ComponentContext context, Object data) {
        //return Row.create(context)
        //    .marginDip(YogaEdge.LEFT, 14.0f)
        //    .marginDip(YogaEdge.RIGHT, 12.0f)
        //    .child(FrescoImage.create(context)
        //        .widthDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .heightDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .roundingParams(loadAvatarRoundParams(0))
        //        .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_A)).build())
        //        .placeholderImageRes(R.mipmap.image_test_1)
        //        .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .build())
        //    .child(FrescoImage.create(context)
        //        .widthDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .heightDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .marginDip(YogaEdge.LEFT, 2.0f)
        //        .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .roundingParams(loadAvatarRoundParams(1))
        //        .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_B)).build())
        //        .placeholderImageRes(R.mipmap.image_test_4)
        //        .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .build())
        //    .child(FrescoImage.create(context)
        //        .widthDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .heightDip(UIUtils.pxToDp(MULTI_SIZE))
        //        .marginDip(YogaEdge.LEFT, 2.0f)
        //        .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .roundingParams(loadAvatarRoundParams(2))
        //        .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_C)).build())
        //        .placeholderImageRes(R.mipmap.image_test_3)
        //        .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
        //        .clickHandler(new EventHandler<>(this, 0, new Object[] { "image_test_3" }))
        //        .build())
        //    .build();

        return Row.create(context)
            .marginDip(YogaEdge.LEFT, 14.0f)
            .marginDip(YogaEdge.RIGHT, 12.0f)
            .child(FrescoImage.create(context)
                .widthPercent(33)
                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .roundingParams(loadAvatarRoundParams(0))
                .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_A)).build())
                .placeholderImageRes(R.mipmap.image_test_1)
                .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build())
            //.child(Row.create(context)
            //    .widthDip(2.0f)
            //    .heightDip(0.0f)
            //    .backgroundColor(0x00000000)
            //    .build())
            .child(FrescoImage.create(context)
                .widthPercent(33)
                .marginDip(YogaEdge.LEFT, 2.0f)
                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .roundingParams(loadAvatarRoundParams(1))
                .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_B)).build())
                .placeholderImageRes(R.mipmap.image_test_4)
                .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build())
            //.child(Row.create(context)
            //    .widthDip(2.0f)
            //    .heightDip(0.0f)
            //    .backgroundColor(0x00000000)
            //    .build())
            .child(FrescoImage.create(context)
                .widthPercent(33)
                .marginDip(YogaEdge.LEFT, 2.0f)
                .actualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .roundingParams(loadAvatarRoundParams(2))
                .controller(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(IMG_C)).build())
                .placeholderImageRes(R.mipmap.image_test_3)
                .placeholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .clickHandler(new EventHandler<>(this, 0, new Object[] { "image_test_3" }))
                .build())
            .build();
    }

    private RoundingParams loadAvatarRoundParams(int type) {
        RoundingParams roundingParams = new RoundingParams();

        float bottomRight = type == 2 ? 12.0f : 0.0f;
        float topLeft = type == 0 ? 12.0f : 0.0f;

        roundingParams.setCornersRadii(topLeft, bottomRight, bottomRight, topLeft);

        //roundingParams.setCornersRadius(12.0f);
        //roundingParams.setRoundAsCircle(true);

        return roundingParams;
    }

    @Override
    public Object dispatchOnEvent(final EventHandler eventHandler, final Object eventState) {
        if (eventHandler == null) {
            return null;
        }
        if (eventHandler.id == 0) {
            Object log = eventHandler.params == null ? null : eventHandler.params[0];

            int[] location = new int[2];
            contentView.getLocationOnScreen(location);

            ULog.show(log == null ? "null" : (String) log);
        }
        return null;
    }
}
