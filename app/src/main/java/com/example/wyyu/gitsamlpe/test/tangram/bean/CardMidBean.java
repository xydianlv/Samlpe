package com.example.wyyu.gitsamlpe.test.tangram.bean;

import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellMid;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardMidBean extends CellBean {

    public String value;

    public CardMidBean() {
        value = "TextMid";
    }

    @Override String getCellType() {
        return CardCellMid.CELL_TYPE;
    }

    @Override String getCellKey() {
        return CardCellMid.CELL_KEY;
    }
}
