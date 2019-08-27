package com.example.wyyu.gitsamlpe.test.tangram.bean;

import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellTop;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardTopBean extends CellBean {

    public String value;

    public CardTopBean() {
        value = "TextTop";
    }

    @Override String getCellType() {
        return CardCellTop.CELL_TYPE;
    }

    @Override String getCellKey() {
        return CardCellTop.CELL_KEY;
    }
}
