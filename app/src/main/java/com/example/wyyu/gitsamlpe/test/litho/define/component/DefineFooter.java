package com.example.wyyu.gitsamlpe.test.litho.define.component;

import android.util.Log;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class DefineFooter extends Component {

    private DefineDataContainer defineDataContainer;
    //private DefineFooter footerCopy;

    private DefineFooter() {
        super("DefineFooter");

        defineDataContainer = new DefineDataContainer();
        //footerCopy = null;
    }

    @Override protected StateContainer getStateContainer() {
        return defineDataContainer;
    }

    @Override public DefineFooter makeShallowCopy() {
        //if (footerCopy != null) {
        //    return footerCopy;
        //}

        DefineFooter footerCopy = (DefineFooter) super.makeShallowCopy();
        footerCopy.defineDataContainer = defineDataContainer;
        return footerCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {

        int index = defineDataContainer == null || defineDataContainer.defineData == null ? -1
            : defineDataContainer.defineData.index;

        Log.e("DefineFooterTest", "onCreateLayout -> index : " + index);

        int countLike = defineDataContainer == null || defineDataContainer.defineData == null ? -47
            : defineDataContainer.defineData.countLike;
        int countReview =
            defineDataContainer == null || defineDataContainer.defineData == null ? -315
                : defineDataContainer.defineData.countReview;

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

    @Override protected void transferState(StateContainer _prevStateContainer,
        StateContainer _nextStateContainer) {
        DefineDataContainer prevStateContainer = (DefineDataContainer) _prevStateContainer;
        DefineDataContainer nextStateContainer = (DefineDataContainer) _nextStateContainer;
        nextStateContainer.defineData = prevStateContainer.defineData;
    }

    public void setDefineData(DefineData defineData) {

        Log.e("DefineFooterTest", "setDefineData -> index : " + defineData.index);

        defineDataContainer.defineData = defineData;
        //DefineDataStateUpdate stateUpdate = new DefineDataStateUpdate();
        //stateUpdate.defineData = defineData;

        //ComponentContext treeContext = footerCopy != null ? footerCopy.getScopedContext() : null;
        //
        //if (treeContext != null) {
        //    treeContext.updateStateAsync(new DefineDataStateUpdate(), "");
        //}
    }

    private static class DefineDataContainer implements StateContainer {
        @State DefineData defineData;
    }

    private static class DefineDataStateUpdate implements ComponentLifecycle.StateUpdate {

        private DefineData defineData;

        DefineDataStateUpdate() {

        }

        @Override public void updateState(StateContainer _stateContainer) {
            //DefineDataContainer stateContainer = (DefineDataContainer) _stateContainer;
            //StateValue<DefineData> value = new StateValue<>();
            //value.set(defineData);
            //stateContainer.defineData = value.get();
        }
    }

    public static class Builder extends Component.Builder<Builder> {

        ComponentContext componentContext;
        DefineFooter defineFooter;

        private void init(ComponentContext context, DefineFooter footer) {
            super.init(context, 0, 0, footer);
            componentContext = context;
            defineFooter = footer;
        }

        @Override public Builder getThis() {
            return this;
        }

        @Override public DefineFooter build() {
            DefineFooter footer = defineFooter;
            release();
            return footer;
        }

        @Override protected void release() {
            super.release();

            componentContext = null;
            defineFooter = null;
        }
    }

    public static Builder create(ComponentContext context) {
        final Builder builder = new Builder();
        DefineFooter instance = new DefineFooter();
        builder.init(context, instance);
        return builder;
    }
}