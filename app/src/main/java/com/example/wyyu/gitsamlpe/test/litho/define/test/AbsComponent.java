package com.example.wyyu.gitsamlpe.test.litho.define.test;

import android.content.Context;
import android.view.View;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.EventHandler;
import com.facebook.litho.LithoView;
import com.facebook.litho.StateContainer;

/**
 * Created by wyyu on 2019-10-12.
 **/

public abstract class AbsComponent<T> extends Component implements IComponent<T> {

    private ComponentCore componentCore;

    public AbsComponent(String simpleName) {
        super(simpleName);
        componentCore = new ComponentCore(simpleName);
    }

    @Override public AbsComponent makeShallowCopy() {
        AbsComponent componentCopy = (AbsComponent) super.makeShallowCopy();
        componentCopy.componentCore = componentCore;
        return componentCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {
        return componentCore.onCreateLayout(componentContext);
    }

    @Override public void dispatchEvent(EventHandler eventHandler) {

    }

    @Override public void setViewData(T data) {
        componentCore.setData(data);
    }

    @Override public View createView(Context context) {
        return LithoView.create(context, this);
    }

    private class ComponentCore extends Component {

        private T data;

        private ComponentCore(String simpleName) {
            super(simpleName + "Core");
            data = null;
        }

        @Override protected Component onCreateLayout(ComponentContext componentContext) {
            setScopedContext(componentContext);

            return createLayout(componentContext, data);
        }

        @Override public boolean hasState() {
            return true;
        }

        @Override
        public Object dispatchOnEvent(final EventHandler eventHandler, final Object eventState) {
            dispatchEvent(eventHandler);
            return null;
        }

        private void setData(T data) {
            this.data = data;

            ComponentContext context = getScopedContext();
            if (context != null) {
                context.updateStateAsync(new CoreUpdate(), getSimpleName() + " -> updateData");
            }
        }
    }

    private static final class CoreUpdate implements ComponentLifecycle.StateUpdate {

        @Override public void updateState(StateContainer stateContainer) {

        }
    }
}
