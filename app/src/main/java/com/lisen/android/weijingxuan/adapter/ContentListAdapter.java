package com.lisen.android.weijingxuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.bean.Content;
import com.lisen.android.weijingxuan.util.MyUtils;
import com.lisen.android.weijingxuan.util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class ContentListAdapter extends BaseAdapter {

    private List<Content> mContentList;
    public static final int FIRST_LOAD = 0;
    public static final int REFRESH_LOAD = 1;
    public static final int MORE_LOAD = 2;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    public ContentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mImageLoader = ImageLoader.getInstance();
        mContentList  = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.content_list_item, parent, false);
            viewHolder.ivTitle = (ImageView) convertView.findViewById(R.id.iv_content_list_title);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_content_list_title);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_content_list_user_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_content_list_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Content content = mContentList.get(position);
        viewHolder.tvTitle.setText(content.getTitle());
        viewHolder.tvUserName.setText(content.getUserName());
        viewHolder.tvDate.setText(content.getDate());
        mImageLoader.displayImage(content.getContentImg(), viewHolder.ivTitle, Options.getContentListOptions());
        return convertView;
    }

    class ViewHolder{
        public ImageView ivTitle;
        public TextView tvTitle;
        public TextView tvUserName;
        public TextView tvDate;
    }

    public void setContentList(List<Content> data, int loadType) {
        if (data != null && data.size() > 0) {
            switch (loadType) {
                case FIRST_LOAD:
                    mContentList.addAll(data);
                    break;
                case REFRESH_LOAD:
                    Date lastUpdateDate = MyUtils.stringToDate(mContentList.get(0).getDate());
                    List<Content> newContentList = new ArrayList<>();
                    int i = 0;
                    while (lastUpdateDate.before(MyUtils.stringToDate(data.get(i).getDate()))) {
                        newContentList.add(data.get(i));
                        i++;
                    }
                    mContentList.addAll(0, newContentList);
                    break;
                case MORE_LOAD:
                    mContentList.addAll(data);
                    break;
                default:
                    break;
            }
            notifyDataSetChanged();
        }
    }
}
