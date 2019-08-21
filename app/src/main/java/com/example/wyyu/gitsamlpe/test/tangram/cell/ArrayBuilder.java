package com.example.wyyu.gitsamlpe.test.tangram.cell;

import com.example.wyyu.gitsamlpe.test.tangram.bean.CellBean;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class ArrayBuilder {

    private @CellLayoutType String layoutType;
    private JSONObject jsonStyle;
    private JSONArray jsonArray;

    public ArrayBuilder(@CellLayoutType String layoutType) {
        this.layoutType = layoutType;
        jsonArray = new JSONArray();
    }

    public void addAllCell(List<CellBean> beanList) throws JSONException {
        if (beanList == null || beanList.isEmpty()) {
            return;
        }
        for (CellBean cellBean : beanList) {
            jsonArray.put(cellBean.toJsonObject());
        }
    }

    public void addCell(JSONObject object) {
        jsonArray.put(object);
    }

    public void style(JSONObject jsonStyle) {
        this.jsonStyle = jsonStyle;
    }

    public JSONObject toObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("type", layoutType);
        object.put("items", jsonArray);
        object.put("style", jsonStyle);
        return object;
    }
}
