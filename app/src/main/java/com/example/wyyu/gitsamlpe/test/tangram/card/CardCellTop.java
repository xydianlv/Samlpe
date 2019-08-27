package com.example.wyyu.gitsamlpe.test.tangram.card;

import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardTopBean;
import com.example.wyyu.gitsamlpe.test.tangram.layout.CardCellTopLayout;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellTop extends BaseCell<CardCellTopLayout> {

    public static final Class CELL_LAYOUT = CardCellTopLayout.class;

    public static final String CELL_TYPE = "cell_card_top";
    public static final String CELL_KEY = "cell_key_top";

    private CardTopBean topBean;

    public CardCellTop() {

    }

    public CardCellTop(CardTopBean topBean) {
        this.topBean = topBean;
    }

    @Override public void parseWith(@NonNull JSONObject data, @NonNull final MVHelper resolver) {
        try {
            topBean = (CardTopBean) data.get(CardCellTop.CELL_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public boolean isValid() {
        return true;
    }

    @Override public void bindView(@NonNull CardCellTopLayout view) {
        view.cacheValue(topBean);
        view.setOnClickListener(this);
    }
}
