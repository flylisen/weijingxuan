package com.lisen.android.weijingxuan.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Administrator on 2016/7/28.
 */
public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, ResponseHandlerInterface handlerInterface) {
        client.get(url, handlerInterface);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cnmg = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmg.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }
}
