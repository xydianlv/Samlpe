package com.example.wyyu.gitsamlpe.test.database;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class DataBean {

    @JSONField(name = "id") // 用户ID
    public long id;

    @JSONField(name = "name") // 用户昵称
    public String name;

    // 用户完整数据，方便数据库扩展
    public JSONObject userJson;

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
