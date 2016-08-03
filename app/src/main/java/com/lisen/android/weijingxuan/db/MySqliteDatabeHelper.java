package com.lisen.android.weijingxuan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MySqliteDatabeHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "weijingxuang.db";
    private static final String TABLE_NAME = "chanel";
    private static final String TABLE_MY_CHANEL = "mychanel";
    private static final String TABLE_OHTER_CHANEL = "otherchanel";
    private static final String TABLE_CONTENT_LIST = "content";
    public MySqliteDatabeHelper(Context context) {
        super(context, DB_NAME, null, 7);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "json_chanel TEXT)";

        String sql2 = "create table if not exists " + TABLE_MY_CHANEL +
                "(" +
                "chanel_name TEXT," +
                "chanel_id TEXt" +
                ")";
        String sql3 = "create table if not exists " + TABLE_OHTER_CHANEL +
                "(" +
                "chanel_name TEXT," +
                "chanel_id TEXt" +
                ")";
        String sql4 = "create table if not exists " + TABLE_CONTENT_LIST +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "url TEXT," +
                "json TEXT" +
                ")";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("drop table " + TABLE_MY_CHANEL);
        onCreate(db);
    }
}
