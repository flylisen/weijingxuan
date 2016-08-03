package com.lisen.android.weijingxuan.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/7/27.
 */
public class L  {

    private static boolean mWriteLogs = true;
    public static void setLog(boolean writeLogs) {
        mWriteLogs = writeLogs;
    }

    public static void d(String tag, String msg) {
        if (!mWriteLogs) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!mWriteLogs) {
            return;
        }

        Log.e(tag, msg);
    }
}
