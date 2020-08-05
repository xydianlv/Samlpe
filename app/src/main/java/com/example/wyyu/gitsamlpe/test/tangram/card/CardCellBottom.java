package com.example.wyyu.gitsamlpe.test.tangram.card;

import androidx.annotation.NonNull;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardBottomBean;
import com.example.wyyu.gitsamlpe.test.tangram.layout.CardCellBottomLayout;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardCellBottom extends BaseCell<CardCellBottomLayout> {

    public static final Class CELL_LAYOUT = CardCellBottomLayout.class;

    public static final String CELL_TYPE = "cell_card_bottom";
    public static final String CELL_KEY = "cell_key_bottom";

    private CardBottomBean bottomBean;

    public CardCellBottom() {

    }

    public CardCellBottom(CardBottomBean bottomBean) {
        this.bottomBean = bottomBean;
    }

    @Override public void parseWith(@NonNull JSONObject data, @NonNull final MVHelper resolver) {
        try {
            bottomBean = (CardBottomBean) data.get(CardCellBottom.CELL_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public boolean isValid() {
        return true;
    }

    @Override public void bindView(@NonNull CardCellBottomLayout view) {
        view.cacheValue(bottomBean);
        view.setOnClickListener(this);
    }
}
