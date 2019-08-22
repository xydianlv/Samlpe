package com.example.wyyu.gitsamlpe.test.tangram.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutDefine;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-21.
 *
 * 自定义组件中，Cell、Bean、Layout 是一一对应的关系，即列表中的每一个 Item 都有这样一组对应
 *
 * 在使用自定义组件时，可以把 Cell 作为元素来做列表构建
 *
 * 就是从 Model 层返回给 Activity 可以返回一个 List<Cell>
 **/

public class CellDefine extends BaseCell<ChildLayoutDefine> {

    public static final Class CELL_LAYOUT = ChildLayoutDefine.class;

    public static final String CELL_TYPE = "type_define";

    private DefineBean defineBean;

    public CellDefine() {

    }

    @Override public void parseStyle(@Nullable JSONObject data) {
    }

    @Override public void parseWith(@NonNull JSONObject data, @NonNull final MVHelper resolver) {
        try {
            defineBean = (DefineBean) data.get(DefineBean.CELL_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public boolean isValid() {
        return true;
    }

    @Override public void bindView(@NonNull ChildLayoutDefine view) {
        view.cacheValue(defineBean);
        view.setOnClickListener(this);
    }

    public DefineBean getDefineBean() {
        return defineBean;
    }
}
