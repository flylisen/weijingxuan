package com.lisen.android.weijingxuan.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.activity.DetailActivity;
import com.lisen.android.weijingxuan.adapter.ContentListAdapter;
import com.lisen.android.weijingxuan.app.AppAplication;
import com.lisen.android.weijingxuan.bean.Content;
import com.lisen.android.weijingxuan.bean.Response;
import com.lisen.android.weijingxuan.util.Constants;
import com.lisen.android.weijingxuan.util.HttpUtils;
import com.lisen.android.weijingxuan.util.MyUtils;
import com.lisen.android.weijingxuan.view.PullToRefreshListView;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/7/30.
 */
public class NewsFragment extends Fragment implements PullToRefreshListView.IReflashListener {

    private String mChanelId;
    private String mChanelName;
    private List<Content> mContentList;
    private Response mResponse;
    private Handler mHandler = new Handler();

    private ContentListAdapter mAdapter;
    private PullToRefreshListView mListView;
    /**
     * 保存最后更新的时间
     */
    private String mLastUpdateTime = "";
    /**
     * 默认请求的页数
     */
    public static final String PAGE_DEFUALT = "1";
    private String mUrl;
    /**
     * 下次请求的页码
     */
    private int mNextPage = 2; //从第二页算起，第一页是固定的
    private boolean mIsLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_layout, container, false);
        mListView = (PullToRefreshListView) view.findViewById(R.id.lv_content_list);
        mAdapter = new ContentListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        //设置下拉刷新完成接口
        mListView.setInterface(this);
        //监听上拉加载更多
        mListView.setOnLoadMoreLIstener(new PullToRefreshListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Content content = (Content) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("detail_url", content.getUrl());
                startActivity(intent);
            }
        });

        Bundle args = getArguments();
        mChanelId = args != null ? args.getString("chanel_id") : "0";
        mChanelName = args != null ? args.getString("chanel_name") : "0";
        mUrl = Constants.ARTICAL_LIST_BASE + "?" + Constants.APP_ID +
                "&" + "typeId=" + mChanelId + "&" + "page=" + PAGE_DEFUALT + "&" + Constants.SIGN_SECRET;

        return view;
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        if (mIsLoading) {
            //正在加载 返回
            return;
        }
        mIsLoading = true;
        if (HttpUtils.isNetworkConnected(getActivity())) {
            String page = String.valueOf(mNextPage);
            String url = Constants.ARTICAL_LIST_BASE + "?" + Constants.APP_ID +
                    "&" + "typeId=" + mChanelId + "&" + "page=" + page + "&" + Constants.SIGN_SECRET;

            //发起请求
            HttpUtils.get(url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    mIsLoading = false;
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    parseMoreJson(responseString);
                }
            });
        } else {
           Toast toast = Toast.makeText(getActivity(), "当前为离线状态，无法加载更多内容", Toast.LENGTH_LONG);
            MyUtils.showMyToast(toast, 1000);
            mIsLoading = false;
        }
    }

    /**
     * 解析更多数据
     */
    private void parseMoreJson(String jsonString) {
        java.lang.reflect.Type type = new TypeToken<Response>() {
        }.getType();
        Gson gson = new Gson();
        mResponse = gson.fromJson(jsonString, type);
        if (mResponse != null) {
            final List<Content> contentList = mResponse.getShowapi_res_body().getPagebean().getContentlist();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setContentList(contentList, ContentListAdapter.MORE_LOAD);
                }
            });

        }
        mIsLoading = false;
        mNextPage++;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

       // loadData();
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        loadData();
        super.onActivityCreated(savedInstanceState);

    }

    private boolean mDataFromDB;
    private void loadData() {
        if (mIsLoading) {
            //当前已在加载 返回
            return;
        }
        mIsLoading = true;

        if (HttpUtils.isNetworkConnected(getActivity())) {

            HttpUtils.get(mUrl, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    /// TODO: 2016/8/2 一些人性化的提示
                    mIsLoading = false;
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    mDataFromDB = false;
                    parseJson(responseString);
                }
            });

        } else {
            /// TODO: 2016/7/30 从数据库中加载
            SQLiteDatabase db = AppAplication.getAppAplication().getSqliteHelper().getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from content where url = " + "'" + mUrl + "'", null);
            if (cursor != null && cursor.moveToFirst()) {
                String jsonString = cursor.getString(cursor.getColumnIndex("json"));
                mDataFromDB = true;
                parseJson(jsonString);
                cursor.close();
                db.close();
            }

        }
    }

    /**
     * 解析数据
     *
     * @param jsonString
     */
    private void parseJson(String jsonString) {
        java.lang.reflect.Type type = new TypeToken<Response>() {
        }.getType();
        Gson gson = new Gson();
        mResponse = gson.fromJson(jsonString, type);
        if (mResponse != null) {
            mContentList = mResponse.getShowapi_res_body().getPagebean().getContentlist();
            String newestTime = mContentList.get(0).getDate();
            if (newestTime.equals(mLastUpdateTime)) {
                //没更新，返回
                mListView.reflashComplete();
                mIsLoading = false;
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //有更新 重置数据集
                    if (!mFromRefresh) {
                        mAdapter.setContentList(mContentList, ContentListAdapter.FIRST_LOAD);
                    } else {
                        mAdapter.setContentList(mContentList, ContentListAdapter.REFRESH_LOAD);
                        mListView.reflashComplete();
                    }

                }
            });
            mLastUpdateTime = newestTime;
            mIsLoading = false;
            /// TODO: 2016/8/2  有更新，将最新的数据代替之前的数据

            if (!mDataFromDB) {
                SQLiteDatabase db = AppAplication.getAppAplication().getSqliteHelper().getWritableDatabase();
                db.execSQL("replace into content(url, json) values (" + "'" + mUrl + "'" + "," + "'" + jsonString + "'" + ")");
                db.close();
            }

        }
    }

    private boolean mFromRefresh = false;

    @Override
    public void onRefresh() {
        if (HttpUtils.isNetworkConnected(getActivity())) {
            mFromRefresh = true;
            loadData();
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getActivity(), "无网络连接,无法刷新数据", Toast.LENGTH_LONG);
                    MyUtils.showMyToast(toast, 1000);
                    mListView.reflashComplete();
                }
            }, 3000);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
