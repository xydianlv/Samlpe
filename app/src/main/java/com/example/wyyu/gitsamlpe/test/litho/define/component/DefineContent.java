package com.example.wyyu.gitsamlpe.test.litho.define.component;

import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineImage;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class DefineContent extends Component {

    private DefineDataContainer defineDataContainer;
    //private DefineContent contentCopy;

    private DefineContent() {
        super("DefineContent");

        defineDataContainer = new DefineDataContainer();
        //contentCopy = null;
    }

    @Override protected StateContainer getStateContainer() {
        return defineDataContainer;
    }

    @Override public boolean isEquivalentTo(Component other) {
        if (ComponentsConfiguration.useNewIsEquivalentToInLayoutSpec) {
            return super.isEquivalentTo(other);
        }
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DefineContent defineContent = (DefineContent) other;
        if (this.getId() == defineContent.getId()) {
            return true;
        }
        return defineDataContainer != null && defineDataContainer.equals(
            defineContent.defineDataContainer);
    }

    @Override public DefineContent makeShallowCopy() {
        //contentCopy = (DefineContent) super.makeShallowCopy();
        //contentCopy.defineDataContainer = new DefineDataContainer();
        //return contentCopy;
        DefineContent contentCopy = (DefineContent) super.makeShallowCopy();
        contentCopy.defineDataContainer = defineDataContainer;
        return contentCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {

        DefineImage defineImage =
            defineDataContainer == null || defineDataContainer.defineData == null ? null
                : defineDataContainer.defineData.imageBean;
        String content =
            defineDataContainer == null || defineDataContainer.defineData == null ? "content"
                : defineDataContainer.defineData.content;

        return Column.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14)
            .paddingDip(YogaEdge.TOP, 10)
            .child(Text.create(componentContext)
                .text(content)
                .textColor(0xff333333)
                .textSizeDip(16)
                .spacingMultiplier(1.3f)
                .maxLines(4)
                .ellipsize(TextUtils.TruncateAt.END)
                .build())
            .child(loadMultiImage(componentContext, defineImage))
            .build();
    }

    private Component loadMultiImage(ComponentContext componentContext, DefineImage defineImage) {
        if (defineImage == null) {
            return Column.create(componentContext).widthDip(0).heightDip(0).build();
        } else if (defineImage.height > defineImage.width) {
            return Image.create(componentContext)
                .paddingDip(YogaEdge.TOP, 12)
                .heightDip(232)
                .widthDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(defineImage.resId)
                .build();
        } else {
            return Image.create(componentContext)
                .paddingDip(YogaEdge.TOP, 12)
                .widthDip(232)
                .heightDip(WindowManager.LayoutParams.WRAP_CONTENT)
                .scaleType(ImageView.ScaleType.FIT_START)
                .drawableRes(defineImage.resId)
                .build();
        }
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
        defineDataContainer.defineData = defineData;

        //DefineDataStateUpdate stateUpdate = new DefineDataStateUpdate();
        //stateUpdate.defineData = defineData;
        //
        //ComponentContext treeContext = contentCopy != null ? contentCopy.getScopedContext() : null;
        //
        //if (treeContext != null) {
        //    treeContext.updateStateSync(stateUpdate, "");
        //}
    }

    private static class DefineDataContainer implements StateContainer {
        @State DefineData defineData;

        @Override public boolean equals(Object o) {
            if (!(o instanceof DefineDataContainer)) {
                return false;
            }
            DefineDataContainer stateContainer = (DefineDataContainer) o;
            if (stateContainer.hashCode() != hashCode()) {
                return false;
            }
            return defineData != null && defineData.equals(stateContainer.defineData);
        }
    }

    private static class DefineDataStateUpdate implements ComponentLifecycle.StateUpdate {

        private DefineData defineData;

        DefineDataStateUpdate() {

        }

        @Override public void updateState(StateContainer _stateContainer) {
            DefineDataContainer stateContainer = (DefineDataContainer) _stateContainer;
            StateValue<DefineData> value = new StateValue<>();
            value.set(defineData);
            stateContainer.defineData = value.get();
        }
    }

    public static class Builder extends Component.Builder<Builder> {

        ComponentContext componentContext;
        DefineContent defineContent;

        private void init(ComponentContext context, DefineContent content) {
            super.init(context, 0, 0, content);
            componentContext = context;
            defineContent = content;
        }

        @Override public Builder getThis() {
            return this;
        }

        @Override public DefineContent build() {
            DefineContent content = defineContent;
            release();
            return content;
        }

        @Override protected void release() {
            super.release();

            componentContext = null;
            defineContent = null;
        }
    }

    public static Builder create(ComponentContext context) {
        final Builder builder = new Builder();
        DefineContent instance = new DefineContent();
        builder.init(context, instance);
        return builder;
    }
}