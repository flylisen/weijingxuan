package com.lisen.android.weijingxuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.bean.ChanelItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class OtherGridViewAdapter extends BaseAdapter {

    private List<ChanelItem> mDatas;
    private Context mContext;

    public OtherGridViewAdapter(List<ChanelItem> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public ChanelItem getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setText(mDatas.get(position).getName());
        return view;
    }

    public void addItem(ChanelItem item) {
        mDatas.add(0, item);
        notifyDataSetChanged();
    }

    public ChanelItem deleteItem(int position) {
        ChanelItem chanelItem = mDatas.remove(position);
        notifyDataSetChanged();
        return chanelItem;
    }
}
