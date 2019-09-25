package com.example.wyyu.gitsamlpe.test.tangram;

import com.example.wyyu.gitsamlpe.test.tangram.cell.CellPreLoad;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-09-25.
 **/

public class CellUtils {

    /**
     * TangramEngine 在初始化时只接受 JSONArray 和 List<Card> 来设置第一屏数据
     *
     * 为了统一 Model 层的数据回调 List<BaseCell> ，在完成 Engine 初始化后，用这条 JSONArray 来做列表预加载
     *
     * @return 预加载的数据
     */
    public static JSONArray getPreLoadArray() {
        try {
            JSONObject object = new JSONObject();
            object.put("type", CellPreLoad.CELL_TYPE);

            JSONArray array = new JSONArray();
            array.put(object);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("items", array);
            jsonObject.put("type", "container-oneColumn");

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);

            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
