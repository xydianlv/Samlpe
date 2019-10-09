package com.example.wyyu.gitsamlpe.test.litho.custom.component;

import android.text.TextUtils;
import com.example.wyyu.gitsamlpe.R;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;

/**
 * Created by wyyu on 2019-10-09.
 **/

public final class ComponentFoot extends Component {

    private LikeCountStateContainer likeCountStateContainer;
    private ComponentFoot footCopy;

    private ComponentFoot() {
        super("ComponentFoot");

        likeCountStateContainer = new LikeCountStateContainer();
        footCopy = null;
    }

    @Override protected StateContainer getStateContainer() {
        return likeCountStateContainer;
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
        ComponentFoot componentFoot = (ComponentFoot) other;
        if (this.getId() == componentFoot.getId()) {
            return true;
        }
        return likeCountStateContainer != null && likeCountStateContainer.equals(
            componentFoot.likeCountStateContainer);
    }

    @Override public ComponentFoot makeShallowCopy() {
        footCopy = (ComponentFoot) super.makeShallowCopy();
        footCopy.likeCountStateContainer = new LikeCountStateContainer();
        return footCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {
        return Row.create(componentContext)
            .paddingDip(YogaEdge.HORIZONTAL, 14.0f)
            .paddingDip(YogaEdge.VERTICAL, 12.0f)
            .child(Image.create(componentContext)
                .heightDip(18.0f)
                .widthDip(18.0f)
                .drawableRes(R.mipmap.icon_test)
                .build())
            .child(Text.create(componentContext)
                .ellipsize(TextUtils.TruncateAt.END)
                .textColor(0xff333333)
                .textSizeDip(13.0f)
                .text(likeCountStateContainer.likeCount)
                .build())
            .child(Image.create(componentContext)
                .heightDip(18.0f)
                .widthDip(18.0f)
                .drawableRes(R.mipmap.icon_test)
                .build())
            .child(Text.create(componentContext)
                .ellipsize(TextUtils.TruncateAt.END)
                .textColor(0xff333333)
                .textSizeDip(13.0f)
                .text("47")
                .build())
            .child(Image.create(componentContext)
                .positionType(YogaPositionType.ABSOLUTE)
                .heightDip(18.0f)
                .widthDip(18.0f)
                .drawableRes(R.mipmap.icon_test)
                .build())
            .build();
    }

    @Override public boolean hasState() {
        return true;
    }

    @Override protected void transferState(StateContainer _prevStateContainer,
        StateContainer _nextStateContainer) {
        LikeCountStateContainer prevStateContainer = (LikeCountStateContainer) _prevStateContainer;
        LikeCountStateContainer nextStateContainer = (LikeCountStateContainer) _nextStateContainer;
        nextStateContainer.likeCount = prevStateContainer.likeCount;
    }

    public void setFootValue(int likeCount) {
        RefreshLikeStateUpdate stateUpdate = new RefreshLikeStateUpdate();
        stateUpdate.likeCount = String.valueOf(likeCount);

        ComponentContext treeContext = footCopy != null ? footCopy.getScopedContext() : null;

        if (treeContext != null) {
            treeContext.updateStateSync(stateUpdate, "");
        }
    }

    private static class LikeCountStateContainer implements StateContainer {
        @State String likeCount;

        @Override public boolean equals(Object o) {
            if (!(o instanceof LikeCountStateContainer)) {
                return false;
            }
            LikeCountStateContainer stateContainer = (LikeCountStateContainer) o;
            if (stateContainer.hashCode() != hashCode()) {
                return false;
            }
            return !TextUtils.isEmpty(likeCount) && likeCount.equals(stateContainer.likeCount);
        }
    }

    private static class RefreshLikeStateUpdate implements ComponentLifecycle.StateUpdate {

        private String likeCount;

        RefreshLikeStateUpdate() {

        }

        @Override public void updateState(StateContainer _stateContainer) {
            LikeCountStateContainer stateContainer = (LikeCountStateContainer) _stateContainer;
            StateValue<String> info = new StateValue<>();
            info.set(likeCount);
            stateContainer.likeCount = info.get();
        }
    }

    public static class Builder extends Component.Builder<Builder> {

        ComponentContext componentContext;
        ComponentFoot componentFoot;

        private void init(ComponentContext context, ComponentFoot foot) {
            super.init(context, 0, 0, foot);
            componentContext = context;
            componentFoot = foot;
        }

        @Override public Builder getThis() {
            return this;
        }

        @Override public ComponentFoot build() {
            ComponentFoot foot = componentFoot;
            release();
            return foot;
        }

        @Override protected void release() {
            super.release();

            componentContext = null;
            componentFoot = null;
        }
    }

    public static Builder create(ComponentContext context) {
        final Builder builder = new Builder();
        ComponentFoot instance = new ComponentFoot();
        builder.init(context, instance);
        return builder;
    }
}
