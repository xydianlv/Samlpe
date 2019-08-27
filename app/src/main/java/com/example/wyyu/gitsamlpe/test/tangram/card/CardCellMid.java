package com.example.wyyu.gitsamlpe.test.tangram.card;

import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardMidBean;
import com.example.wyyu.gitsamlpe.test.tangram.layout.CardCellMidLayout;
import com.example.wyyu.gitsamlpe.test.tangram.support.EventLoadMore;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellMid extends BaseCell<CardCellMidLayout> {

    public static final Class CELL_LAYOUT = CardCellMidLayout.class;

    public static final String CELL_TYPE = "cell_card_mid";
    public static final String CELL_KEY = "cell_key_mid";

    private CardMidBean midBean;

    public CardCellMid() {

    }

    public CardCellMid(CardMidBean midBean) {
        this.midBean = midBean;
    }

    @Override public void parseWith(@NonNull JSONObject data, @NonNull final MVHelper resolver) {
        try {
            midBean = (CardMidBean) data.get(CardCellMid.CELL_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public boolean isValid() {
        return true;
    }

    @Override public void bindView(@NonNull CardCellMidLayout view) {
        view.cacheValue(midBean);
        view.setOnClickListener(this);
    }
}
