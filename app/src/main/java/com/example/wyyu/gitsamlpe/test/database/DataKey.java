package com.example.wyyu.gitsamlpe.test.database;

/**
 * Created by wyyu on 2018/11/12.
 **/

public interface DataKey {

    String TABLE_NAME = "table_data_list";

    String ID = "id";

    String NAME = "name";

    String USER_JSON = "user_json";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
        + TABLE_NAME
        + " ("
        + ID
        + " integer(64),"
        + NAME
        + " text,"
        + USER_JSON
        + " blob"
        + ");";
}
