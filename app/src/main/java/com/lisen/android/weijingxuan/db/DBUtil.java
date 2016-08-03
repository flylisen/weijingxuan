package com.lisen.android.weijingxuan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/7/29.
 */
public class DBUtil {

    private static DBUtil mInstance;
    private MySqliteDatabeHelper sqliteDatabeHelper;
    private SQLiteDatabase db;
    private DBUtil(Context context) {
        sqliteDatabeHelper = new MySqliteDatabeHelper(context);
        db = sqliteDatabeHelper.getWritableDatabase();
    }
    public static DBUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBUtil.class) {
               mInstance = new DBUtil(context);
            }
        }
        return mInstance;
    }


}
