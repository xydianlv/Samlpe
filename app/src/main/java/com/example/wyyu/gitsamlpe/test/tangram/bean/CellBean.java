package com.example.wyyu.gitsamlpe.test.tangram.bean;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-21.
 **/

public abstract class CellBean implements Serializable {

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", getCellType());
        jsonObject.put(getCellKey(), this);
        return jsonObject;
    }

    abstract String getCellType();

    abstract String getCellKey();
}
