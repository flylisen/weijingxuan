package com.lisen.android.weijingxuan.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lisen.android.weijingxuan.app.AppAplication;
import com.lisen.android.weijingxuan.db.MySqliteDatabeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class ChanelManage {
    private static ChanelManage mChanelManage;
    public static List<ChanelItem> defualtUserChanels;
    public static List<ChanelItem> defualtOhterChanels;

    static {
        defualtUserChanels = new ArrayList<>();
        defualtOhterChanels = new ArrayList<>();
        defualtUserChanels.add(new ChanelItem("0", "热点鲜"));
        defualtUserChanels.add(new ChanelItem("1", "推荐看"));
        defualtUserChanels.add(new ChanelItem("2", "段子手"));
        defualtUserChanels.add(new ChanelItem("3", "养生堂"));
        defualtUserChanels.add(new ChanelItem("4", "私房话"));
        defualtUserChanels.add(new ChanelItem("5", "八卦精"));
        defualtUserChanels.add(new ChanelItem("6", "爱生活"));
        defualtUserChanels.add(new ChanelItem("7", "财经迷"));
        defualtUserChanels.add(new ChanelItem("8", "汽车迷"));
        defualtOhterChanels.add(new ChanelItem("9", "科技咖"));
        defualtOhterChanels.add(new ChanelItem("10", "潮人帮"));
        defualtOhterChanels.add(new ChanelItem("11", "辣妈带"));
        defualtOhterChanels.add(new ChanelItem("12", "点赞党"));
        defualtOhterChanels.add(new ChanelItem("13", "旅行家"));
        defualtOhterChanels.add(new ChanelItem("14", "职场人"));
        defualtOhterChanels.add(new ChanelItem("15", "美食家"));
        defualtOhterChanels.add(new ChanelItem("16", "古今通"));
        defualtOhterChanels.add(new ChanelItem("17", "学霸族"));
        defualtOhterChanels.add(new ChanelItem("18", "星座控"));
        defualtOhterChanels.add(new ChanelItem("19", "体育迷"));
    }

    private ChanelManage() {

    }

    public static ChanelManage getChanelManage() {
        if (mChanelManage == null) {
            mChanelManage = new ChanelManage();
        }

        return mChanelManage;
    }

    public List<ChanelItem> getUserChanel() {
        List<ChanelItem> list = new ArrayList<>();
        SQLiteDatabase db = AppAplication.getAppAplication().getSqliteHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from mychanel", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("chanel_name"));
                String id = cursor.getString(cursor.getColumnIndex("chanel_id"));
                ChanelItem item = new ChanelItem(id, name);
                list.add(item);
                Log.d("TAG", "get from database: " + name);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } else {
            list.addAll(defualtUserChanels);
        }
        return list;
    }

    public List<ChanelItem> getOtherChanel() {
        List<ChanelItem> list = new ArrayList<>();
        SQLiteDatabase db = AppAplication.getAppAplication().getSqliteHelper().getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from otherchanel", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("chanel_name"));
                String id = cursor.getString(cursor.getColumnIndex("chanel_id"));
                ChanelItem item = new ChanelItem(id, name);
                list.add(item);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } else {
            list.addAll(defualtOhterChanels);
        }
        return list;
    }
}
