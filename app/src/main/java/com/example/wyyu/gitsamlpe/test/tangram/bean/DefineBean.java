package com.example.wyyu.gitsamlpe.test.tangram.bean;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class DefineBean extends CellBean {

    public static final String CELL_TYPE = "type_define";
    public static final String CELL_KEY = "type_key";

    public int index;
    public int count;

    public DefineBean() {
        count = 0;
    }

    @Override String getCellType() {
        return CELL_TYPE;
    }

    @Override String getCellKey() {
        return CELL_KEY;
    }
}
