package com.example.wyyu.gitsamlpe.test.tangram.support;

import android.view.View;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CellDefine;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutDefine;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.SimpleClickSupport;

/**
 * Created by wyyu on 2019-08-22.
 **/

public class DefineClickSupport extends SimpleClickSupport {

    public DefineClickSupport() {
        // 开启优化模式，点击会被同一回调到 defaultClick
        setOptimizedMode(true);
    }

    @Override public void defaultClick(View targetView, BaseCell cell, int eventType) {
        if (cell instanceof CellDefine && targetView instanceof ChildLayoutDefine) {
            ((CellDefine) cell).getDefineBean().count++;
            ((ChildLayoutDefine) targetView).setCount(((CellDefine) cell).getDefineBean().count);
        }
    }
}
