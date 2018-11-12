package com.example.wyyu.gitsamlpe.test.database;

import android.content.Context;
import android.util.Log;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.application.AppController;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class TDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gitsample.db";

    // 每当需要创建新的表时，需要将 Version 升级，数据库在初始化时会回调 onUpgrade 方法进行创建
    private static final int VERSION = 1;

    private static final String TAG = "ZJDataBase";

    private static volatile SQLiteDatabase database;

    private TDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ULog.show("TDataBase onCreate");
            db.execSQL(DataKey.CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ULog.show("TDataBase onCreate exception : " + e.getMessage());
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            ULog.show("TDataBase onUpgrade");
            if (oldVersion == 1) {
                ULog.show("TDataBase onUpgrade create_table");
                db.execSQL(DataKey.CREATE_TABLE);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ULog.show("TDataBase onUpgrade exception : " + e.getMessage());
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // 向外界提供获取 数据库 的接口
    public synchronized static SQLiteDatabase getDatabase() {
        if (database == null) {
            database = new TDataBase(AppController.getAppContext()).getWritableDatabase();
        }
        return database;
    }

    // 在 Application 初始化时调用该方法初始化数据库
    public synchronized static void init() {
        if (database != null) {
            return;
        }
        try {
            getDatabase();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}