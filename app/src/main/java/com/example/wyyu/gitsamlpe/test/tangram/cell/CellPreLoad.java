package com.example.wyyu.gitsamlpe.test.tangram.cell;

import android.view.View;
import com.example.wyyu.gitsamlpe.test.tangram.layout.LayoutPreLoad;
import com.tmall.wireless.tangram.structure.BaseCell;

/**
 * Created by wyyu on 2019-09-25.
 **/

public class CellPreLoad extends BaseCell<LayoutPreLoad> {

    public static final String CELL_TYPE = "type_pre_load";

    public static Class<? extends View> getCellLayout() {
        return LayoutPreLoad.class;
    }

    @Override public boolean isValid() {
        return true;
    }
}
