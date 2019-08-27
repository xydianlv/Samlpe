package com.example.wyyu.gitsamlpe.test.tangram.bean;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class LoadBean extends CellBean {

    @Override String getCellType() {
        return CellLoad.CELL_TYPE;
    }

    @Override String getCellKey() {
        return CellLoad.CELL_KEY;
    }
}
