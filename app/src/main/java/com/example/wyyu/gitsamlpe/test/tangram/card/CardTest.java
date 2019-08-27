package com.example.wyyu.gitsamlpe.test.tangram.card;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.card.LinearCard;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class CardTest extends LinearCard {

    public static final String TYPE_CARD = "type_card";

    @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);
        try {
            List<BaseCell> cellList = new ArrayList<>();
            JSONArray cellArray = data.getJSONArray("items");
            if (cellArray == null || cellArray.length() == 0) {
                return;
            }
            for (int index = 0; index < cellArray.length(); index++) {
                JSONObject object = cellArray.getJSONObject(index);
                if (object == null || TextUtils.isEmpty(object.optString("type"))) {
                    continue;
                }
                switch (object.optString("type")) {
                    case CardCellTop.CELL_TYPE:
                        CardCellTop cellTop = new CardCellTop();
                        object.put("bizId", id);
                        resolver.parseCell(cellTop, object);
                        cellList.add(cellTop);
                        break;
                    case CardCellMid.CELL_TYPE:
                        CardCellMid cellMid = new CardCellMid();
                        object.put("bizId", id);
                        resolver.parseCell(cellMid, object);
                        cellList.add(cellMid);
                        break;
                    case CardCellBottom.CELL_TYPE:
                        CardCellBottom cellBottom = new CardCellBottom();
                        object.put("bizId", id);
                        resolver.parseCell(cellBottom, object);
                        cellList.add(cellBottom);
                        break;
                }
            }
            if (!cellList.isEmpty()) {
                setCells(cellList);
                notifyDataChange();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
