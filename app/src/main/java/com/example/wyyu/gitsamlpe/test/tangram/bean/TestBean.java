package com.example.wyyu.gitsamlpe.test.tangram.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutA;

/**
 * Created by wyyu on 2019-08-20.
 **/

public class TestBean extends CellBean {

    public static final Class CELL_LAYOUT = ChildLayoutA.class;

    public static final String CELL_TYPE = "type_test";
    public static final String CELL_KEY = "key_test";

    @JSONField(name = "index") // 序号
    public int index;

    @Override String getCellType() {
        return CELL_TYPE;
    }

    @Override String getCellKey() {
        return CELL_KEY;
    }
}
