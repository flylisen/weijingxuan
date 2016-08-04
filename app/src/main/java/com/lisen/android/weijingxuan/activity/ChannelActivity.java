package com.lisen.android.weijingxuan.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.adapter.DragGridViewAdapter;
import com.lisen.android.weijingxuan.adapter.OtherGridViewAdapter;
import com.lisen.android.weijingxuan.app.AppAplication;
import com.lisen.android.weijingxuan.bean.ChanelItem;
import com.lisen.android.weijingxuan.bean.ChanelManage;
import com.lisen.android.weijingxuan.db.MySqliteDatabeHelper;
import com.lisen.android.weijingxuan.util.Constants;
import com.lisen.android.weijingxuan.view.MyDragGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ChannelActivity extends AppCompatActivity {


    private ArrayList<ChanelItem> mChanelItems = new ArrayList<>();
    private boolean mStateEdit = false;


    private List<ChanelItem> mUserChanels;
    private List<ChanelItem> mOhterChanels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chanel);

        initData();
        intiView();
    }

    /**
     * 初始化视图
     */
    private void intiView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_channel);

        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.back_detail);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quitAndSave();
                }
            });
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

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

        MyDragGridView userGridView = (MyDragGridView) findViewById(R.id.user_drag_grid_view_channel_activity);
        GridView otherGridView = (GridView) findViewById(R.id.other_grid_view_channel_activity);
        final DragGridViewAdapter dragGridViewAdapter = new DragGridViewAdapter(mUserChanels, ChannelActivity.this);
        final OtherGridViewAdapter otherGridViewAdapter = new OtherGridViewAdapter(mOhterChanels, ChannelActivity.this);

        if (userGridView != null) {
            userGridView.setAdapter(dragGridViewAdapter);
            userGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!mStateEdit) {
                        return;
                    }
                    ChanelItem chanelItem = dragGridViewAdapter.deleteItem(position);
                    if (chanelItem != null) {
                        otherGridViewAdapter.addItem(chanelItem);
                    }

                }
            });
        }
        if (otherGridView != null) {
            otherGridView.setAdapter(otherGridViewAdapter);

            otherGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!mStateEdit) {
                        return;
                    }
                    ChanelItem chanelItem = otherGridViewAdapter.deleteItem(position);
                    if (chanelItem != null) {
                        dragGridViewAdapter.addItem(chanelItem);
                    }
                }
            });
        }
    }

    /**
     * 从数据库中加载数据
     */
    private void initData() {
        mUserChanels = ChanelManage.getChanelManage().getUserChanel();
        mOhterChanels = ChanelManage.getChanelManage().getOtherChanel();
    }

    /**
     * 退出和保存数据
     */
    private void quitAndSave() {

        int childCount = mUserChanels.size();
        SQLiteDatabase db = AppAplication.getSqliteHelper().getWritableDatabase();
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
     *
     * @return
     */
    private String getId(String name) {
        String id = "";
        for (ChanelItem item : mChanelItems) {
            if (name.equals(item.getName())) {
                id = item.getId();
                break;
            }
        }

        return id;
    }


    /**
     * 将标签添到容器
     *
     * @param item
     * @param container
     */


    /**
     * 按下返回键后保存
     */
    @Override
    public void onBackPressed() {
        quitAndSave();
    }
}
