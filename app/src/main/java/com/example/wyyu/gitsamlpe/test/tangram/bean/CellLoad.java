package com.example.wyyu.gitsamlpe.test.tangram.bean;

import androidx.annotation.NonNull;
import android.util.Log;
import com.example.wyyu.gitsamlpe.test.tangram.layout.CellLoadLayout;
import com.example.wyyu.gitsamlpe.test.tangram.support.EventLoadMore;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tmall.wireless.tangram.structure.BaseCell;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CellLoad extends BaseCell<CellLoadLayout> {

    public static final Class CELL_LAYOUT = CellLoadLayout.class;

    public static final String CELL_TYPE = "type_load";
    public static final String CELL_KEY = "key_load";

    @Override public void bindView(@NonNull CellLoadLayout view) {
        view.setOnClickListener(this);

        Log.e("CellBindViewTest", "position : " + pos);

        LiveEventBus.get().with(EventLoadMore.EVENT).setValue(new EventLoadMore());
    }
}
