package com.example.wyyu.gitsamlpe.test.litho.define.component;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-10-11.
 **/

public class FooterTest extends Component {

    private DefineData defineData;

    FooterTest() {
        super("FooterTest");
        defineData = null;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {
        setScopedContext(componentContext);

        int countLike = defineData == null ? -47 : defineData.countLike;
        int countReview = defineData == null ? -315 : defineData.countReview;

        return Row.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.BOTTOM, 12)
            .paddingDip(YogaEdge.TOP, 14)
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .widthDip(18)
                .heightDip(18)
                .build())
            .child(Text.create(componentContext)
                .text(String.valueOf(countLike))
                .paddingDip(YogaEdge.TOP, 1)
                .marginDip(YogaEdge.LEFT, 12)
                .textColor(0xff333333)
                .textSizeDip(13)
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .marginDip(YogaEdge.LEFT, 32)
                .widthDip(18)
                .heightDip(18)
                .build())
            .child(Text.create(componentContext)
                .text(String.valueOf(countReview))
                .paddingDip(YogaEdge.TOP, 1)
                .marginDip(YogaEdge.LEFT, 12)
                .textColor(0xff333333)
                .textSizeDip(13)
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 14)
                .positionDip(YogaEdge.TOP, 14)
                .widthDip(18)
                .heightDip(18)
                .build())
            .build();
    }

    @Override public boolean hasState() {
        return true;
    }

    void setDefineData(DefineData defineData) {
        this.defineData = defineData;

        ComponentContext context = getScopedContext();
        if (context != null) {
            context.updateStateAsync(new FooterUpdate(), "SimpleInfo.refreshInfo");
        }
    }

    private static final class FooterUpdate implements ComponentLifecycle.StateUpdate {

        @Override public void updateState(StateContainer stateContainer) {

        }
    }
}
