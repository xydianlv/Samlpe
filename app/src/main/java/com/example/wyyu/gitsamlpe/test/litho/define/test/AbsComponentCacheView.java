package com.example.wyyu.gitsamlpe.test.litho.define.test;

import android.content.Context;
import android.view.View;
import com.facebook.litho.LithoView;

/**
 * Created by wyyu on 2019-10-15.
 **/

public abstract class AbsComponentCacheView<T> extends AbsComponent<T> {

    protected View contentView;

    public AbsComponentCacheView(String simpleName) {
        super(simpleName);
    }

    @Override public View createView(Context context) {
        contentView = LithoView.create(context, this);
        return contentView;
    }
}
