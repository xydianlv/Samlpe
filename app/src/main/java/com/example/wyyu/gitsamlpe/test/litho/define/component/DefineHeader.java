package com.example.wyyu.gitsamlpe.test.litho.define.component;

import android.util.Log;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Column;
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

public final class DefineHeader extends Component {

    private DefineDataContainer defineDataContainer;

    private DefineHeader() {
        super("DefineHeader");
        defineDataContainer = new DefineDataContainer();
    }

    @Override protected StateContainer getStateContainer() {
        return defineDataContainer;
    }

    @Override public DefineHeader makeShallowCopy() {
        DefineHeader headerCopy = (DefineHeader) super.makeShallowCopy();
        headerCopy.defineDataContainer = new DefineDataContainer();
        return headerCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {

        DefineData defineData = defineDataContainer == null ? null : defineDataContainer.defineData;

        int iconId = defineData == null ? R.mipmap.multi_image_9 : defineData.iconId;
        String title = defineData == null ? "Title" : defineData.title;
        String info = defineData == null ? "info" : String.valueOf(defineData.index);

        return Row.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.TOP, 12)
            .child(Image.create(componentContext)
                .drawableRes(iconId)
                .widthDip(32)
                .heightDip(32)
                .scaleType(ImageView.ScaleType.CENTER_CROP))
            .child(Column.create(componentContext)
                .marginDip(YogaEdge.LEFT, 8)
                .child(Text.create(componentContext)
                    .text(title)
                    .textSizeDip(14)
                    .textColor(0xff333333)
                    .build())
                .child(Text.create(componentContext)
                    .text(info)
                    .textSizeDip(11)
                    .textColor(0xff666666)
                    .build())
                .build())
            .child(Image.create(componentContext)
                .drawableRes(R.mipmap.icon_test)
                .positionType(YogaPositionType.ABSOLUTE)
                .positionDip(YogaEdge.RIGHT, 14)
                .positionDip(YogaEdge.TOP, 16)
                .widthDip(14)
                .heightDip(14))
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

        Log.e("DefineHeaderTest",
            "\nsetDefineData -> hasCode : " + hashCode() + "  index : " + defineData.index);

        //this.defineDataContainer.defineData = defineData;
        ////this.defineData = defineData;
        //
        //ComponentContext treeContext = getScopedContext();
        //if (treeContext != null) {
        //    treeContext.updateStateSync(new DefineDataStateUpdate(), "");
        //}

        DefineDataStateUpdate stateUpdate = new DefineDataStateUpdate();
        stateUpdate.defineData = defineData;

        ComponentContext treeContext = getScopedContext();

        if (treeContext != null) {
            treeContext.updateStateAsync(new DefineDataStateUpdate(), "setHeaderData");
        }
    }

    private static class DefineDataContainer implements StateContainer {
        @State DefineData defineData;
    }

    private static class DefineDataStateUpdate implements ComponentLifecycle.StateUpdate {

        private DefineData defineData;

        DefineDataStateUpdate() {

        }

        @Override public void updateState(StateContainer _stateContainer) {
            DefineDataContainer stateContainer = (DefineDataContainer) _stateContainer;
            stateContainer.defineData = defineData;
        }
    }

    public static class Builder extends Component.Builder<Builder> {

        ComponentContext componentContext;
        DefineHeader defineHeader;

        private void init(ComponentContext context, DefineHeader header) {
            super.init(context, 0, 0, header);
            componentContext = context;
            defineHeader = header;
        }

        @Override public Builder getThis() {
            return this;
        }

        @Override public DefineHeader build() {
            DefineHeader header = defineHeader;
            release();
            return header;
        }

        @Override protected void release() {
            super.release();

            componentContext = null;
            defineHeader = null;
        }
    }

    public static Builder create(ComponentContext context) {
        final Builder builder = new Builder();
        DefineHeader instance = new DefineHeader();
        builder.init(context, instance);
        return builder;
    }
}
