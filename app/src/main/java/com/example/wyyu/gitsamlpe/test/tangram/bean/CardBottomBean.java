package com.example.wyyu.gitsamlpe.test.tangram.bean;

import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellBottom;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardBottomBean extends CellBean {

    public String value;

    public CardBottomBean() {
        value = "TextBottom";
    }

    @Override String getCellType() {
        return CardCellBottom.CELL_TYPE;
    }

    @Override String getCellKey() {
        return CardCellBottom.CELL_KEY;
    }
}
