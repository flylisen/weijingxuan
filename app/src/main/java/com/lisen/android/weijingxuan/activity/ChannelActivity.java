package com.lisen.android.weijingxuan.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.app.AppAplication;
import com.lisen.android.weijingxuan.bean.ChanelItem;
import com.lisen.android.weijingxuan.bean.ChanelManage;
import com.lisen.android.weijingxuan.db.MySqliteDatabeHelper;
import com.lisen.android.weijingxuan.util.Constants;
import com.lisen.android.weijingxuan.util.HttpUtils;
import com.lisen.android.weijingxuan.util.L;
import com.lisen.android.weijingxuan.view.FlowLayout;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ChannelActivity extends AppCompatActivity {


    private ArrayList<ChanelItem> mChanelItems = new ArrayList<>();
    private boolean mStateEdit = false;
    private FlowLayout mOhterChanelsContainer;
    private FlowLayout mUserChanelsContainer;
    private MySqliteDatabeHelper helper;
    private List<ChanelItem> mUserChanels;
    private List<ChanelItem> mOhterChanels;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chanel);
        helper = AppAplication.getSqliteHelper();
        String url = Constants.ARTICAL_CATEGORY_BASE + "?" + "showapi_appid=" + Constants.APP_ID
                + "&" + "showapi_sign=" + Constants.SIGN_SECRET;
        mOhterChanelsContainer = (FlowLayout) findViewById(R.id.mAllChanel_Container);
        mUserChanelsContainer = (FlowLayout) findViewById(R.id.mMyChanel_Container);
        mUserChanels = ChanelManage.getChanelManage().getUserChanel();
        mOhterChanels = ChanelManage.getChanelManage().getOtherChanel();
       // initData(url);
        if (mUserChanels != null && mUserChanels.size() > 0) {
            for (ChanelItem item : mUserChanels) {
                addToChanel(item, mUserChanelsContainer);
            }
        }

        if (mOhterChanels != null && mOhterChanels.size() > 0) {
            for (ChanelItem item : mOhterChanels) {
                addToChanel(item, mOhterChanelsContainer);
            }
        }
        final TextView tvEdit = (TextView) findViewById(R.id.text_edit);
        if (tvEdit != null) {
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mStateEdit) {
                        tvEdit.setText(getResources().getString(R.string.finish));
                        mStateEdit = !mStateEdit;
                    } else if (mStateEdit) {
                        tvEdit.setText(getResources().getString(R.string.edit));
                        mStateEdit = !mStateEdit;
                        quitAndSave();
                    }
                }
            });
        }
    }

    /**
     * 退出和保存数据
     */
    private void quitAndSave() {
        int childCount = mUserChanelsContainer.getChildCount();
        SQLiteDatabase db = helper.getWritableDatabase();
        if (childCount == 0) {
            Toast.makeText(ChannelActivity.this, "请选择至少一个频道", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存用户频道
        db.execSQL("delete  from mychanel");
        for (int i = 0; i < mUserChanels.size(); i++) {
            ChanelItem chanelItem = mUserChanels.get(i);
            String name = chanelItem.getName();
            String id = chanelItem.getId();
            db.execSQL("insert into mychanel values (" + "'" + name + "'" + "," + "'" + id + "'" + ")");
        }

        //保存推荐频道
        db.execSQL("delete  from otherchanel");
        for (int i = 0; i < mOhterChanels.size(); i++) {
            ChanelItem chanelItem = mOhterChanels.get(i);
            String name = chanelItem.getName();
            String id = chanelItem.getId();
            db.execSQL("insert into otherchanel values (" + "'" + name + "'" + "," + "'" + id + "'" + ")");
        }
        db.close();
        setResult(MainActivity.CHANEL_RESULT);

        finish();
    }

    /**
     * 得到频道对应的id
     * @return
     */
    private String getId(String name) {
        String id = "";
        for(ChanelItem item : mChanelItems) {
            if (name.equals(item.getName())) {
                id = item.getId();
                break;
            }
        }

        return id;
    }

    private void initData(String url) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from chanel", null);
        if (cursor != null && cursor.moveToFirst()) {
            String jsonString = cursor.getString(cursor.getColumnIndex("json_chanel"));
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                parseJson(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cursor.close();
            db.close();
        } else {

            if (HttpUtils.isNetworkConnected(ChannelActivity.this)) {
                HttpUtils.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        L.d("TAG", "成功接收到数据:" + response.toString());
                        String jsonString = response.toString();
                        Log.d("TAG", jsonString);
                      db.execSQL("replace into chanel(json_chanel) values (" + "'" + jsonString + "'" + ")");
                        parseJson(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        L.d("TAG", "获取数据失败:");
                        throwable.printStackTrace();
                    }

                });
            }
        }
    }

    private void addToAllChanel() {
        if (mChanelItems != null && mChanelItems.size() > 0) {

            for (ChanelItem item : mChanelItems) {

                addToChanel(item, mOhterChanelsContainer);
            }
        }
    }

    /**
     * 将标签添到容器
     *
     * @param item
     * @param container
     */
    private void addToChanel(final ChanelItem item, final FlowLayout container) {
        TextView textView = new TextView(ChannelActivity.this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.leftMargin = 10;
        p.leftMargin = 10;
        p.topMargin = 10;
        p.bottomMargin = 10;
        textView.setPadding(20, 10, 20, 10);
        textView.setText(item.getName());
       // Log.d("TAG", item.getName());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLUE);
        textView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStateEdit) {
                    return;
                }
                String name = ((TextView) v).getText().toString();
                if (name.equals(item.getName())) {

                    if (v.getParent() == mOhterChanelsContainer) {
                        mOhterChanelsContainer.removeView(v);
                        mUserChanelsContainer.addView(v);
                        mOhterChanels.remove(item);
                        mUserChanels.add(item);
                    } else if (v.getParent() == mUserChanelsContainer) {
                        mUserChanelsContainer.removeView(v);
                        mOhterChanelsContainer.addView(v);
                        mUserChanels.remove(item);
                        mOhterChanels.add(item);
                    }
                    mOhterChanelsContainer.invalidate();
                    mUserChanelsContainer.invalidate();
                }
            }
        });
        container.addView(textView, p);

    }


    private void parseJson(JSONObject responseString) {
        try {
            JSONObject jsonObject = responseString.getJSONObject("showapi_res_body");
            JSONArray jsonArray = jsonObject.getJSONArray("typeList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                ChanelItem chanelItem = new ChanelItem(itemObject.getString("id"),itemObject.getString("name"));
                mChanelItems.add(chanelItem);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToMyChanel() {
        if (mChanelItems != null && mChanelItems.size() > 0) {

            for (ChanelItem item : mChanelItems) {

                if (item.getId().equals("1") || item.getId().equals("0")) {
                    addToChanel(item, mUserChanelsContainer);
                }
            }
        }
    }

    /**
     * 按下返回键后保存
     */
    @Override
    public void onBackPressed() {
        quitAndSave();
    }
}
