package com.example.wyyu.gitsamlpe.test.tangram.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutDefine;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class CellDefine extends BaseCell {

    public static final Class CELL_LAYOUT = ChildLayoutDefine.class;

    public static final String CELL_TYPE = "type_define";

    public CellDefine() {

    }

    @Override public void parseStyle(@Nullable JSONObject data) {
        ULog.show(data == null ? "" : data.toString());
    }

    @Override public void parseWith(@NonNull JSONObject data, @NonNull final MVHelper resolver) {
        ULog.show(data.toString());
    }

    @Override public boolean isValid() {
        return true;
    }
}
