package com.example.wyyu.gitsamlpe.test.database;

import android.arch.persistence.db.SupportSQLiteQueryBuilder;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class DataManager {

    public static int saveData(@NonNull DataBean dataBean) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DataKey.ID, dataBean.id);
        contentValues.put(DataKey.NAME, dataBean.name);
        contentValues.put(DataKey.USER_JSON, dataBean.userJson.toJSONString());

        TDataBase.getDatabase().insert(DataKey.TABLE_NAME, null, contentValues);

        return getDataCount();
    }

    public static List<DataBean> getDataList() {
        Cursor cursor = getUserListCursor();
        if (cursor == null) {
            return null;
        }
        List<DataBean> chatRoomList = new LinkedList<>();
        while (cursor.moveToNext()) {
            DataBean dataBean = new DataBean();

            dataBean.id = cursor.getLong(cursor.getColumnIndex(DataKey.ID));
            dataBean.name = cursor.getString(cursor.getColumnIndex(DataKey.NAME));

            byte[] userJson = cursor.getBlob(cursor.getColumnIndex(DataKey.USER_JSON));
            String user = new String(userJson, Charset.forName("UTF-8"));
            dataBean.userJson = JSONObject.parseObject(user);

            chatRoomList.add(dataBean);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return chatRoomList;
    }

    private static int getDataCount() {
        Cursor cursor = getUserListCursor();
        int count = cursor == null ? 0 : cursor.getCount();

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return count;
    }

    private static Cursor getUserListCursor() {
        SQLiteDatabase database = TDataBase.getDatabase();
        String sql = SupportSQLiteQueryBuilder.builder(DataKey.TABLE_NAME).columns(new String[] {
            DataKey.ID, DataKey.NAME, DataKey.USER_JSON
        }).limit(String.valueOf(100)).create().getSql();
        return database.rawQuery(sql, null);
    }
}
