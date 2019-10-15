package com.example.wyyu.gitsamlpe.test.litho.common.component;

import android.view.WindowManager;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.test.AbsComponent;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.widget.Card;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-10-14.
 **/

public class ComponentPercent extends AbsComponent<Object> {

    public ComponentPercent() {
        super("ComponentPercent");
    }

    @Override public Component createLayout(ComponentContext context, Object data) {
        return Column.create(context)
            //.child(loadPFromRow(context))
            .child(loadPFromCard(context))
            //.child(loadPFromCardPercent(context))
            //.child(loadPFromCardBg(context))
            //.child(loadPFromCardBgTwo(context))
            .child(loadPFromCardBgThree(context))
            //.child(loadStrokeFromCard(context))
            //.child(loadStrokeFromCardTwo(context))
            //.child(loadStrokeFromCardThree(context))
            //.child(loadStrokeFromProgress(context))
            .build();
    }

    private Component loadPFromRow(ComponentContext context) {
        return Row.create(context)
            .widthDip(WindowManager.LayoutParams.MATCH_PARENT)
            .backgroundColor(0xff1ed1ff)
            .heightDip(32.0f)
            .build();
    }

    private Component loadPFromCard(ComponentContext context) {
        return Card.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .content(Row.create(context)
                .widthDip(120.0f)
                .heightDip(32.0f)
                .backgroundColor(0xff1ed1ff)
                .build())
            .cardBackgroundColor(0xfff16d7a)
            .cornerRadiusDip(12.0f)
            .shadowEndColor(0x00000000)
            .shadowStartColor(0x00000000)
            .build();
    }

    private Component loadPFromCardPercent(ComponentContext context) {
        return Card.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .content(Row.create(context)
                .heightDip(32.0f)
                .child(Row.create(context).widthPercent(30).backgroundColor(0xfff16d7a).build())
                .child(Row.create(context).widthPercent(70).backgroundColor(0xff1ed1ff).build())
                .build())
            .cornerRadiusDip(6.0f)
            .shadowEndColor(0x00000000)
            .shadowStartColor(0x00000000)
            .build();
    }

    private Component loadPFromCardBg(ComponentContext context) {
        return Card.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .content(Row.create(context)
                .paddingDip(YogaEdge.ALL, 1.0f)
                .marginDip(YogaEdge.ALL, 1.0f)
                .heightDip(32.0f)
                .child(Row.create(context).widthPercent(30).backgroundColor(0xfff16d7a).build())
                .child(Row.create(context).widthPercent(70).backgroundColor(0xff1ed1ff).build())
                .backgroundRes(R.drawable.bg_litho_percent)
                .build())
            .cornerRadiusDip(6.0f)
            .shadowEndColor(0x00000000)
            .shadowStartColor(0x00000000)
            .build();
    }

    private Component loadPFromCardBgTwo(ComponentContext context) {
        return Row.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .heightDip(32.0f)
            .child(Card.create(context)
                .content(Row.create(context)
                    .widthDip(240.0f)
                    .heightDip(24.0f)
                    .backgroundColor(0xff1ed1ff)
                    .build())
                .cardBackgroundColor(0xff0000ff)
                .shadowStartColor(0x00000000)
                .shadowEndColor(0x00000000)
                .cornerRadiusDip(6.0f)
                .build())
            .backgroundRes(R.drawable.bg_litho_percent)
            .build();
    }

    private Component loadPFromCardBgThree(ComponentContext context) {
        return Row.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .heightDip(32.0f)
            .child(Row.create(context)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 1.0f)
                .positionDip(YogaEdge.VERTICAL, 1.0f)
                .widthPercent(30)
                .backgroundRes(R.drawable.bg_litho_percent_core)
                .build())
            .backgroundRes(R.drawable.bg_litho_percent)
            .build();
    }

    private Component loadStrokeFromCard(ComponentContext context) {
        return Row.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .child(Card.create(context)
                .content(Row.create(context)
                    .widthDip(64.0f)
                    .heightDip(64.0f)
                    .backgroundColor(0xff1ed1ff)
                    .build())
                .cornerRadiusDip(32.0f)
                .shadowEndColor(0x00000000)
                .shadowStartColor(0x00000000)
                .build())
            .child(Card.create(context)
                .content(Row.create(context)
                    .widthDip(62.0f)
                    .heightDip(62.0f)
                    .backgroundColor(0xfff16d7a)
                    .build())
                .cornerRadiusDip(31.0f)
                .shadowEndColor(0x00000000)
                .shadowStartColor(0x00000000)
                .build())
            .build();
    }

    //private Component loadStrokeFromProgress(ComponentContext context) {
    //    return Progress.create(context)
    //        .marginDip(YogaEdge.TOP, 12)
    //        .widthDip(64.0f)
    //        .heightDip(64.0f)
    //        .color(0xfff16d7a)
    //        .build();
    //}

    private Component loadStrokeFromCardTwo(ComponentContext context) {
        return Card.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .content(Row.create(context)
                .heightDip(32.0f)
                .child(Row.create(context)
                    .positionType(YogaPositionType.ABSOLUTE)
                    .positionDip(YogaEdge.HORIZONTAL, 0.0f)
                    .positionDip(YogaEdge.VERTICAL, 0.0f)
                    .backgroundColor(0xfff16d7a)
                    .build())
                .child(Row.create(context)
                    .positionType(YogaPositionType.ABSOLUTE)
                    .positionDip(YogaEdge.HORIZONTAL, 1.0f)
                    .positionDip(YogaEdge.VERTICAL, 1.0f)
                    .backgroundColor(0xff1ed1ff)
                    .build())
                .backgroundRes(R.drawable.bg_litho_percent)
                .build())
            .shadowStartColor(0x00000000)
            .shadowEndColor(0x00000000)
            .cornerRadiusDip(6.0f)
            .build();
    }

    private Component loadStrokeFromCardThree(ComponentContext context) {
        return Row.create(context)
            .marginDip(YogaEdge.TOP, 12.0f)
            .widthDip(WindowManager.LayoutParams.MATCH_PARENT)
            .heightDip(32.0f)
            .child(Card.create(context)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.HORIZONTAL, 0.0f)
                .positionDip(YogaEdge.VERTICAL, 0.0f)
                .content(Row.create(context).heightDip(32.0f).backgroundColor(0xfff16d7a).build())
                .shadowStartColor(0x00000000)
                .shadowEndColor(0x00000000)
                .cardBackgroundColor(0x00000000)
                .cornerRadiusDip(6.0f)
                .build())
            .child(Card.create(context)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.HORIZONTAL, 1.0f)
                .positionDip(YogaEdge.VERTICAL, 1.0f)
                .content(Row.create(context).heightDip(32.0f).backgroundColor(0xff1ed1ff).build())
                .shadowStartColor(0x00000000)
                .shadowEndColor(0x00000000)
                //.cardBackgroundColor(0x00000000)
                .clippingColor(0xfff16d7a)
                .cornerRadiusDip(6.0f)
                .build())
            .build();
    }
}
