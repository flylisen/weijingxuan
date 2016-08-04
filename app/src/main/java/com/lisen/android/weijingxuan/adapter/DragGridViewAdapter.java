package com.lisen.android.weijingxuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.bean.ChanelItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class DragGridViewAdapter extends BaseAdapter {


    private List<ChanelItem> mDatas;
    private Context mContext;
    private int mHidePosition = AdapterView.INVALID_POSITION;
    public DragGridViewAdapter(List<ChanelItem> datas, Context context) {
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
        if (position != mHidePosition) {
            view.setVisibility(View.VISIBLE);
            view.setText(mDatas.get(position).getName());
        } else {
            view.setVisibility(View.INVISIBLE);
        }
        view.setId(position);
        return view;
    }

    public void hideView(int position) {
        mHidePosition = position;
        notifyDataSetChanged();
    }

    public void showHideView() {
        mHidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void swap(int draggedPosition, int destinationPosition) {
        //从前往后移，其他item依次往前移
        if (draggedPosition < destinationPosition) {
            mDatas.add(destinationPosition + 1, getItem(draggedPosition));
            mDatas.remove(draggedPosition);
        } else if (draggedPosition> destinationPosition) {
            mDatas.add(destinationPosition, getItem(draggedPosition));
            mDatas.remove(draggedPosition + 1);
        }
        mHidePosition = destinationPosition;
        notifyDataSetChanged();
    }

    public void addItem(ChanelItem item) {
        mDatas.add(item);
        notifyDataSetChanged();
    }

    public ChanelItem deleteItem(int position) {
        ChanelItem chanelItem =  mDatas.remove(position);
        notifyDataSetChanged();
        return chanelItem;

    }

    public List<ChanelItem> getDatas() {
        return mDatas;
    }
}
