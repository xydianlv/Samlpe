package com.example.wyyu.gitsamlpe.test.litho.define.test;

import android.content.Context;
import android.view.View;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.LithoView;
import com.facebook.litho.Row;
import com.facebook.litho.StateContainer;

/**
 * Created by wyyu on 2019-10-12.
 **/

public abstract class AbsComponent<T> extends Component implements IComponent<T> {

    private ComponentCore componentCore;
    private boolean widthParams;
    private Object[] params;

    public AbsComponent(String simpleName) {
        super(simpleName);
        componentCore = new ComponentCore(simpleName);
        widthParams = false;
    }

    @Override public AbsComponent makeShallowCopy() {
        AbsComponent componentCopy = (AbsComponent) super.makeShallowCopy();
        componentCopy.componentCore = componentCore;
        return componentCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {
        return componentCore.onCreateLayout(componentContext);
    }

    @Override public Component createLayout(ComponentContext context, T data) {
        return Row.create(context).heightDip(0.0f).widthDip(0.0f).build();
    }

    @Override public Component createLayout(ComponentContext context, T data, Object... params) {
        return Row.create(context).heightDip(0.0f).widthDip(0.0f).build();
    }

    @Override public void dispatchEvent(EventHandler eventHandler) {

    }

    @Override public void setViewData(T data) {
        componentCore.setData(data);
        widthParams = false;
    }

    @Override public void setViewData(T data, Object... params) {
        this.componentCore.setData(data);
        this.params = params;
        this.widthParams = true;
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

            return widthParams ? createLayout(componentContext, data, params)
                : createLayout(componentContext, data);
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
                context.updateStateAsync(new StateContainer.StateUpdate(0),
                    getSimpleName() + " -> updateData");
            }
        }
    }
}
